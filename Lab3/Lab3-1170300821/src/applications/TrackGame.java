package applications;

import APIs.TrackGameAPI;
import circularOrbit.TrackGameOrbit;
import ortherAPIs.TrackGameDifference;
import physicalObject.Athlete;
import track.GameTrack;

import java.util.*;

public class TrackGame {
    private TrackGameOrbit C1 = new TrackGameOrbit();
    private TrackGameAPI c1API = new TrackGameAPI();
    private boolean inited = false;

    public TrackGameOrbit getTrackGameOrbit() {
        return C1;
    }

    public void inti(String filename) {
        if (filename != null) {
            C1.readFiles(filename);
            inited = true;
        }
    }

    public void clear() {
        C1 = new TrackGameOrbit();
        inited = false;
    }

    /**
     * 编排分组
     *
     * @param choice 选择随机或者顺序
     */
    public void makeGroup(String choice) {
        if (inited) {
            if (choice.equals("random"))
                C1 = c1API.randomSort(C1);
            else if (choice.equals("S2B"))
                C1 = c1API.S2B_Sort(C1);
        }
    }

    /**
     * 交换两个运动员的分组赛道安排
     *
     * @param a1
     * @param a2
     */
    public void changeGroups(Athlete a1, Athlete a2) {
        if (inited) {
            boolean flag = false;
            for (Map<GameTrack, List<Athlete>> map : C1.gameGroup) {
                for (GameTrack track : map.keySet()) {
                    if (map.get(track).contains(a1) && map.get(track).contains(a2))
                        flag = true;
                }
            }
            if (flag)
                c1API.moveInGroup(C1, a1, a2);
            else
                c1API.moveBetweenGroup(C1, a1, a2);
        }
    }

    /**
     * 交换两个运动员的分组赛道安排
     *
     * @param group1 运动员1的位置
     * @param track1
     * @param i1
     * @param group2 运动员2的位置
     * @param track2
     * @param i2
     */
    public void changeGroups(int group1, int track1, int i1, int group2, int track2, int i2) {
        if (inited) {
            Athlete a1 = c1API.findAthlete(C1, group1, track1, i1);
            Athlete a2 = c1API.findAthlete(C1, group2, track2, i2);
            if (a1 != null && a2 != null)
                changeGroups(a1, a2);
        }
    }

    /**
     * 删除一个运动员，包括去除分组安排
     *
     * @param a1 运动员
     */
    public void deleteAthlete(Athlete a1) {
        if (inited) {
            GameTrack track = null;
            for (Map<GameTrack, List<Athlete>> map : C1.gameGroup) {
                for (GameTrack track1 : map.keySet()) {
                    if (map.get(track1).contains(a1))
                        track = track1;
                }
            }
            if (track != null) {
                C1.removeObject(track, a1);
                C1.objectSet.remove(a1);

                for (Map<GameTrack, List<Athlete>> map : C1.gameGroup) {
                    if (map.keySet().contains(track)) {
                        if (map.get(track).isEmpty()) {
                            map.remove(track);
                            C1.trackSet.remove(track);
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除一个运动员，包括去除分组安排
     *
     * @param group 运动员的位置
     * @param track
     * @param i
     */
    public void deleteAthlete(int group, int track, int i) {
        if (inited) {
            Athlete a1 = c1API.findAthlete(C1, group, track, i);
            if (a1 != null)
                deleteAthlete(a1);
        }
    }

    /**
     * 增加一个运动员，默认分到最后的小组
     *
     * @param a1
     */
    public void addAthlete(Athlete a1) {
        if (inited) {
            boolean flag = false;
            C1.objectSet.add(a1);
            //最后的分组有空余
            for (Map<GameTrack, List<Athlete>> map : C1.gameGroup) {
                if (map.keySet().size() < C1.numOfTracksInFile) {
                    List<Athlete> athleteSet = new ArrayList<>();
                    athleteSet.add(a1);
                    GameTrack track = new GameTrack(map.keySet().size() + 1);
                    C1.addTrack(track);
                    map.put(track, athleteSet);
                    flag = true;
                }
            }
            if (!flag) {//分组无空余
                List<Athlete> athleteSet = new ArrayList<>();
                athleteSet.add(a1);
                GameTrack track = new GameTrack(1);
                C1.addTrack(track);
                Map<GameTrack, List<Athlete>> map = new HashMap<>();
                map.put(track, athleteSet);
                C1.gameGroup.add(map);
            }
        }
    }

    /**
     * 比较本系统和C2系统的不同
     *
     * @param C2 另一个系统
     * @return difference类
     */
    public TrackGameDifference getDifference(TrackGameOrbit C2) {
        TrackGameDifference difference = null;
        if (inited) {
            if (C1 != null && C2 != null) {
                difference = (TrackGameDifference) c1API.getDifference(C1, C2);
            }
        }
        return difference;
    }
}
