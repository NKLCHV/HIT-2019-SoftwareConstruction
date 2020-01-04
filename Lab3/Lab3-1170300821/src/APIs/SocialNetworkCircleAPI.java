package APIs;

import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircleOrbit;
import ortherAPIs.Difference;
import ortherAPIs.SocialNetworkCircleDifference;
import physicalObject.Friend;
import track.SocialNetworkCircleTrack;

import java.util.*;

public class SocialNetworkCircleAPI extends CircularOrbitAPIs {

    /**
     * 找到friend所在的轨道
     * @param C1 系统
     * @param friend 朋友
     * @return 轨道
     */
    public SocialNetworkCircleTrack findFriend(SocialNetworkCircleOrbit C1, Friend friend) {
        for (SocialNetworkCircleTrack track : C1.relationMap.keySet()) {
            List<Friend> friendSet = C1.relationMap.get(track);
            for (Friend friendtemp : friendSet) {
                if (friendtemp.equals(friend))
                    return track;
            }
        }
        return null;
    }

    /**
     * 计算系统熵值
     * @param c 系统
     * @return 0-1
     */
    @Override
    public double getObjectDistributionEntropy(CircularOrbit c) {
        SocialNetworkCircleOrbit C1 = (SocialNetworkCircleOrbit) c;
        if (C1.trackSet.size() <= 0)
            return 0;
        double min = 1;
        double max = C1.trackSet.size();
        if (max == min) {
            return 0;
        }
        double[] list = new double[C1.trackSet.size()];
        for (SocialNetworkCircleTrack track : C1.trackSet) {
            int r = track.getTrackRadius();
            list[r - 1] = (r - min) / (max - min + 1);
        }
        double sum = 0;
        int sumOfObjects = C1.objectSet.size();
        for (SocialNetworkCircleTrack track : C1.trackSet) {
            sum += list[track.getTrackRadius() - 1] * C1.relationMap.get(track).size();
        }
        for (int i = 0; i < list.length; i++) {
            list[i] = list[i] / sum;
        }
        double k = 1 / Math.log(sumOfObjects);
        double result = 0;
        for (int i = 0; i < list.length; i++) {
            result += -k * list[i] * Math.log(list[i]);
        }
        return result;
    }

    /**
     * 获得朋友所在轨道层数
     * @param C1 系统
     * @param friend 朋友
     * @return 整型
     */
    private int getFriendTrackRadius(SocialNetworkCircleOrbit C1, Friend friend) {
        if (friend.equals(C1.central))
            return 0;
        for (SocialNetworkCircleTrack track : C1.relationMap.keySet()) {
            if (C1.relationMap.get(track).contains(friend))
                return track.getTrackRadius();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 计算逻辑距离
     * @param c 系统
     * @param e1 第一个人
     * @param e2 第二个人
     * @return 整型
     */
    @Override
    public int getLogicalDistance(CircularOrbit c, Object e1, Object e2) {

        SocialNetworkCircleOrbit C1 = (SocialNetworkCircleOrbit) c;
        int result = C1.trackSet.size() * 2;
        Friend f1 = (Friend) e1;
        Friend f2 = (Friend) e2;
        Friend fin = f1;
        List<Friend> friendList1 = new ArrayList<>();
        List<Friend> friendList2 = new ArrayList<>();
        while (fin.equals(C1.central)) {
            Friend[] friends = (Friend[]) C1.treeFriendShip.keySet().toArray();
            for(int i1= 0;i1<friends.length;i1++){
                Friend friend = friends[i1];
                if (C1.treeFriendShip.get(friend).contains(fin)) {
                    fin = friend;
                    friendList1.add(fin);
                    break;
                }
            }
        }
        fin = f2;
        while (fin.equals(C1.central)) {
            Friend[] friends = (Friend[]) C1.treeFriendShip.keySet().toArray();
            for(int i1= 0;i1<friends.length;i1++){
                Friend friend = friends[i1];
                if (C1.treeFriendShip.get(friend).contains(fin)) {
                    fin = friend;
                    friendList2.add(fin);
                    break;
                }
            }
        }

        for (int i = 1; i <= friendList1.size(); i++) {
            for (int j = 1; j <= friendList2.size(); j++) {
                if (friendList1.get(i) == friendList2.get(j))
                    result = i + j;
            }
        }

        return result;
    }

    /**
     * 比较系统不同
     * @param c1 系统1
     * @param c2 系统2
     * @return difference类
     */
    @Override
    public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        SocialNetworkCircleDifference difference = new SocialNetworkCircleDifference();
        SocialNetworkCircleOrbit C1 = (SocialNetworkCircleOrbit) c1;
        SocialNetworkCircleOrbit C2 = (SocialNetworkCircleOrbit) c2;

        difference.DcentralName = C1.centralObject.getName() + "-" + C2.centralObject.getName();
        difference.DtrackNum = C1.trackSet.size() - C2.trackSet.size();

        int size1 = C1.trackSet.size();
        int size2 = C2.trackSet.size();
        int size = Math.max(size1, size2);

        difference.DfriendNum = new int[size];
        if (size1 > size2) {
            for (int i = size2; i < size1; i++) {
                for (SocialNetworkCircleTrack track : C1.relationMap.keySet()) {
                    if (track.getTrackRadius() == i) {
                        difference.DfriendNum[i] = C1.relationMap.get(track).size();
                    }
                }
            }
        } else if (size1 < size2) {
            for (int i = size1; i < size2; i++) {
                for (SocialNetworkCircleTrack track : C2.relationMap.keySet()) {
                    if (track.getTrackRadius() == i) {
                        difference.DfriendNum[i] = -C2.relationMap.get(track).size();
                    }
                }
            }
        } else {
            for (int i = 1; i <= size; i++)
                for (SocialNetworkCircleTrack track1 : C1.relationMap.keySet()) {
                    int j = track1.getTrackRadius();
                    for (SocialNetworkCircleTrack track2 : C2.relationMap.keySet()) {
                        if (track2.getTrackRadius() == j) {
                            difference.DfriendNum[j] = C1.relationMap.get(track1).size() - C2.relationMap.get(track2).size();
                        }
                    }
                }
        }
        return difference;
    }
}
