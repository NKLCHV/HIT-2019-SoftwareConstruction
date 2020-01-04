package circularOrbit;

import physicalObject.Athlete;
import track.GameTrack;
import track.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrackGameOrbit extends ConcreteCircularOrbit {
    public List<GameTrack> trackSet = new ArrayList<>();//轨道的集合
    public List<Athlete> objectSet = new ArrayList<>();//轨道上物体的集合
    //    private Map<GameTrack, Athlete> relationMap = new HashMap<>();//轨道和物体的对应关系
    public List<Map<GameTrack, List<Athlete>>> gameGroup = new ArrayList<>();//每组一个map，每个track对应一个athlete的set
    public String gameType = new String();
    public int numOfTracksInFile = 0;
    public int tracksInSet = 0;

    //TrackGame中无中心物体
    public void checkRep() {
        tracksInSet = trackSet.size();
    }

    /**
     * 增加一条轨道
     *
     * @param t
     */
    @Override
    public void addTrack(Track t) {
        trackSet.add((GameTrack) t);
        checkRep();
    }

    /**
     * 增加一条轨道
     *
     * @param t
     */
    @Override
    public void removeTrack(Track t) {
        Iterator<GameTrack> it = trackSet.iterator();
        while (it.hasNext()) {
            Track temp = it.next();
            if (temp.equal(t))
                it.remove();
        }
        checkRep();
    }

    @Override
    public void addObjectToTrack(Object object, Track t) {
        objectSet.add((Athlete) object);
        for (Map<GameTrack, List<Athlete>> map : gameGroup) {//遍历分组
            for (GameTrack track : map.keySet()) {//遍历跑道
                if (track.equal(t)) {
                    map.get(track).add((Athlete) object);
                }
            }
        }
    }

    /**
     * 仅在分组关系中去除一个成员，不涉及objectSet和轨道的调整
     * @param track
     * @param athlete
     */
    public void removeObject(GameTrack track, Athlete athlete) {
        Iterator<Athlete> it = objectSet.iterator();
        while (it.hasNext()) {
            Athlete temp = it.next();
            if (temp.equals(athlete))
                it.remove();
        }
        checkRep();

        for(Map<GameTrack, List<Athlete>> map : gameGroup){//遍历组
            for(GameTrack key : map.keySet()){//遍历track
                if(key.equal(track)){//找到后删除
                    Iterator<Athlete> athleteIt = map.get(key).iterator();
                    while ((athleteIt.hasNext())){
                        Athlete athleteTemp = athleteIt.next();
                        if(athleteTemp.equals(athlete)){
                            athleteIt.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * 从文件读入运动员信息，比赛类型和每组赛道数目
     * @param filename
     */
    @Override
    public void readFiles(String filename) {
        try {
            BufferedReader bfReader = new BufferedReader(new FileReader(filename));
            String tempLine = null;
            Pattern pattern1 = Pattern.compile("Athlete\\s::=\\s<([\\w]+),([\\d]+),([A-Z]{3}),([\\d]+),(\\d{1,2}\\.\\d{2})>");
            Pattern pattern2 = Pattern.compile("Game\\s::=\\s([\\d]{3})");
            Pattern pattern3 = Pattern.compile("NumOfTracks\\s::=\\s([\\d])");

            while ((tempLine = bfReader.readLine()) != null) {
                tempLine = tempLine.trim();
                Matcher matcher1 = pattern1.matcher(tempLine);
                if (matcher1.matches()) {//匹配运动员
                    //System.out.println(matcher1.group());
                    String name = matcher1.group(1);
                    int number = Integer.valueOf(matcher1.group(2));
                    String country = matcher1.group(3);
                    int age = Integer.valueOf(matcher1.group(4));
                    double grade = Double.valueOf(matcher1.group(5));
                    Athlete athlete = new Athlete(name, number, country, age, grade);
                    objectSet.add(athlete);
                }
                Matcher matcher2 = pattern2.matcher(tempLine);
                if (matcher2.matches()) {
                    gameType = matcher2.group(1);
                }
                Matcher matcher3 = pattern3.matcher(tempLine);
                if (matcher3.matches()) {
                    numOfTracksInFile = Integer.valueOf(matcher3.group(1));
                }
            }

            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
