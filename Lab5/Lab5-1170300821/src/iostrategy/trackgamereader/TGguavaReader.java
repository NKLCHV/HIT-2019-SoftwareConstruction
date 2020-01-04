package iostrategy.trackgamereader;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.TrackGameOrbit;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import exception.ObjectExp;
import exception.ReadFileExp;
import exception.RelationExp;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.Athlete;
import iostrategy.Readers;

public class TGguavaReader implements Readers {

  @Override
  public TrackGameOrbit readfile(ConcreteCircularOrbit orbit0, String filename) {
    TrackGameOrbit orbit = (TrackGameOrbit) orbit0;
    StringBuilder exp = new StringBuilder();
    Pattern pattern1 = Pattern
        .compile("Athlete\\s::=\\s<([\\w]+),([\\d]+),([A-Z]{3}),([\\d]+),(\\d{1,2}\\.\\d{2})>");
    Pattern pattern2 = Pattern.compile("Game\\s::=\\s([*\\d]+)");
    Pattern pattern3 = Pattern.compile("NumOfTracks\\s::=\\s([\\d])");
    try {
      Files.asCharSource(new File(filename), Charset.defaultCharset()).readLines(new LineProcessor<String>() {
            public boolean processLine(String line) throws IOException {
              if (line != null) {
                try {
                  String tempLine = line.trim();
                  ReadFileExp.assertTrue(orbit.checkSpace(tempLine), "空格数量有误");
//                  if (tempLine.charAt(0) == 'A' && !orbit.checkAtheleMassage(tempLine)) {
//                    ReadFileExp.assertTrue(orbit.checkAtheleMassage(tempLine), "运动员信息不足");
//                  }
                  Matcher matcher1 = pattern1.matcher(tempLine);
                  Matcher matcher2 = pattern2.matcher(tempLine);
                  Matcher matcher3 = pattern3.matcher(tempLine);

                  if (matcher1.matches()) {
                    //匹配运动员

                    String name = matcher1.group(1);
                    //判断姓名，错误的不计入
                    ReadFileExp.assertTrue(orbit.checkName(name), "姓名有误");
                    //ObjectExp.assertTrue(checkSameAthlete(name), "出现重复运动员");

                    int number = Integer.valueOf(matcher1.group(2));
                    String country = matcher1.group(3);
                    int age = Integer.valueOf(matcher1.group(4));
                    double grade = Double.valueOf(matcher1.group(5));
                    Athlete athlete = new Athlete(name, number, country, age, grade);
                    orbit.objectList.add(athlete);
                    orbit.names.add(name);
                  } else if (matcher2.matches()) {
                    //匹配游戏类型
                    if (!orbit.checkGameType(matcher2.group(1))) {
                      ReadFileExp.assertTrue(orbit.checkGameType(matcher2.group(1)), "比赛类型有误");
                      orbit.readflag = false;
                    }
                    orbit.gameType = matcher2.group(1);
                  } else if (matcher3.matches()) {
                    //匹配每组轨道数
                    int number = Integer.valueOf(matcher3.group(1));
                    if (!orbit.checkTrackNumber(number)) {
                      ReadFileExp.assertTrue(orbit.checkTrackNumber(number), "每组轨道数有误");
                      orbit.readflag = false;
                    }
                    orbit.numOfTracksInFile = number;
                  }
                } catch (ReadFileExp | ObjectExp | RelationExp e1) {
                  exp.append(e1.toString());
                  if (orbit.readflag) {
                    orbit.logger.error(e1.toString() + "\t解决：重新读文件");
                  } else {
                    orbit.logger.info(e1.toString() + "\t解决：跳过该行");
                  }
                }
              }

              //这里处理一行字符串
              return true;//如果是false，则会中断读取操作
            }

            public String getResult() {
              return null;//返回的结果。可以自定义
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }

    return orbit;
  }
}
