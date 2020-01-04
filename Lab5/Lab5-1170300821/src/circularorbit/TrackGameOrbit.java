package circularorbit;

import exception.ObjectExp;
import exception.ReadFileExp;
import exception.RelationExp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import physicalobject.Athlete;
import track.GameTrack;
import track.Track;

public class TrackGameOrbit extends ConcreteCircularOrbit {

  public List<GameTrack> trackSet = new ArrayList<>();//轨道的集合
  public List<Athlete> objectList = new ArrayList<>(1000000);//轨道上物体的集合
  //每组一个map，每个track对应一个athlete的set
  public List<Map<GameTrack, List<Athlete>>> gameGroup = new ArrayList<>();
  public String gameType = "";
  public int numOfTracksInFile = 0;
  public int tracksInSet = 0;

  public Set<String> names = new HashSet<>(1000000);
  public Logger logger = Logger.getLogger(TrackGameOrbit.class);
  public boolean readflag;

  //TrackGame中无中心物体
  private void checkRep() {
    if (gameGroup.size() != 0) {
      for (int i = 0; i < gameGroup.size() - 1; i++) {
        assert numOfTracksInFile == gameGroup.get(i).size();
      }
      assert trackSet.size() % numOfTracksInFile == gameGroup.get(gameGroup.size() - 1).size();
      int counter = 0;
      for (int i = 0; i < gameGroup.size(); i++) {
        counter += gameGroup.get(i).size();
      }
      assert counter == objectList.size() || counter == objectList.size() / 4;
    }
    tracksInSet = trackSet.size();
  }


  /**
   * 增加一条轨道.
   */
  @Override
  public void addTrack(Track t) {
    trackSet.add((GameTrack) t);
    checkRep();
  }

  /**
   * 增加一条轨道.
   */
  @Override
  public void removeTrack(Track t) {
    Iterator<GameTrack> it = trackSet.iterator();
    while (it.hasNext()) {
      Track temp = it.next();
      if (temp.equal(t)) {
        it.remove();
      }
    }
    checkRep();
  }

  @Override
  public void addObjectToTrack(Object object, Track t) {
    objectList.add((Athlete) object);
    for (int i = 0; i < gameGroup.size(); i++) {
      Map<GameTrack, List<Athlete>> map = gameGroup.get(i);
      List<Track> list = new ArrayList<>(map.keySet());
      for (int j = 0; j < map.keySet().size(); j++) {
        if (list.get(j).equal(t)) {
          map.get(list.get(i)).add((Athlete) object);
        }
      }
    }
  }

  /**
   * 仅在分组关系中去除一个成员，不涉及objectSet和轨道的调整.
   */
  public void removeObject(GameTrack track, Athlete athlete) {
    Iterator<Athlete> it = objectList.iterator();
    while (it.hasNext()) {
      Athlete temp = it.next();
      if (temp.equals(athlete)) {
        it.remove();
      }
    }
    checkRep();

    for (Map<GameTrack, List<Athlete>> map : gameGroup) {
      //遍历组
      for (GameTrack key : map.keySet()) {
        //遍历track
        if (key.equal(track)) {
          //找到后删除
          Iterator<Athlete> athleteIt = map.get(key).iterator();
          while ((athleteIt.hasNext())) {
            Athlete athleteTemp = athleteIt.next();
            if (athleteTemp.equals(athlete)) {
              athleteIt.remove();
            }
          }
        }
      }
    }
  }

  /**
   * 从文件读入运动员信息，比赛类型和每组赛道数目.
   */
  @Override
  public boolean readFiles(String filename) {
    boolean flag = true;
    StringBuilder exp = new StringBuilder();
    try {
      BufferedReader bfReader = new BufferedReader(new FileReader(filename));
      String tempLine = null;
      Pattern pattern1 = Pattern
          .compile("Athlete\\s::=\\s<([\\w]+),([\\d]+),([A-Z]{3}),([\\d]+),(\\d{1,2}\\.\\d{2})>");
      Pattern pattern2 = Pattern.compile("Game\\s::=\\s([*\\d]+)");
      Pattern pattern3 = Pattern.compile("NumOfTracks\\s::=\\s(\\d+)");

      while ((tempLine = bfReader.readLine()) != null) {
        tempLine = tempLine.trim();
        try {
          //ReadFileExp.assertTrue(checkSpace(tempLine), "空格数量有误");
          //          if (tempLine.charAt(0) == 'A' && !checkAtheleMassage(tempLine)) {
          //            ReadFileExp.assertTrue(checkAtheleMassage(tempLine), "运动员信息不足");
          //          }

          Matcher matcher1 = pattern1.matcher(tempLine);
          if (matcher1.matches()) {
            //匹配运动员

            String name = matcher1.group(1);
            //判断姓名，错误的不计入
            ReadFileExp.assertTrue(checkName(name), "姓名有误");
            //ObjectExp.assertTrue(checkSameAthlete(name), "出现重复运动员");

            int number = Integer.valueOf(matcher1.group(2));
            String country = matcher1.group(3);
            int age = Integer.valueOf(matcher1.group(4));
            double grade = Double.valueOf(matcher1.group(5));
            Athlete athlete = new Athlete(name, number, country, age, grade);
            objectList.add(athlete);
            names.add(name);
          }

          Matcher matcher2 = pattern2.matcher(tempLine);
          if (matcher2.matches()) {
            //匹配游戏类型
            if (!checkGameType(matcher2.group(1))) {
              ReadFileExp.assertTrue(checkGameType(matcher2.group(1)), "比赛类型有误");
              flag = false;
            }
            gameType = matcher2.group(1);
          }

          Matcher matcher3 = pattern3.matcher(tempLine);
          if (matcher3.matches()) {
            //匹配每组轨道数
            int number = Integer.valueOf(matcher3.group(1));
            if (!checkTrackNumber(number)) {
              ReadFileExp.assertTrue(checkTrackNumber(number), "每组轨道数有误");
              flag = false;
            }
            numOfTracksInFile = number;
          }
        } catch (ReadFileExp | ObjectExp | RelationExp e1) {
          exp.append(e1.toString());
          if (!flag) {
            logger.error(e1.toString() + "\t解决：重新读文件");
            break;
          } else {
            logger.info(e1.toString() + "\t解决：跳过该行");
            continue;
          }
        }

      }
      bfReader.close();
      if (exp.length() != 0) {
        System.out.println(exp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  /**
   * 判断句子中空格数量.
   */
  public boolean checkSpace(String line) {
    int counter = 0;
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == ' ') {
        counter++;
      }
    }
    return counter >= 2 || line.equals("") || line.equals("\n") || line.equals("\r\n");
  }

  /**
   * 检查姓名.
   */
  public boolean checkName(String name) {
    if (Character.isUpperCase(name.charAt(0))) {
      for (int i = 1; i < name.length(); i++) {
        if (Character.isUpperCase(name.charAt(i))) {
          return false;
        }

      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * 检查比赛类型.
   */
  public boolean checkGameType(String type) {
    boolean f1 = type.equals("100");
    boolean f2 = type.equals("200");
    boolean f3 = type.equals("400");
    boolean f4 = type.equals("800");
    boolean f5 = type.equals("1500");
    boolean f6 = type.equals("3000");
    boolean f7 = type.equals("5000");
    boolean f8 = type.equals("110L");
    boolean f9 = type.equals("4*100");
    boolean f10 = type.equals("4*400");

    return f1 || f2 || f3 || f4 || f5 || f6 || f7 || f8 || f9 || f10;
  }

  /**
   * 检查每组轨道数目.
   */
  public boolean checkTrackNumber(int x) {
    return x <= 20 && x >= 1;
  }

  /**
   * 判断运动员信息是否足够.
   */
  public boolean checkAtheleMassage(String line) {
    String[] line1 = line.split("<");
    String[] line2 = line1[1].split(",");
    return line2.length == 5;
  }

  /**
   * 检查重复运动员.
   */
  private boolean checkSameAthlete(String name) {
    return names.contains(name);
  }
}