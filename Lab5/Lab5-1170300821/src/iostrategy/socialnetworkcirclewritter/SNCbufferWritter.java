package iostrategy.socialnetworkcirclewritter;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.SocialNetworkCircleOrbit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import physicalobject.Friend;
import iostrategy.Writters;

public class SNCbufferWritter implements Writters {

  @Override
  public void writeFiles(ConcreteCircularOrbit orbit0, String filename) {
    SocialNetworkCircleOrbit orbit = (SocialNetworkCircleOrbit) orbit0;
    try {
      BufferedWriter bfWriter = new BufferedWriter(new FileWriter(filename));
      bfWriter.write(
          "CentralUser ::= <" + orbit.central.getName() + "," + orbit.central.getAge() + ","
              + orbit.central.getGender() + ">");
      bfWriter.newLine();
      for (int i = 1; i < orbit.objectSet.size(); i++) {
        Friend friend = orbit.objectSet.get(i);
        bfWriter.write(
            "Friend ::= <" + friend.getName() + "," + friend.getAge() + "," + friend.getGender()
                + ">");
        bfWriter.newLine();
      }
      for (List<Friend> list : orbit.objectRelation.keySet()) {
        double value = orbit.objectRelation.get(list);
        Friend f1 = list.get(0);
        Friend f2 = list.get(1);
        bfWriter.write("SocialTie ::= <" + f1.getName() + "," + f2.getName() + "," + value + ">");
        bfWriter.newLine();
      }
      bfWriter.flush();
      bfWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
