package circularorbit;

import centralobject.SocialNetworkCentralObject;
import exception.ObjectExp;
import exception.ReadFileExp;
import exception.RelationExp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import physicalobject.Friend;
import track.SocialNetworkCircleTrack;

public class SocialNetworkCircleOrbit extends ConcreteCircularOrbit {

  public List<SocialNetworkCircleTrack> trackSet = new ArrayList<>();//轨道的集合
  public List<Friend> objectSet = new ArrayList<>(150000);//轨道上物体的集合
  public Map<SocialNetworkCircleTrack, List<Friend>> relationMap = new HashMap<>();//轨道和物体的对应关系
  public Map<List<Friend>, Double> objectRelation = new HashMap<>(1000000);//朋友A到B和亲密度,无向图
  //public Map<List<Friend>, Double> objectRelation1 = new HashMap<>(1000000);
  public Map<Friend, List<Friend>> friendship = new HashMap<>(150000);//朋友关系
  public Map<Friend, List<Friend>> treeFriendShip = new HashMap<>(1500000);//根据轨道简化后的树型朋友关系
  public Map<String, Friend> findName = new HashMap<>(150000);
  public SocialNetworkCentralObject centralObject = null;//中心用户
  public Friend central;//用于在set中标记关系
  public String[][] message;

  public Logger logger = Logger.getLogger(SocialNetworkCircleOrbit.class);
  public boolean readflag = true;


  private void checkRep() {
    assert trackSet.size() == relationMap.size();
    assert central.getAge() == centralObject.getAge();
    assert central.getGender().equals(centralObject.getGender());
  }

  /**
   * 构造分组.
   */
  public void bfsTrackMaker() {
    Map<Friend, Boolean> visitList = new HashMap<Friend, Boolean>(150000);
    Map<Friend, Integer> radiuMap = new HashMap<Friend, Integer>();
    for (int i = 0; i < objectSet.size(); i++) {
      visitList.put(objectSet.get(i), false);
    }
    //添加第一圈
    SocialNetworkCircleTrack track1 = new SocialNetworkCircleTrack(1);
    relationMap.put(track1, friendship.get(central));
    visitList.replace(central, false, true);
    trackSet.add(track1);
    for (int i = 0; i < friendship.get(central).size(); i++) {
      visitList.replace(friendship.get(central).get(i), false, true);
      radiuMap.put(friendship.get(central).get(i), 1);
    }

    Queue<Friend> tempQueue1 = new LinkedList<>();

    for (int i = 0; i < friendship.get(central).size(); i++) {
      tempQueue1.offer(friendship.get(central).get(i));
    }
    List<Friend> friendList = new ArrayList<>();
    int radiu = 2;
    while (!tempQueue1.isEmpty()) {
      Friend friend = tempQueue1.peek();
      for (int j = 0; j < friendship.get(friend).size(); j++) {
        Friend temp = friendship.get(friend).get(j);
        if (!visitList.get(temp)) {
          friendList.add(temp);
          radiuMap.put(temp, radiuMap.get(friend) + 1);
          tempQueue1.offer(temp);
          visitList.replace(temp, false, true);
        }
      }
      friend = tempQueue1.poll();
    }
    List<Friend> friendList1 = new ArrayList<>(radiuMap.keySet());
    int maxR = 1;
    for (int i = 0; i < friendList1.size(); i++) {
      if (radiuMap.get(friendList1.get(i)) > maxR) {
        maxR = radiuMap.get(friendList1.get(i));
      }
    }
    for (int i = 2; i <= maxR; i++) {
      SocialNetworkCircleTrack track = new SocialNetworkCircleTrack(i);
      List<Friend> list = new ArrayList<>();
      List<Friend> friends = new ArrayList<>(radiuMap.keySet());
      for (int j = 0; j < friends.size(); j++) {
        if (radiuMap.get(friends.get(j)) == i) {
          list.add(friends.get(j));
        }
      }
      trackSet.add(track);
      relationMap.put(track, list);
    }
    checkRep();
  }

  /**
   * 构造树型关系.
   */
  public void makeTreeFriendShip() {
    treeFriendShip.put(central, friendship.get(central));
    List<SocialNetworkCircleTrack> trackList = new ArrayList<>(relationMap.keySet());
    for (int i = 0; i < trackList.size() - 1; i++) {
      List<Friend> friendList = relationMap.get(trackList.get(i));
      List<Friend> friendList1 = relationMap.get(trackList.get(i + 1));
      for (int i1 = 0; i1 < friendList.size(); i1++) {
        List<Friend> list = new ArrayList<>();
        Friend friend1 = friendList.get(i1);
        for (int i2 = 0; i2 < friendList1.size(); i2++) {
          if (friendship.get(friend1).contains(friendList1.get(i2))) {
            list.add(friendList1.get(i2));
          }
        }
        if (list.size() != 0) {
          treeFriendShip.put(friend1, list);
        }
      }
    }
    checkRep();
  }

  /**
   * 根据文件设置，中心、朋友和社会关系,社会关系储存在Map中.
   *
   * @param filename .
   * @return .
   * @throws ObjectExp .
   * @throws ReadFileExp .
   */
  @Override
  public boolean readFiles(String filename) throws ObjectExp, ReadFileExp {
    boolean flag = true;
    StringBuilder exp = new StringBuilder();
    try {

      BufferedReader bfReader = new BufferedReader(new FileReader(filename));
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
          ReadFileExp.assertTrue(checkSpace(temp), "空格数量有误");

          Matcher matcher1 = pattern1.matcher(temp);
          Matcher matcher2 = pattern2.matcher(temp);
          Matcher matcher3 = pattern3.matcher(temp);

          if (matcher1.matches()) {
            //匹配中心用户
            if (!checkGender(matcher1.group(3))) {
              ReadFileExp.assertTrue(checkGender(matcher1.group(3)), "中心用户性别错误");
              flag = false;
            }

            int age = Integer.valueOf(matcher1.group(2));
            if (!checkAge(age)) {
              ReadFileExp.assertTrue(checkAge(age), "中心用户年龄错误");
              flag = false;
            }
            centralObject = new SocialNetworkCentralObject(matcher1.group(1), age,
                matcher1.group(3));
            central = new Friend(matcher1.group(1), age, matcher1.group(3));//用于在set中标记关系
            objectSet.add(central);
            findName.put(matcher1.group(1), central);
            List<Friend> friendSet = new ArrayList<>();
            friendship.put(central, friendSet);
          }

          if (matcher2.matches()) {
            //匹配朋友
            ReadFileExp.assertTrue(checkGender(matcher2.group(3)), "朋友性别错误");
            int age = Integer.valueOf(matcher2.group(2));
            ReadFileExp.assertTrue(checkAge(age), "用户年龄错误");
            //ObjectExp.assertTrue(checkSameFriend(matcher2.group(1)), "出现重名朋友");
            Friend friend = new Friend(matcher2.group(1), age, matcher2.group(3));
            objectSet.add(friend);
            findName.put(matcher2.group(1), friend);
            List<Friend> friendSet = new ArrayList<>();
            friendship.put(friend, friendSet);
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
    //System.out.println(objectSet.size());
    return flag;
  }

  /**
   * .
   *
   * @param filename .
   */
  public void readFiles1(String filename) {
    StringBuilder exp = new StringBuilder();
    try {
      BufferedReader bfReader = new BufferedReader(new FileReader(filename));
      Pattern pattern3 = Pattern
          .compile("SocialTie\\s::=\\s<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(1|0.[\\d]{1,3})>");
      String temp = new String();
      while ((temp = bfReader.readLine()) != null) {
        try {
          temp = temp.trim();
          ReadFileExp.assertTrue(checkSpace(temp), "空格数量有误");
          Matcher matcher3 = pattern3.matcher(temp);
          if (matcher3.matches()) {
            //匹配朋友关系
            String name1 = matcher3.group(1);
            String name2 = matcher3.group(2);
            double intimacy = Double.valueOf(matcher3.group(3));
            //ObjectExp.assertTrue(checkIntimacy(name1, name2, intimacy), "亲密度冲突");
            //RelationExp.assertTrue(checkContainFriend(name1), "friend不存在");
            //RelationExp.assertTrue(checkContainFriend(name2), "friend不存在");

            Friend friend1 = findName.get(name1);
            Friend friend2 = findName.get(name2);
            List<Friend> friendList = new ArrayList<>();
            friendList.add(friend1);
            friendList.add(friend2);
            objectRelation.put(friendList, intimacy);
            setFriendShip(friend1, friend2);
            setFriendShip(friend2, friend1);
          }

        } catch (ReadFileExp | ObjectExp | RelationExp e1) {
          logger.info(e1.toString() + "\t解决：跳过该行");
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
  }


  /**
   *  .
   * @param friendA .
   * @param friendB .
   */
  public void setFriendShip(Friend friendA, Friend friendB) {
    if (friendship.keySet().contains(friendA)) {
      friendship.get(friendA).add(friendB);
    } else {
      List<Friend> friends = new ArrayList<>();
      friends.add(friendB);
      friendship.put(friendA, friends);
    }
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
    return counter >= 0;
  }

  /**
   * 判断性别.
   */
  public boolean checkGender(String gender) {
    return gender.equals("M") || gender.equals("F");
  }

  /**
   * 判断年龄正确.
   */
  public boolean checkAge(int x) {
    return x >= 0 && x <= 100;
  }

  /**
   * 检查重名的人.
   */
  public boolean checkSameFriend(String name) {
    return findName.keySet().contains(name);
  }

  /**
   * 检查冲突的亲密度.
   */
  public boolean checkIntimacy(String name1, String name2, double x) {
    for (List<Friend> friendList : objectRelation.keySet()) {
      String n1 = friendList.get(0).getName();
      String n2 = friendList.get(1).getName();
      if ((n1.equals(name1) && n2.equals(name2)) || (n1.equals(name2) && n2.equals(name1))) {
        if (x != objectRelation.get(friendList)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 检查friend是否存在.
   */
  public boolean checkContainFriend(String name) {
    for (Friend friend : objectSet) {
      if (friend.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }
}
