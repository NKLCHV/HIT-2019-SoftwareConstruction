package iostrategy.socialnetworkcirclereader;

import centralobject.SocialNetworkCentralObject;
import circularorbit.ConcreteCircularOrbit;
import circularorbit.SocialNetworkCircleOrbit;
import exception.ObjectExp;
import exception.ReadFileExp;
import exception.RelationExp;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.Friend;
import iostrategy.Readers;

public class SNCstreamReader implements Readers {

  @Override
  public ConcreteCircularOrbit readfile(ConcreteCircularOrbit orbit0, String filename) {
    SocialNetworkCircleOrbit orbit = (SocialNetworkCircleOrbit) orbit0;
    StringBuilder exp = new StringBuilder();
    try {
      FileInputStream inputStream = new FileInputStream(filename);
      BufferedReader bfReader = new BufferedReader(new InputStreamReader(inputStream));
      Pattern pattern1 = Pattern
          .compile("CentralUser\\s::=\\s<([A-Za-z0-9]+),\\s*([\\d]+),\\s*([\\w])>");
      Pattern pattern2 = Pattern
          .compile("Friend\\s::=\\s<([A-Za-z0-9]+),\\s*([\\d]+),\\s*([\\w])>");
      Pattern pattern3 = Pattern
          .compile("SocialTie\\s::=\\s<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(1|0.[\\d]{1,3})>");
      String temp = new String();
      while ((temp = bfReader.readLine()) != null) {
        try {
          temp = temp.trim();
          ReadFileExp.assertTrue(orbit.checkSpace(temp), "空格数量有误");

          Matcher matcher1 = pattern1.matcher(temp);
          Matcher matcher2 = pattern2.matcher(temp);
          Matcher matcher3 = pattern3.matcher(temp);

          if (matcher1.matches()) {
            //匹配中心用户
            if (!orbit.checkGender(matcher1.group(3))) {
              ReadFileExp.assertTrue(orbit.checkGender(matcher1.group(3)), "中心用户性别错误");
              orbit.readflag = false;
            }

            int age = Integer.valueOf(matcher1.group(2));
            if (!orbit.checkAge(age)) {
              ReadFileExp.assertTrue(orbit.checkAge(age), "中心用户年龄错误");
              orbit.readflag = false;
            }
            orbit.centralObject = new SocialNetworkCentralObject(matcher1.group(1), age,
                matcher1.group(3));
            orbit.central = new Friend(matcher1.group(1), age, matcher1.group(3));//用于在set中标记关系
            orbit.objectSet.add(orbit.central);
            orbit.findName.put(matcher1.group(1), orbit.central);
            List<Friend> friendSet = new ArrayList<>();
            orbit.friendship.put(orbit.central, friendSet);
          }

          if (matcher2.matches()) {
            //匹配朋友
            ReadFileExp.assertTrue(orbit.checkGender(matcher2.group(3)), "朋友性别错误");
            int age = Integer.valueOf(matcher2.group(2));
            ReadFileExp.assertTrue(orbit.checkAge(age), "用户年龄错误");
            //ObjectExp.assertTrue(checkSameFriend(matcher2.group(1)), "出现重名朋友");
            Friend friend = new Friend(matcher2.group(1), age, matcher2.group(3));
            orbit.objectSet.add(friend);
            orbit.findName.put(matcher2.group(1), friend);
            List<Friend> friendSet = new ArrayList<>();
            orbit.friendship.put(friend, friendSet);
          }
        } catch (ReadFileExp | ObjectExp | RelationExp e1) {
          exp.append(e1.toString());
          if (!orbit.readflag) {
            orbit.logger.error(e1.toString() + "\t解决：重新读文件");
            break;
          } else {
            orbit.logger.info(e1.toString() + "\t解决：跳过该行");
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

    try {
      FileInputStream inputStream = new FileInputStream(filename);
      BufferedReader bfReader = new BufferedReader(new InputStreamReader(inputStream));
      Pattern pattern3 = Pattern
          .compile("SocialTie\\s::=\\s<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(1|0.[\\d]{1,3})>");
      String temp = new String();
      while ((temp = bfReader.readLine()) != null) {
        try {
          temp = temp.trim();
          ReadFileExp.assertTrue(orbit.checkSpace(temp), "空格数量有误");
          Matcher matcher3 = pattern3.matcher(temp);
          if (matcher3.matches()) {
            //匹配朋友关系
            String name1 = matcher3.group(1);
            String name2 = matcher3.group(2);
            double intimacy = Double.valueOf(matcher3.group(3));
            //ObjectExp.assertTrue(checkIntimacy(name1, name2, intimacy), "亲密度冲突");
            //RelationExp.assertTrue(checkContainFriend(name1), "friend不存在");
            //RelationExp.assertTrue(checkContainFriend(name2), "friend不存在");

            Friend friend1 = orbit.findName.get(name1);
            Friend friend2 = orbit.findName.get(name2);
            List<Friend> friendList = new ArrayList<>();
            friendList.add(friend1);
            friendList.add(friend2);
            orbit.objectRelation.put(friendList, intimacy);
            orbit.setFriendShip(friend1, friend2);
            orbit.setFriendShip(friend2, friend1);
          }

        } catch (ReadFileExp | ObjectExp | RelationExp e1) {
          orbit.logger.info(e1.toString() + "\t解决：跳过该行");
          continue;
        }
      }
      bfReader.close();
      if (exp.length() != 0) {
        System.out.println(exp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return orbit;
  }
}
