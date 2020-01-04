package APIs;


import circularOrbit.CircularOrbit;
import circularOrbit.TrackGameOrbit;
import ortherAPIs.Difference;
import ortherAPIs.TrackGameDifference;
import physicalObject.Athlete;
import track.GameTrack;

import java.util.*;

public class TrackGameAPI extends CircularOrbitAPIs {

    /**
     * 计算逻辑距离
     * @param c
     * @param e1
     * @param e2
     * @return
     */
    @Override
    public int getLogicalDistance(CircularOrbit c, Object e1, Object e2) {
        TrackGameOrbit C1 = (TrackGameOrbit) c;
        Athlete a1 = (Athlete) e1;
        Athlete a2 = (Athlete) e2;
        for (int i = 0; i < C1.gameGroup.size(); i++) {
            GameTrack track1 = null;
            GameTrack track2 = null;
            Map<GameTrack, List<Athlete>> map = C1.gameGroup.get(i);
            GameTrack[] trackArrayList = (GameTrack[]) map.keySet().toArray();
            for (int i1 = 0; i < map.keySet().size(); i++) {
                GameTrack track = trackArrayList[i1];
                if (map.get(track).contains(a1))
                    track1 = track;
                if (map.get(track).contains(a2))
                    track2 = track;
            }

            if (track1 != null && track2 != null) {
                return Math.abs(track1.getTrackRadius() - track2.getTrackRadius());
            }
        }
        return Integer.MAX_VALUE;
    }

    /**根据位置找运动员
     * @param C1
     * @param group
     * @param track
     * @param i
     * @return
     */
    public Athlete findAthlete(TrackGameOrbit C1, int group, int track, int i) {
        Athlete athlete = null;
        for (GameTrack track1 : C1.gameGroup.get(group).keySet()) {
            if (track1.getTrackRadius() == track) {
                return C1.gameGroup.get(group).get(track1).get(i);
            }
        }

        return null;
    }

    /**计算不同之处
     * @param c1
     * @param c2
     * @return
     */
    @Override
    public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        TrackGameDifference trackGameDifference = new TrackGameDifference();
        TrackGameOrbit trackGameC1 = (TrackGameOrbit) c1;
        TrackGameOrbit trackGameC2 = (TrackGameOrbit) c2;

        trackGameDifference.DobjectsNum = trackGameC1.objectSet.size() - trackGameC2.objectSet.size();
        trackGameDifference.DtracksNum = trackGameC1.trackSet.size() - trackGameC2.trackSet.size();

        Iterator<Map<GameTrack, List<Athlete>>> itC1 = trackGameC1.gameGroup.iterator();
        Iterator<Map<GameTrack, List<Athlete>>> itC2 = trackGameC2.gameGroup.iterator();

        while (itC1.hasNext() || itC2.hasNext()) {
            if (itC1.hasNext() && !itC2.hasNext()) {//迭代到1有分组，2没有分组的情况
                Map<GameTrack, List<String>> maptemp = new HashMap<>();
                Map<GameTrack, List<Athlete>> mapC1 = itC1.next();
                for (GameTrack track : mapC1.keySet()) //遍历每个跑道
                    maptemp.put(track, writeDifference(mapC1, track));
                trackGameDifference.DgameGroup.add(maptemp);
            } else if (!itC1.hasNext() && itC2.hasNext()) {//迭代到2有分组，1没有分组的情况
                Map<GameTrack, List<String>> maptemp = new HashMap<>();
                Map<GameTrack, List<Athlete>> mapC2 = itC2.next();
                for (GameTrack track : mapC2.keySet()) //遍历每个跑道
                    maptemp.put(track, writeDifference(mapC2, track));
                trackGameDifference.DgameGroup.add(maptemp);
            } else {
                Map<GameTrack, List<Athlete>> mapC1 = itC1.next();
                Map<GameTrack, List<Athlete>> mapC2 = itC2.next();
                Map<GameTrack, List<String>> maptemp = new HashMap<>();
                Iterator<GameTrack> itTrackC1 = mapC1.keySet().iterator();
                Iterator<GameTrack> itTrackC2 = mapC2.keySet().iterator();

                while (itTrackC1.hasNext() || itTrackC2.hasNext()) {
                    if (itTrackC1.hasNext() && !itTrackC2.hasNext()) {//分组对应,跑道不对应的情况
                        GameTrack track = itTrackC1.next();
                        maptemp.put(track, writeDifference(mapC1, track));
                    } else if (!itTrackC1.hasNext() && itTrackC2.hasNext()) {//分组对应,跑道不对应的情况
                        GameTrack track = itTrackC2.next();
                        maptemp.put(track, writeDifference(mapC2, track));
                    } else {//分组和跑道一一对应
                        GameTrack track1 = itTrackC1.next();
                        GameTrack track2 = itTrackC2.next();
                        List<Athlete> athleteSet1 = mapC1.get(track1);
                        List<Athlete> athleteSet2 = mapC2.get(track2);
                        List<String> names = new ArrayList<>();
                        Iterator<Athlete> athleteIt1 = athleteSet1.iterator();
                        Iterator<Athlete> athleteIt2 = athleteSet2.iterator();
                        while (athleteIt1.hasNext() && athleteIt2.hasNext()) {
                            names.add(athleteIt1.next().getObjectName() + "-" + athleteIt2.next().getObjectName());
                        }
                        maptemp.put(track1, names);
                    }
                }
                trackGameDifference.DgameGroup.add(maptemp);
            }
        }
        return trackGameDifference;
    }

    /**
     *
     * @param trackC
     * @return
     */
    public TrackGameOrbit randomSort(TrackGameOrbit trackC) {
        int groupSize = trackC.numOfTracksInFile;
        List<Map<GameTrack, List<Athlete>>> mapList = new ArrayList<>();
        Map<GameTrack, List<Athlete>> map = new HashMap<>();
        mapList.add(map);
        int c = 0;
        for (int i = 0; i < trackC.objectSet.size(); i++) {
            GameTrack track = new GameTrack(i % groupSize + 1);
            Athlete athlete = trackC.objectSet.get(i);
            List<Athlete> athleteSet = new ArrayList<>();
            athleteSet.add(athlete);
            trackC.trackSet.add(track);
            mapList.get(c).put(track, athleteSet);
            if (i % groupSize == groupSize - 1 || i == trackC.objectSet.size() - 1) {
                trackC.gameGroup.add(mapList.get(c));
                Map<GameTrack, List<Athlete>> tmap = new HashMap<>();
                mapList.add(tmap);
                c++;
            }
        }
        return trackC;
    }

    public TrackGameOrbit S2B_Sort(TrackGameOrbit trackC) {
        return trackC;
    }

    public boolean moveInGroup(TrackGameOrbit C1, Athlete a1, Athlete a2) {
        for (int i = 0; i < C1.gameGroup.size(); i++) {
            GameTrack track1 = null;
            GameTrack track2 = null;
            Map<GameTrack, List<Athlete>> map = C1.gameGroup.get(i);
            GameTrack[] trackArrayList = (GameTrack[]) map.keySet().toArray();
            for (int i1 = 0; i < map.keySet().size(); i++) {
                GameTrack track = trackArrayList[i1];
                if (map.get(track).contains(a1))
                    track1 = track;
                if (map.get(track).contains(a2))
                    track2 = track;
            }

            if (track1 != null && track2 != null) {
                List<Athlete> s1 = map.get(track1);
                List<Athlete> s2 = map.get(track2);
                map.remove(track1);
                map.remove(track2);
                map.put(track1, s2);
                map.put(track2, s1);
                return true;
            }
        }

        return false;
    }

    public boolean moveBetweenGroup(TrackGameOrbit C1, Athlete a1, Athlete a2) {
        GameTrack track1 = null;
        GameTrack track2 = null;
        Map<GameTrack, List<Athlete>> map1 = null;
        Map<GameTrack, List<Athlete>> map2 = null;
        for (int i = 0; i < C1.gameGroup.size(); i++) {
            Map<GameTrack, List<Athlete>> map = C1.gameGroup.get(i);
            //遍历找到运动员所在的组和跑道
            GameTrack[] trackArrayList = (GameTrack[]) map.keySet().toArray();
            for (int i1 = 0; i < map.keySet().size(); i++) {
                GameTrack track = trackArrayList[i1];
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
            Iterator<Map<GameTrack, List<Athlete>>> mapIt = C1.gameGroup.iterator();
            while (mapIt.hasNext()) {
                Map<GameTrack, List<Athlete>> map = mapIt.next();
                if (map == map1 || map == map2) {
                    mapIt.remove();
                }
            }
            //对换运动员后加入set
            List<Athlete> s1 = map1.get(track1);
            List<Athlete> s2 = map2.get(track2);
            map1.remove(track1);
            map2.remove(track2);
            map1.put(track1, s2);
            map2.put(track2, s1);
            C1.gameGroup.add(map1);
            C1.gameGroup.add(map2);
            return true;
        }
        return false;
    }

    private List<String> writeDifference(Map<GameTrack, List<Athlete>> mapC, GameTrack track) {
        List<Athlete> athleteSet = mapC.get(track);
        List<String> names = new ArrayList<>();
        for(int i = 0;i<athleteSet.size();i++){
            Athlete athlete = athleteSet.get(i);
            names.add("null-" + athlete.getObjectName());
        }
        return names;
    }
}
