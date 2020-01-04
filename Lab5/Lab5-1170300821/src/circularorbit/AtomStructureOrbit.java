package circularorbit;

import centralobject.AtomCentralObject;
import centralobject.nucleon.Neutron;
import centralobject.nucleon.Nucleon;
import centralobject.nucleon.NucleonCache;
import centralobject.nucleon.Proton;
import exception.ObjectExp;
import exception.ReadFileExp;
import exception.RelationExp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import physicalobject.Electron;
import physicalobject.flyweight.ElectronFactory;
import track.AtomStructureTrack;

public class AtomStructureOrbit extends circularorbit.ConcreteCircularOrbit {

  public String atomName = new String();
  public int electronTrackNumber = 0;//电子轨道数量
  public int[] electronTracks = new int[20];//记录每个轨道上电子数
  public List<Electron> objectSet = new ArrayList<Electron>();//轨道上物体的集合
  public List<Nucleon> centralObjectSet = new ArrayList<>();
  public List<AtomStructureTrack> trackSet = new ArrayList<AtomStructureTrack>();//轨道的集合
  // public Map<AtomStructureTrack, Set<Electron>> relationMap = new HashMap<>();//轨道和物体的对应关系
  public Logger logger = Logger.getLogger(AtomStructureOrbit.class);


  @Override
  public boolean readFiles(String filename) {
    StringBuilder exp = new StringBuilder();
    boolean flag = true;
    try {
      BufferedReader bfReader = new BufferedReader(new FileReader(filename));
      Pattern pattern1 = Pattern.compile("ElementName\\s::=\\s([A-Za-z]+)");
      Pattern pattern2 = Pattern.compile("NumberOfTracks\\s::=\\s([\\d]+)");
      Pattern pattern3 = Pattern.compile("NumberOfElectron\\s::=\\s(\\d/\\d+;?)+");
      Pattern pattern4 = Pattern.compile("NumberOfNucleon\\s::\\s(\\d+);(\\d+)");
      String temp = null;
      while ((temp = bfReader.readLine()) != null) {
        try {
          temp = temp.trim();

          ReadFileExp.assertTrue(checkSpace(temp), "空格数量有误");

          Matcher matcher1 = pattern1.matcher(temp);
          Matcher matcher2 = pattern2.matcher(temp);
          Matcher matcher3 = pattern3.matcher(temp);
          Matcher matcher4 = pattern4.matcher(temp);
          //匹配原子名
          if (matcher1.matches()) {
            ReadFileExp.assertTrue(checkName(matcher1.group(1)), "原子名错误");
            atomName = matcher1.group(1);
            NucleonCache.loadmap(atomName);
          }
          //匹配轨道数
          if (matcher2.matches()) {
            electronTrackNumber = Integer.valueOf(matcher2.group(1));
          }
          //匹配电子排布
          if (matcher3.matches()) {
            String[] temp1 = matcher3.group().split(" ");
            String[] temp2 = temp1[temp1.length - 1].split(";");//取出电子分布的数据
            RelationExp.assertTrue(checkequal(electronTrackNumber, temp2.length), "轨道数不匹配");
            ReadFileExp.assertTrue(checkTrackOrder(temp1[temp1.length - 1]), "轨道信息的顺序错误");
            ReadFileExp.assertTrue(checkElectron(temp1[temp1.length - 1]), "电子排布错误");

            for (int i = 0; i < temp2.length; i++) {
              String[] temp3 = temp2[i].split("/");
              electronTracks[Integer.valueOf(temp3[0])] = Integer.valueOf(temp3[1]);
              for (int j = 0; j < Integer.valueOf(temp3[1]); j++) {
                //objectSet.add(new Electron(atomName));
                Electron electron = (Electron) ElectronFactory.getElectron(atomName);
                objectSet.add(electron);
              }
            }
          }

          if (matcher4.matches()) {
            int a = Integer.valueOf(matcher4.group(1));
            int b = Integer.valueOf(matcher4.group(2));
            for (int i = 0; i < a; i++) {
              Proton proton = (Proton) NucleonCache.getNucleon("1");
              centralObjectSet.add(proton);
            }
            for (int i = 0; i < b; i++) {
              Neutron neutron = (Neutron) NucleonCache.getNucleon("0");
              centralObjectSet.add(neutron);
            }
          }
        } catch (ReadFileExp e1) {
          exp.append(e1.toString());
          logger.error(e1.toString() + "\t解决：重新读文件");
          flag = false;
          break;
        } catch (ObjectExp e2) {
          exp.append(e2.toString());
          logger.error(e2.toString() + "\t解决：重新读文件");
          flag = false;
          break;
        } catch (RelationExp e3) {
          exp.append(e3.toString());
          logger.error(e3.toString() + "\t解决：重新读文件");
          flag = false;
          break;
        }
      }
      for (int i = 1; i <= electronTrackNumber; i++) {
        AtomStructureTrack track = new AtomStructureTrack(i, atomName);
        trackSet.add(track);
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
  private boolean checkSpace(String line) {
    int counter = 0;
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == ' ') {
        counter++;
      }
    }
    return counter >= 2;
  }

  /**
   * 检查名字.
   */
  private boolean checkName(String name) {
    if (name.length() == 1) {
      return Character.isUpperCase(name.charAt(0));
    } else if (name.length() == 2) {
      return Character.isUpperCase(name.charAt(0)) && Character.isLowerCase(name.charAt(1));
    }
    return false;
  }

  /**
   * 判断相等.
   */
  private boolean checkequal(int a, int b) {
    return a == b;
  }

  /**
   * 检查轨道信息的顺序.
   */
  private boolean checkTrackOrder(String line) {
    String[] line1 = line.split(";");
    for (int i = 0; i < line1.length - 1; i++) {
      if (line1[i].charAt(0) >= line1[i + 1].charAt(0)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查电子排布.
   */
  private boolean checkElectron(String line) {
    String[] line1 = line.split(";");
    if (line1.length == 2) {
      return line1[0].charAt(2) == '2';
    } else if (line1.length >= 3) {
      return line1[1].charAt(2) == '8';
    }
    return true;
  }


}
