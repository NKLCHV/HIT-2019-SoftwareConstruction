package api;

import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircleOrbit;
import java.util.ArrayList;
import java.util.List;
import ortherapis.Difference;
import ortherapis.SocialNetworkCircleDifference;
import physicalobject.Friend;
import track.SocialNetworkCircleTrack;

public class SocialNetworkCircleApi extends CircularOrbitApis {

  private void checkRep(SocialNetworkCircleOrbit orbit) {
    assert orbit.trackSet.size() == orbit.relationMap.size();
    assert orbit.central.getAge() == orbit.centralObject.getAge();
    assert orbit.central.getGender().equals(orbit.centralObject.getGender());
  }

  /**
   * 找到friend所在的轨道.
   *
   * @param c1 系统
   * @param friend 朋友
   * @return 轨道
   */
  public SocialNetworkCircleTrack findFriend(SocialNetworkCircleOrbit c1, Friend friend) {
    assert c1 != null && friend != null;
    for (SocialNetworkCircleTrack track : c1.relationMap.keySet()) {
      List<Friend> friendSet = c1.relationMap.get(track);
      for (Friend friendtemp : friendSet) {
        if (friendtemp.getName().equals(friend.getName())) {
          return track;
        }
      }
    }
    checkRep(c1);
    return null;
  }

  /**
   * 计算系统熵值.
   *
   * @param c 系统
   * @return 0-1
   */
  @Override
  public double getObjectDistributionEntropy(CircularOrbit c) {
    assert c != null;
    SocialNetworkCircleOrbit c1 = (SocialNetworkCircleOrbit) c;
    if (c1.relationMap.size() <= 0) {
      return 0;
    }
    double min = 1;
    double max = c1.relationMap.size();
    if (max == min) {
      return 0;
    }
    double[] list = new double[c1.trackSet.size()];
    for (SocialNetworkCircleTrack track : c1.trackSet) {
      int r = track.getTrackRadius();
      list[r - 1] = (r + 1 - min) / (max - min + 1);
    }
    double sum = 0;
    int sumOfObjects = c1.objectSet.size();
    for (SocialNetworkCircleTrack track : c1.trackSet) {
      sum += list[track.getTrackRadius() - 1] * c1.relationMap.get(track).size();
    }
    for (int i = 0; i < list.length; i++) {
      list[i] = list[i] / sum;
    }
    double k = 1 / Math.log(sumOfObjects);
    double result = 0;
    for (int i = 0; i < list.length; i++) {
      result += -k * list[i] * Math.log(list[i]);
    }
    checkRep(c1);
    assert result >= 0 && result <= 1;
    return result;
  }

  /**
   * 获得朋友所在轨道层数.
   *
   * @param c1 系统
   * @param friend 朋友
   * @return 整型
   */
  public int getFriendTrackRadius(SocialNetworkCircleOrbit c1, Friend friend) {
    assert c1 != null && friend != null;
    if (friend.equals(c1.central)) {
      return 0;
    }
    for (SocialNetworkCircleTrack track : c1.relationMap.keySet()) {
      if (c1.relationMap.get(track).contains(friend)) {
        return track.getTrackRadius();
      }
    }
    checkRep(c1);
    return Integer.MAX_VALUE;
  }

  /**
   * 计算逻辑距离.
   *
   * @param c 系统
   * @param e1 第一个人
   * @param e2 第二个人
   * @return 整型
   */
  @Override
  public int getLogicalDistance(CircularOrbit c, Object e1, Object e2) {
    assert c != null && e1 != null && e2 != null;
    SocialNetworkCircleOrbit c1 = (SocialNetworkCircleOrbit) c;
    int result = c1.trackSet.size() * 2;
    Friend f1 = (Friend) e1;
    Friend f2 = (Friend) e2;
    Friend fin = f1;
    List<Friend> friendList1 = new ArrayList<Friend>();
    List<Friend> friendList2 = new ArrayList<Friend>();
    while (fin.equals(c1.central)) {
      List<Friend> friends = new ArrayList<Friend>(c1.treeFriendShip.keySet());
      for (int i1 = 0; i1 < friends.size(); i1++) {
        Friend friend = friends.get(i1);
        if (c1.treeFriendShip.get(friend).contains(fin)) {
          fin = friend;
          friendList1.add(fin);
          break;
        }
      }
    }
    fin = f2;
    while (fin.equals(c1.central)) {
      List<Friend> friends = new ArrayList<Friend>(c1.treeFriendShip.keySet());
      for (int i1 = 0; i1 < friends.size(); i1++) {
        Friend friend = friends.get(i1);
        if (c1.treeFriendShip.get(friend).contains(fin)) {
          fin = friend;
          friendList2.add(fin);
          break;
        }
      }
    }

    for (int i = 1; i <= friendList1.size(); i++) {
      for (int j = 1; j <= friendList2.size(); j++) {
        if (friendList1.get(i) == friendList2.get(j)) {
          result = i + j;
        }
      }
    }
    checkRep(c1);
    assert result >= 0 && result <= c1.trackSet.size() * 2;
    return result;
  }

  /**
   * 比较系统不同.
   *
   * @param c1 系统1
   * @param c2 系统2
   * @return difference类
   */
  @Override
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
    assert c1 != null && c2 != null;
    SocialNetworkCircleDifference difference = new SocialNetworkCircleDifference();
    SocialNetworkCircleOrbit orbit1 = (SocialNetworkCircleOrbit) c1;
    SocialNetworkCircleOrbit orbit2 = (SocialNetworkCircleOrbit) c2;

    difference.dcentralName = orbit1.central.getName() + "-" + orbit2.central.getName();
    int c1n = orbit1.relationMap.size();
    int c2n = orbit2.relationMap.size();
    difference.dtrackNum = c1n - c2n;
    int max = c1n > c2n ? c1n : c2n;
    int min = c1n > c2n ? c2n : c1n;
    List<SocialNetworkCircleTrack> tracks1 = new ArrayList<>(orbit1.relationMap.keySet());
    List<SocialNetworkCircleTrack> tracks2 = new ArrayList<>(orbit2.relationMap.keySet());
    difference.dfriendNum = new int[max];
    for (int i = 0; i < min; i++) {
      difference.dfriendNum[i] =
          orbit1.relationMap.get(tracks1.get(i)).size()
              - orbit2.relationMap.get(tracks2.get(i)).size();
    }
    checkRep(orbit1);
    checkRep(orbit2);
    assert difference != null;
    return difference;
  }
}
