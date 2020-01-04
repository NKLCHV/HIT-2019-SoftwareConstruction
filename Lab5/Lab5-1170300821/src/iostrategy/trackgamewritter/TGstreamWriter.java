package iostrategy.trackgamewritter;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.TrackGameOrbit;
import java.io.FileOutputStream;
import java.io.IOException;
import physicalobject.Athlete;
import iostrategy.Writters;

public class TGstreamWriter implements Writters {

  @Override
  public void writeFiles(ConcreteCircularOrbit orbit0, String filename) {
    TrackGameOrbit orbit = (TrackGameOrbit) orbit0;
    String newLine = System.getProperty("line.separator");//用于换行
    StringBuilder stringBuilder = new StringBuilder();
    try {
      FileOutputStream output = new FileOutputStream(filename);
      stringBuilder.append( "Game ::= " + orbit.gameType);
      stringBuilder.append(newLine);
      stringBuilder.append("NumOfTracks ::= " + orbit.numOfTracksInFile);
      stringBuilder.append(newLine);

      for (int i = 0; i < orbit.objectList.size(); i++) {
        Athlete athlete = orbit.objectList.get(i);
        stringBuilder.append(
            "Athlete ::= <" + athlete.getName() + "," + athlete.getNumber() + "," + athlete
                .getCountry() + "," + athlete.getAge() + "," + athlete.getBestgrade() + ">");
        stringBuilder.append(newLine);
      }
      output.write(stringBuilder.toString().getBytes());
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
