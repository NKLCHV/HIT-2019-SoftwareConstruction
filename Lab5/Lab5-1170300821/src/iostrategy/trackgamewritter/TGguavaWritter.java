package iostrategy.trackgamewritter;

import static com.google.common.base.Preconditions.checkNotNull;

import circularorbit.ConcreteCircularOrbit;
import circularorbit.TrackGameOrbit;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import physicalobject.Athlete;
import iostrategy.Writters;

public class TGguavaWritter implements Writters {

  @Override
  public void writeFiles(ConcreteCircularOrbit orbit0, String filename) {
    TrackGameOrbit orbit = (TrackGameOrbit) orbit0;
    final File newFile = new File(filename);
    StringBuilder stringBuilder = new StringBuilder();
    String newLine = System.getProperty("line.separator");//用于换行
    checkNotNull(filename, "Provided file name for writing must NOT be null.");
    try {
      stringBuilder.append("Game ::= " + orbit.gameType);
      stringBuilder.append(newLine);
      stringBuilder.append("NumOfTracks ::= " + orbit.numOfTracksInFile);
      stringBuilder.append(newLine);
//      Files.write(stringBuilder, newFile, Charsets.UTF_8);
//      stringBuilder.delete(0, stringBuilder.length());
      for (int i = 0; i < orbit.objectList.size(); i++) {
        Athlete athlete = orbit.objectList.get(i);
        stringBuilder
            .append("Athlete ::= <" + athlete.getName() + "," + athlete.getNumber() + "," + athlete
                .getCountry() + "," + athlete.getAge() + "," + athlete.getBestgrade() + ">");
        stringBuilder.append(newLine);

//        if (i % 50000 == 0) {
//          Files.append(stringBuilder, newFile, Charsets.UTF_8);
//          stringBuilder.delete(0, stringBuilder.length());
//        }
      }
      Files.write(stringBuilder, newFile, Charsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
