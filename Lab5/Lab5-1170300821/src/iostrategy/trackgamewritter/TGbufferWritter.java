package iostrategy.trackgamewritter;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.TrackGameOrbit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import physicalobject.Athlete;
import iostrategy.Writters;

public class TGbufferWritter implements Writters {

  @Override
  public void writeFiles(ConcreteCircularOrbit orbit0, String filename) {
    TrackGameOrbit orbit = (TrackGameOrbit) orbit0;
    try {
      BufferedWriter bfWriter = new BufferedWriter(new FileWriter(filename));
      bfWriter.write("Game ::= " + orbit.gameType);
      bfWriter.newLine();
      bfWriter.write("NumOfTracks ::= " + orbit.numOfTracksInFile);
      bfWriter.newLine();
      for (int i = 0; i < orbit.objectList.size(); i++) {
        Athlete athlete = orbit.objectList.get(i);
        bfWriter.write(
            "Athlete ::= <" + athlete.getName() + "," + athlete.getNumber() + "," + athlete
                .getCountry() + "," + athlete.getAge() + "," + athlete.getBestgrade() + ">");
        bfWriter.newLine();
      }
      bfWriter.flush();
      bfWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
