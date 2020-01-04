package api;

import circularorbit.CircularOrbit;
import circularorbit.TrackGameOrbit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ortherapis.Difference;
import ortherapis.TrackGameDifference;
import physicalobject.Athlete;
import track.GameTrack;

public class TrackGameApi extends CircularOrbitApis {

  private void checkRep(TrackGameOrbit orbit) {
    if (orbit.gameGroup.size() != 0) {
      for (int i = 0; i < orbit.gameGroup.size() - 1; i++) {
        assert orbit.numOfTracksInFile == orbit.gameGroup.get(i).size();
      }
      assert orbit.trackSet.size() % orbit.numOfTracksInFile == orbit.gameGroup
          .get(orbit.gameGroup.size() - 1).size();
      int counter = 0;
      for (int i = 0; i < orbit.gameGroup.size(); i++) {
        counter += orbit.gameGroup.get(i).size();
      }
      assert counter == orbit.objectList.size() || counter == orbit.objectList.size() / 4;
    }
    orbit.tracksInSet = orbit.trackSet.size();
  }

  /**
   * 计算逻辑距离.
   */
  @Override
  public int getLogicalDistance(CircularOrbit c, Object e1, Object e2) {
    assert c != null && e1 != null && e2 != null;
    TrackGameOrbit c1 = (TrackGameOrbit) c;
    Athlete a1 = (Athlete) e1;
    Athlete a2 = (Athlete) e2;
    for (int i = 0; i < c1.gameGroup.size(); i++) {
      GameTrack track1 = null;
      GameTrack track2 = null;
      Map<GameTrack, List<Athlete>> map = c1.gameGroup.get(i);
      List<GameTrack> trackList = new ArrayList<GameTrack>(map.keySet());
      for (int j = 0; j < trackList.size(); j++) {
        if (map.get(trackList.get(i)).contains(a1)) {
          track1 = (GameTrack) trackList.get(i);
        }
        if (map.get(trackList.get(i)).contains(a2)) {
          track2 = (GameTrack) trackList.get(i);
        }
      }

      if (track1 != null && track2 != null) {
        return Math.abs(track1.getTrackRadius() - track2.getTrackRadius());
      }
    }
    checkRep(c1);
    return Integer.MAX_VALUE;
  }

  /**
   * 根据位置找运动员.
   */
  public Athlete findAthlete(TrackGameOrbit c1, int group, int track, int i) {
    assert c1 != null;
    Athlete athlete = null;
    List<GameTrack> trackList = new ArrayList<>(c1.gameGroup.get(group).keySet());
    for (int i1 = 0; i < trackList.size(); i++) {
      GameTrack track1 = trackList.get(i1);
      if (track1.getTrackRadius() == track) {
        return c1.gameGroup.get(group).get(track1).get(i - 1);
      }
    }
    checkRep(c1);
    return null;
  }

  /**
   * 计算不同之处.
   */
  @Override
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
    assert c1 != null && c2 != null;
    TrackGameDifference trackGameDifference = new TrackGameDifference();
    TrackGameOrbit trackGameC1 = (TrackGameOrbit) c1;
    TrackGameOrbit trackGameC2 = (TrackGameOrbit) c2;

    trackGameDifference.dobjectsNum = trackGameC1.objectList.size() - trackGameC2.objectList.size();
    trackGameDifference.dtracksNum = trackGameC1.trackSet.size() - trackGameC2.trackSet.size();

    Iterator<Map<GameTrack, List<Athlete>>> itC1 = trackGameC1.gameGroup.iterator();
    Iterator<Map<GameTrack, List<Athlete>>> itC2 = trackGameC2.gameGroup.iterator();

    while (itC1.hasNext() || itC2.hasNext()) {
      if (itC1.hasNext() && !itC2.hasNext()) {
        //迭代到1有分组，2没有分组的情况
        Map<GameTrack, List<String>> maptemp = new HashMap<GameTrack, List<String>>();
        Map<GameTrack, List<Athlete>> mapC1 = itC1.next();
        for (GameTrack track : mapC1.keySet()) {
          //遍历每个跑道
          maptemp.put(track, writeDifference(mapC1, track));
        }
        trackGameDifference.dgameGroup.add(maptemp);
      } else if (!itC1.hasNext() && itC2.hasNext()) {
        //迭代到2有分组，1没有分组的情况
        Map<GameTrack, List<String>> maptemp = new HashMap<GameTrack, List<String>>();
        Map<GameTrack, List<Athlete>> mapC2 = itC2.next();
        for (GameTrack track : mapC2.keySet()) {
          //遍历每个跑道
          maptemp.put(track, writeDifference(mapC2, track));
        }
        trackGameDifference.dgameGroup.add(maptemp);
      } else {
        Map<GameTrack, List<Athlete>> mapC1 = itC1.next();
        Map<GameTrack, List<Athlete>> mapC2 = itC2.next();
        Map<GameTrack, List<String>> maptemp = new HashMap<GameTrack, List<String>>();
        Iterator<GameTrack> itTrackC1 = mapC1.keySet().iterator();
        Iterator<GameTrack> itTrackC2 = mapC2.keySet().iterator();

        while (itTrackC1.hasNext() || itTrackC2.hasNext()) {
          if (itTrackC1.hasNext() && !itTrackC2.hasNext()) {
            //分组对应,跑道不对应的情况
            GameTrack track = itTrackC1.next();
            maptemp.put(track, writeDifference(mapC1, track));
          } else if (!itTrackC1.hasNext() && itTrackC2.hasNext()) {
            //分组对应,跑道不对应的情况
            GameTrack track = itTrackC2.next();
            maptemp.put(track, writeDifference(mapC2, track));
          } else {
            //分组和跑道一一对应
            GameTrack track1 = itTrackC1.next();
            GameTrack track2 = itTrackC2.next();
            List<Athlete> athleteSet1 = mapC1.get(track1);
            List<Athlete> athleteSet2 = mapC2.get(track2);
            List<String> names = new ArrayList<String>();
            Iterator<Athlete> athleteIt1 = athleteSet1.iterator();
            Iterator<Athlete> athleteIt2 = athleteSet2.iterator();
            while (athleteIt1.hasNext() && athleteIt2.hasNext()) {
              names
                  .add(athleteIt1.next().getObjectName() + "-" + athleteIt2.next().getObjectName());
            }
            maptemp.put(track1, names);
          }
        }
        trackGameDifference.dgameGroup.add(maptemp);
      }
    }
    checkRep(trackGameC1);
    checkRep(trackGameC2);
    assert trackGameDifference != null;
    return trackGameDifference;
  }

  /**
   * 随机编排.
   *
   * @param trackC .
   * @return .
   */
  public TrackGameOrbit randomSort(TrackGameOrbit trackC) {
    assert trackC != null;
    int groupSize = trackC.numOfTracksInFile;
    List<Map<GameTrack, List<Athlete>>> mapList = new ArrayList<Map<GameTrack, List<Athlete>>>();
    Map<GameTrack, List<Athlete>> map = new HashMap<GameTrack, List<Athlete>>();
    mapList.add(map);
    int c = 0;
    for (int i = 0; i < trackC.objectList.size(); i++) {
      GameTrack track = new GameTrack(i % groupSize + 1);
      Athlete athlete = trackC.objectList.get(i);
      List<Athlete> athleteSet = new ArrayList<Athlete>();
      athleteSet.add(athlete);
      trackC.trackSet.add(track);
      mapList.get(c).put(track, athleteSet);
      if (i % groupSize == groupSize - 1 || i == trackC.objectList.size() - 1) {
        trackC.gameGroup.add(mapList.get(c));
        Map<GameTrack, List<Athlete>> tmap = new HashMap<GameTrack, List<Athlete>>();
        mapList.add(tmap);
        c++;
      }
    }
    checkRep(trackC);
    assert trackC != null;
    return trackC;
  }

  /**
   * .
   *
   * @param trackC .
   * @return .
   */
  public TrackGameOrbit s2bSort(TrackGameOrbit trackC) {
    return trackC;
  }

  /**
   * 在分组内调整.
   *
   * @param c1 .
   * @param a1 .
   * @param a2 .
   * @return .
   */
  public TrackGameOrbit moveInGroup(TrackGameOrbit c1, Athlete a1, Athlete a2) {
    assert c1 != null && a1 != null && a2 != null;
    for (int i = 0; i < c1.gameGroup.size(); i++) {
      GameTrack track1 = null;
      GameTrack track2 = null;
      Map<GameTrack, List<Athlete>> map = c1.gameGroup.get(i);
      List<GameTrack> trackList = new ArrayList<GameTrack>(map.keySet());
      // GameTrack[] trackArrayList = (GameTrack[]) map.keySet().toArray();
      for (int i1 = 0; i < map.keySet().size(); i++) {
        GameTrack track = trackList.get(i);
        if (map.get(track).contains(a1)) {
          track1 = track;
        }
        if (map.get(track).contains(a2)) {
          track2 = track;
        }
      }

      if (track1 != null && track2 != null) {
        List<Athlete> s1 = map.get(track1);
        List<Athlete> s2 = map.get(track2);
        map.remove(track1);
        map.remove(track2);
        map.put(track1, s2);
        map.put(track2, s1);
      }
    }
    checkRep(c1);
    return c1;
  }

  /**
   * 在分组外调整.
   *
   * @param c1 .
   * @param a1 .
   * @param a2 .
   * @return .
   */
  public TrackGameOrbit moveBetweenGroup(TrackGameOrbit c1, Athlete a1, Athlete a2) {
    assert c1 != null && a1 != null && a2 != null;
    GameTrack track1 = null;
    GameTrack track2 = null;
    Map<GameTrack, List<Athlete>> map1 = null;
    Map<GameTrack, List<Athlete>> map2 = null;
    for (int i = 0; i < c1.gameGroup.size(); i++) {
      Map<GameTrack, List<Athlete>> map = c1.gameGroup.get(i);
      //遍历找到运动员所在的组和跑道
      List<GameTrack> trackList = new ArrayList<GameTrack>(map.keySet());
      for (int i1 = 0; i < map.keySet().size(); i++) {
        GameTrack track = trackList.get(i);
        if (map.get(track).contains(a1)) {
          track1 = track;
          map1 = map;
        }
        if (map.get(track).contains(a2)) {
          track2 = track;
          map2 = map;
        }
      }
    }
    if (map1 != null && map2 != null) {
      //先移除在添加修改后的两个组
      Iterator<Map<GameTrack, List<Athlete>>> mapIt = c1.gameGroup.iterator();
      while (mapIt.hasNext()) {
        Map<GameTrack, List<Athlete>> map = mapIt.next();
        if (map == map1 || map == map2) {
          mapIt.remove();
        }
      }
      //对换运动员后加入set
      final List<Athlete> s1 = map1.get(track1);
      final List<Athlete> s2 = map2.get(track2);
      map1.remove(track1);
      map2.remove(track2);
      map1.put(track1, s2);
      map2.put(track2, s1);
      c1.gameGroup.add(map1);
      c1.gameGroup.add(map2);
    }
    checkRep(c1);
    return c1;
  }

  /**
   * .
   *
   * @param mapC .
   * @param track .
   * @return .
   */
  public List<String> writeDifference(Map<GameTrack, List<Athlete>> mapC, GameTrack track) {
    assert mapC != null && track != null;
    List<Athlete> athleteSet = mapC.get(track);
    List<String> names = new ArrayList<String>();
    for (int i = 0; i < athleteSet.size(); i++) {
      Athlete athlete = athleteSet.get(i);
      names.add("null-" + athlete.getObjectName());
    }
    return names;
  }
}
