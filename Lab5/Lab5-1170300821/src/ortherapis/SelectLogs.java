package ortherapis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectLogs {

  String filename = "src//logs//debug.log";
  String regx = "([\\d-]+)\\s+([\\d:]+)\\s+(\\w+:\\d)"
      + "\\s+-\\s+([\\w._\\d]+):([\\w._\\d]+)\\s+-\\s+(\\w+)\\s+([\\w\\s,.?;:]+)";

  public String[][] selectByLevel(String level) {
    level = level.toUpperCase();
    return select(level, 6);
  }

  public String[][] selectByObject(String object) {
    return select(object, 4);
  }

  public String[][] selectByMethod(String method) {
    return select(method, 5);
  }

  public String[][] selectByTime(String time) {
    return select(time, 1);
  }

  private String[][] select(String item, int x) {
    int counter = 0;
    try {
      BufferedReader bfreader = new BufferedReader(new FileReader(filename));
      String templine = null;
      while ((templine = bfreader.readLine()) != null) {
        templine = templine.trim();
        Pattern pattern1 = Pattern.compile(regx);
        Matcher matcher = pattern1.matcher(templine);
        if (matcher.matches()) {
          if (matcher.group(x).equals(item)) {
            counter++;
          }
        }
      }
      bfreader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String[][] result = new String[counter][4];

    try {
      BufferedReader bfreader = new BufferedReader(new FileReader(filename));
      String templine = null;
      int i = 0;
      int j = 0;
      while ((templine = bfreader.readLine()) != null) {
        templine = templine.trim();
        Pattern pattern1 = Pattern.compile(regx);
        Matcher matcher = pattern1.matcher(templine);
        if (matcher.matches()) {
          if (matcher.group(x).equals(item)) {
            result[i][0] = matcher.group(1) + "\t" + matcher.group(2);
            result[i][1] = matcher.group(3);
            result[i][2] = matcher.group(4) + ":" + matcher.group(5);
            result[i][3] = matcher.group(6) + "\t" + matcher.group(7);
            i++;
          }
        }
      }
      bfreader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 打印log.
   * @param result .
   */
  public void printLogs(String[][] result) {
    for (int i = 0; i < result.length; i++) {
      System.out.println("时间：  " + result[i][0]);
      System.out.println("线程：  " + result[i][1]);
      System.out.println("类和方法：  " + result[i][2]);
      System.out.println("信息：  " + result[i][3]);
      System.out.println();
    }
  }
}
