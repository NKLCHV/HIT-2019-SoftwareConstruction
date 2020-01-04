package applications;

import api.TrackGameApi;
import circularorbit.TrackGameOrbit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ortherapis.TrackGameDifference;
import physicalobject.Athlete;
import track.GameTrack;

public class TrackGame {

  private TrackGameOrbit orbit = new TrackGameOrbit();
  private TrackGameApi c1Api = new TrackGameApi();
  private boolean inited = false;

  public Logger logger = Logger.getLogger(TrackGame.class);

  public TrackGameOrbit getTrackGameOrbit() {
    return orbit;
  }

  /**
   * 初始化轨道系统 .
   *
   * @param filename .
   */
  public void init(String filename) {
    if (filename != null) {
      orbit.readFiles(filename);
      orbit = c1Api.randomSort(orbit);
      inited = true;
      logger.debug("TrackGame has been initialized");
    }
  }

  /**
   * 清除数据.
   */
  public void clean() {
    orbit = new TrackGameOrbit();
    inited = false;
    logger.debug("TrackGame has been cleared");
  }

  /**
   * 编排分组.
   *
   * @param choice 选择随机或者顺序
   */
  public void makeGroup(String choice) {
    if (inited) {
      if (choice.equals("random")) {
        orbit = c1Api.randomSort(orbit);
      } else if (choice.equals("S2B")) {
        orbit = c1Api.s2bSort(orbit);
      }
      logger.debug("TrackGame has been made groups");
    }
    logger.info("TrackGame try to make group before init");
  }

  /**
   * 交换两个运动员的分组赛道安排.
   */
  public void changeGroups(Athlete a1, Athlete a2) {
    if (inited) {
      boolean flag = false;
      for (Map<GameTrack, List<Athlete>> map : orbit.gameGroup) {
        for (GameTrack track : map.keySet()) {
          if (map.get(track).contains(a1) && map.get(track).contains(a2)) {
            flag = true;
          }
        }
      }
      if (flag) {
        c1Api.moveInGroup(orbit, a1, a2);
      } else {
        c1Api.moveBetweenGroup(orbit, a1, a2);
      }
      logger.debug("TrackGame groups has been changed");
    }
    logger.info("TrackGame try to change group before init");
  }

  /**
   * 交换两个运动员的分组赛道安排.
   *
   * @param group1 运动员1的位置
   * @param group2 运动员2的位置
   */
  public void changeGroups(int group1, int track1, int i1, int group2, int track2, int i2) {
    if (inited) {
      Athlete a1 = c1Api.findAthlete(orbit, group1, track1, i1);
      Athlete a2 = c1Api.findAthlete(orbit, group2, track2, i2);
      if (a1 != null && a2 != null) {
        changeGroups(a1, a2);
      }
    } else {
      logger.info("TrackGame try to change group before init");
    }
  }

  /**
   * 删除一个运动员，包括去除分组安排.
   *
   * @param a1 运动员
   */
  public void deleteAthlete(Athlete a1) {
    if (inited) {
      GameTrack track = null;
      for (Map<GameTrack, List<Athlete>> map : orbit.gameGroup) {
        for (GameTrack track1 : map.keySet()) {
          if (map.get(track1).contains(a1)) {
            track = track1;
          }
        }
      }
      if (track != null) {
        orbit.removeObject(track, a1);
        orbit.objectList.remove(a1);

        for (Map<GameTrack, List<Athlete>> map : orbit.gameGroup) {
          if (map.keySet().contains(track)) {
            if (map.get(track).isEmpty()) {
              map.remove(track);
              orbit.trackSet.remove(track);
            }
          }
        }
      }
      logger.debug("TrackGame has delete a athlete");
    }
    logger.info("TrackGame try to delete athlete before init");
  }

  /**
   * 删除一个运动员，包括去除分组安排.
   *
   * @param group 运动员的位置
   */
  public void deleteAthlete(int group, int track, int i) {
    if (inited) {
      Athlete a1 = c1Api.findAthlete(orbit, group, track, i);
      if (a1 != null) {
        deleteAthlete(a1);
      }
    } else {
      logger.info("TrackGame try to delete athlete before init");
    }
  }

  /**
   * 增加一个运动员，默认分到最后的小组.
   */
  public void addAthlete(Athlete a1) {
    if (inited) {
      boolean flag = false;
      orbit.objectList.add(a1);
      //最后的分组有空余
      for (Map<GameTrack, List<Athlete>> map : orbit.gameGroup) {
        if (map.keySet().size() < orbit.numOfTracksInFile) {
          List<Athlete> athleteSet = new ArrayList<Athlete>();
          athleteSet.add(a1);
          GameTrack track = new GameTrack(map.keySet().size() + 1);
          orbit.addTrack(track);
          map.put(track, athleteSet);
          flag = true;
        }
      }
      if (!flag) {
        //分组无空余
        List<Athlete> athleteSet = new ArrayList<Athlete>();
        athleteSet.add(a1);
        GameTrack track = new GameTrack(1);
        orbit.addTrack(track);
        Map<GameTrack, List<Athlete>> map = new HashMap<GameTrack, List<Athlete>>();
        map.put(track, athleteSet);
        orbit.gameGroup.add(map);
      }
      logger.debug("TrackGame has added a athlete");
    }
    logger.info("TrackGame try to add athlete before init");
  }

  /**
   * 比较本系统和C2系统的不同.
   *
   * @param c2 另一个系统
   * @return difference类
   */
  public TrackGameDifference getDifference(TrackGameOrbit c2) {
    TrackGameDifference difference = null;
    if (inited) {
      if (orbit != null && c2 != null) {
        difference = (TrackGameDifference) c1Api.getDifference(orbit, c2);
        logger.debug("TrackGame has take difference");
      } else {
        logger.info("TrackGame try to take difference with an empty system");
      }
    } else {
      logger.info("TrackGame try to get difference before init");
    }
    return difference;
  }
}
