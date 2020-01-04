package circularOrbit;

import centralObject.SocialNetworkCentralObject;
import physicalObject.Friend;
import track.SocialNetworkCircleTrack;
import track.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialNetworkCircleOrbit extends ConcreteCircularOrbit {
    public List<SocialNetworkCircleTrack> trackSet = new ArrayList<>();//轨道的集合
    public List<Friend> objectSet = new ArrayList<>();//轨道上物体的集合
    public Map<SocialNetworkCircleTrack, List<Friend>> relationMap = new HashMap<>();//轨道和物体的对应关系
    public Map<Map<Friend, Friend>, Double> objectRelation = new HashMap<>();//朋友A到B和亲密度
    public Map<Friend, List<Friend>> friendship = new HashMap<>();//朋友关系
    public Map<Friend, List<Friend>> treeFriendShip = new HashMap<>();//根据轨道简化后的树型朋友关系
    public SocialNetworkCentralObject centralObject = null;//中心用户
    public Friend central;//用于在set中标记关系


    public void BFStrackMaker() {
        Map<Friend, Boolean> visited = new HashMap<>();
        Set<Friend> friends = friendship.keySet();
        for (Friend friend : friends) {
            visited.put(friend, false);
        }

        Queue<Friend> tempQueue1 = new LinkedList<>();
        Queue<Friend> tempQueue2 = new LinkedList<>();

        tempQueue1.offer(central);
        tempQueue2.offer(central);//标记何时增加轨道层数
        visited.replace(central, false, true);
        List<Friend> friendSet = new ArrayList<>();
        int i = 1;
        while (!tempQueue1.isEmpty()) {
            Friend friend = tempQueue1.peek();

            for (int j = 0; j < friendship.get(friend).size(); j++) {
                Friend temp = friendship.get(friend).get(i);
                if (!visited.get(friend)) {
                    friendSet.add(friend);
                    tempQueue1.offer(friend);
                    visited.replace(friend, false, true);
                }
            }
            tempQueue1.poll();
            tempQueue2.poll();

            if (tempQueue2.isEmpty()) {//当标记队列被清空后，friendset中的friend加入到map中,并将set中的friend加入标记队列
                SocialNetworkCircleTrack track = new SocialNetworkCircleTrack(i);
                for (int j = 0; j < friendSet.size(); i++) {
                    Friend temp = friendSet.get(i);
                    tempQueue2.offer(temp);
                }
                relationMap.put(track, friendSet);
                trackSet.add(track);
                friendSet.clear();
                i++;
            }
        }
    }

    public void makeTreeFriendShip() {
        for (int i = 0; i < objectSet.size(); i++) {
            Friend friend = objectSet.get(i);
            SocialNetworkCircleTrack track = new SocialNetworkCircleTrack(1);
            int r = 0;
            for (SocialNetworkCircleTrack track1 : relationMap.keySet()) {
                if (relationMap.get(track1).contains(friend))
                    r = track1.getTrackRadius();
            }
            for (SocialNetworkCircleTrack track1 : relationMap.keySet()) {
                if (track1.getTrackRadius() == r + 1)
                    track = track1;
            }
            List<Friend> friendSet = new ArrayList<>();
            for (int j = 0; j < friendship.get(friend).size(); j++) {
                Friend temp = friendship.get(friend).get(j);
                if (!relationMap.get(track).contains(temp))
                    friendSet.add(temp);
            }
            treeFriendShip.put(friend, friendSet);
        }
    }

    /**
     * 根据文件设置，中心、朋友和社会关系
     * 社会关系储存在Map<Friend, set<Friend>>中
     *
     * @param filename
     */
    @Override
    public void readFiles(String filename) {
        try {

            BufferedReader bfReader = new BufferedReader(new FileReader(filename));
            Pattern pattern1 = Pattern.compile("CentralUser\\s::=\\s<([A-Za-z0-9]+),([\\d]+),([MF])>");
            Pattern pattern2 = Pattern.compile("Friend\\s::=\\s<([A-Za-z0-9]+),\\s([\\d]+),\\s([MF])>");
            Pattern pattern3 = Pattern.compile("SocialTie\\s::=\\s<([A-Za-z0-9]+),\\s([A-Za-z0-9]+),\\s(1|0.[\\d]{1,3})>");
            String temp = new String();
            while ((temp = bfReader.readLine()) != null) {
                Matcher matcher1 = pattern1.matcher(temp);
                Matcher matcher2 = pattern2.matcher(temp);
                Matcher matcher3 = pattern3.matcher(temp);

                //System.out.println(temp + "  " + matcher1.matches());

                if (matcher1.matches()) {//匹配中心用户
                    System.out.println(matcher1.group());
                    int age = Integer.valueOf(matcher1.group(2));
                    centralObject = new SocialNetworkCentralObject(matcher1.group(1), age, matcher1.group(3));
                    central = new Friend(matcher1.group(1), age, matcher1.group(3));//用于在set中标记关系
                    //
                    objectSet.add(central);
                    List<Friend> friendSet = new ArrayList<>();
                    friendship.put(central, friendSet);
                }

                if (matcher2.matches()) {//匹配朋友
                    int age = Integer.valueOf(matcher2.group(2));
                    Friend friend = new Friend(matcher2.group(1), age, matcher2.group(3));
                    objectSet.add(friend);
                    List<Friend> friendSet = new ArrayList<>();
                    friendship.put(friend, friendSet);
                }

                if (matcher3.matches()) {//匹配朋友关系
                    Friend friendA = null;//将朋友关系作为有向图，两个人名对应的Friend作为节点
                    Friend friendB = null;

                    for (int i = 0; i < objectSet.size(); i++) {
                        Friend friend1 = objectSet.get(i);
                        if (friend1.getName().equals(matcher3.group(1))) {
                            System.out.println(matcher3.group(2));
                            System.out.println(matcher3.group(3));
                            for (int j = 0; j < objectSet.size(); j++) {
                                Friend friend2 = objectSet.get(j);
                                if (friend2.getName().equals(matcher3.group(2))) {
                                    Map<Friend, Friend> friendmap = new HashMap<>();
                                    friendmap.put(friend1, friend2);
                                    objectRelation.put(friendmap, Double.valueOf(matcher3.group(3)));
                                    setFriendShip(friend1, friend2);
                                }
                            }
                        }
                    }
                }

            }

            bfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setFriendShip(Friend friendA, Friend friendB) {
        if (friendship.keySet().contains(friendA)) {
            friendship.get(friendA).add(friendB);
        }
        List<Friend> friends = new ArrayList<>();
        friends.add(friendB);
        friendship.put(friendA, friends);
    }

}
