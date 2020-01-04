package iostrategy.socialnetworkcirclewritter;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.SocialNetworkCircleOrbit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import physicalobject.Friend;
import iostrategy.Writters;


public class SNCstreamWritter implements Writters {

  @Override
  public void writeFiles(ConcreteCircularOrbit orbit0, String filename) {
    SocialNetworkCircleOrbit orbit = (SocialNetworkCircleOrbit) orbit0;
    String newLine = System.getProperty("line.separator");//用于换行
    StringBuilder stringBuilder = new StringBuilder();
    try {
      FileOutputStream output = new FileOutputStream(filename);
      stringBuilder.append(
          "CentralUser ::= <" + orbit.central.getName() + "," + orbit.central.getAge() + ","
              + orbit.central.getGender() + ">");
      stringBuilder.append(newLine);
      for (int i = 1; i < orbit.objectSet.size(); i++) {
        Friend friend = orbit.objectSet.get(i);
        stringBuilder.append("Friend ::= <" + friend.getName() + "," + friend.getAge()
            + "," + friend.getGender() + ">");
        stringBuilder.append(newLine);
      }
      for (List<Friend> list : orbit.objectRelation.keySet()) {
        double value = orbit.objectRelation.get(list);
        Friend f1 = list.get(0);
        Friend f2 = list.get(1);
        stringBuilder
            .append("SocialTie ::= <" + f1.getName() + "," + f2.getName() + "," + value + ">");
        stringBuilder.append(newLine);
      }
      output.write(stringBuilder.toString().getBytes());
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
