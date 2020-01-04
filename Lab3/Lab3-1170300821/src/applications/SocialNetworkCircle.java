package applications;

import APIs.SocialNetworkCircleAPI;
import circularOrbit.SocialNetworkCircleOrbit;
import physicalObject.Friend;
import track.SocialNetworkCircleTrack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SocialNetworkCircle {
    private SocialNetworkCircleOrbit C1 = new SocialNetworkCircleOrbit();
    private SocialNetworkCircleAPI API = new SocialNetworkCircleAPI();
    private boolean inited = false;

    public SocialNetworkCircleOrbit getOrbit() {
        return C1;
    }

    public void init(String filename) {
        if (filename != null) {
            C1.readFiles(filename);
            C1.BFStrackMaker();
            C1.makeTreeFriendShip();
            inited = true;
        }
    }

    public void clear() {
        C1 = new SocialNetworkCircleOrbit();
        inited = false;
    }

    /**
     * 判定用户是在哪个轨道上
     *
     * @param friend 用户
     * @return 用户所在轨道
     */
    public SocialNetworkCircleTrack findFriend(Friend friend) {
        if (inited) {
            if (friend != null)
                return API.findFriend(C1, friend);
        }
        return null;
    }

    /**
     * 判定用户是在哪个轨道上
     *
     * @param name 用户姓名
     * @return 用户所在轨道
     */
    public SocialNetworkCircleTrack findFriend(String name) {
        if (inited) {
            Friend friend = null;
            for (Friend tempfriend : C1.objectSet) {
                if (tempfriend.getObjectName().equals(name))
                    friend = tempfriend;
            }
            if (friend != null)
                return API.findFriend(C1, friend);
        }
        return null;
    }


    public int informationDiffusion(Friend friend) {
        if (inited) {
            if (friend != null && C1.objectSet.contains(friend))
                return C1.treeFriendShip.get(friend).size();
        }
        return 0;
    }

    /**
     * 计算两个轨道上的 用户之间的逻辑距离
     *
     * @param e1 用户1
     * @param e2 用户2
     * @return 当用户不存在时，返回MAX_VALUE
     */
    public int getLogicalDistance(Friend e1, Friend e2) {
        if (inited) {
            if (e1 != null && e2 != null)
                if (C1.objectSet.contains(e1) && C1.objectSet.contains(e2)) {
                    if (e1.equals(e2))
                        return 0;
                    else
                        return API.getLogicalDistance(C1, e1, e2);
                }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 计算两个轨道上的 用户之间的逻辑距离
     *
     * @param name1 用户1姓名
     * @param name2 用户2姓名
     * @return 当用户不存在时，返回MAX_VALUE
     */
    public int getLogicalDistance(String name1, String name2) {
        Friend e1 = null;
        Friend e2 = null;
        if (inited) {
            for (Friend friend : C1.objectSet) {
                if (friend.getObjectName().equals(name1))
                    e1 = friend;
                if (friend.getObjectName().equals(name2))
                    e2 = friend;
            }

        }
        if (e1 != null && e2 != null)
            return getLogicalDistance(e1, e2);

        return Integer.MAX_VALUE;
    }

    public void deleteAnRelasion(Friend f1, Friend f2) {
        if (inited && C1.friendship.keySet().contains(f1) && C1.friendship.get(f1).contains(f2)) {
            Iterator<Friend> it = C1.friendship.get(f1).iterator();
            while (it.hasNext()) {
                Friend friend = it.next();
                if (friend.equals(f2))
                    it.remove();
            }
            C1.BFStrackMaker();
            C1.makeTreeFriendShip();
        }
    }

    public void addAnRelasion(Friend f1,Friend f2){
        if(inited && C1.objectSet.contains(f1)){
            C1.friendship.get(f1).add(f2);
            C1.BFStrackMaker();
            C1.makeTreeFriendShip();
        }
    }
}
