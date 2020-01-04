package iostrategy;

import circularorbit.SocialNetworkCircleOrbit;
import circularorbit.TrackGameOrbit;
import iostrategy.socialnetworkcirclereader.SNCbufferReader;
import iostrategy.socialnetworkcirclereader.SNCguavaReader;
import iostrategy.socialnetworkcirclereader.SNCstreamReader;
import iostrategy.socialnetworkcirclewritter.SNCbufferWritter;
import iostrategy.socialnetworkcirclewritter.SNCguavaWritter;
import iostrategy.socialnetworkcirclewritter.SNCstreamWritter;
import iostrategy.trackgamereader.TGbufferReader;
import iostrategy.trackgamereader.TGguavaReader;
import iostrategy.trackgamereader.TGscannerReader;
import iostrategy.trackgamereader.TGstreamReader;
import iostrategy.trackgamewritter.TGbufferWritter;
import iostrategy.trackgamewritter.TGguavaWritter;
import iostrategy.trackgamewritter.TGstreamWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IoTest {

  public static void ioTest() {
    long[] time1 = new long[10];
    long[] time2 = new long[13];

    time2[0] = ioTest_1(new TGbufferReader(), "TrackGame_Buffer_Read");
    time2[1] = ioTest_1(new TGguavaReader(), "TrackGame_Guava_Read");
    time2[2] = ioTest_1(new TGscannerReader(), "TrackGame_Scanner_Read");
    time2[3] = ioTest_1(new TGstreamReader(), "TrackGame_Stream_Read");

    time2[4] = ioTest_2(new TGbufferWritter(), "txt/TrackGame_Buffer_Write.txt");
    time2[5] = ioTest_2(new TGstreamWriter(), "txt/TrackGame_Stream_Write.txt");
    time2[6] = ioTest_2(new TGguavaWritter(), "txt/TrackGame_Guava_Write.txt");

    time2[7] = ioTest_3(new SNCbufferReader(), "SocialNetworkCircle_Buffer_Read");
    time2[8] = ioTest_3(new SNCstreamReader(), "SocialNetworkCircle_Stream_Read");
    time2[9] = ioTest_3(new SNCguavaReader(), "SocialNetworkCircle_Guava_Read");

    time2[10] = ioTest_4(new SNCbufferWritter(), "txt/SocialNetworkCircle_Buffer_Write.txt");
    time2[11] = ioTest_4(new SNCguavaWritter(), "txt/SocialNetworkCircle_Guava_Write.txt");
    time2[12] = ioTest_4(new SNCstreamWritter(), "txt/SocialNetworkCircle_Stream_Write.txt");

    try {
      BufferedWriter bfWriter = new BufferedWriter(new FileWriter("txt/test.txt"));
      for (int i = 0; i < 13; i++) {
        bfWriter.write(String.valueOf(time2[i]));
        bfWriter.newLine();
      }
      bfWriter.flush();
      bfWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static long ioTest_1(Readers readers, String name) {
    long time1 = 0;
    for (int i = 0; i < 10; i++) {
      time1 += ioTest1(readers, name);
    }
    return time1 / 10;
  }

  public static long ioTest_3(Readers readers, String name) {
    long time1 = 0;
    for (int i = 0; i < 10; i++) {
      time1 += ioTest3(readers, name);
    }
    return time1 / 10;
  }

  private static long ioTest_2(Writters writters, String filename) {
    long time1 = 0;
    for (int i = 0; i < 10; i++) {
      time1 += ioTest2(writters, filename);
    }
    return time1 / 10;
  }

  private static long ioTest_4(Writters writters, String filename) {
    long time1 = 0;
    for (int i = 0; i < 10; i++) {
      time1 += ioTest4(writters, filename);
    }
    return time1 / 10;
  }


  private static long ioTest1(Readers readers, String name) {
    TrackGameOrbit orbit = new TrackGameOrbit();
    ReadFiles readFiles = new ReadFiles(readers);
    long startTime = System.currentTimeMillis();
    orbit = (TrackGameOrbit) readFiles.readfile(orbit, "txt/TrackGame.txt");
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    //System.out.println(name + "   " + totalTime);
    return totalTime;
  }

  private static long ioTest2(Writters writters, String filename) {
    TrackGameOrbit orbit = new TrackGameOrbit();
    orbit.readFiles("txt/TrackGame.txt");
    long startTime = System.currentTimeMillis();
    WriteFiles writeFiles = new WriteFiles(writters);
    writeFiles.writeFiles(orbit, filename);
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    //System.out.println(filename + "   " + totalTime);
    return totalTime;
  }

  private static long ioTest3(Readers readers, String name) {
    SocialNetworkCircleOrbit orbit = new SocialNetworkCircleOrbit();
    ReadFiles readFiles = new ReadFiles(readers);
    long startTime = System.currentTimeMillis();
    orbit = (SocialNetworkCircleOrbit) readFiles.readfile(orbit, "txt/SocialNetworkCircle.txt");
    orbit.bfsTrackMaker();
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    //System.out.println(name + "   " + totalTime);
    return totalTime;
  }

  private static long ioTest4(Writters writters, String filename) {
    SocialNetworkCircleOrbit orbit = new SocialNetworkCircleOrbit();
    ReadFiles readFiles = new ReadFiles(new SNCbufferReader());
    orbit = (SocialNetworkCircleOrbit) readFiles.readfile(orbit, "txt/SocialNetworkCircle.txt");
    orbit.bfsTrackMaker();
    WriteFiles writeFiles = new WriteFiles(writters);
    long startTime = System.currentTimeMillis();
    writeFiles.writeFiles(orbit, filename);
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    //System.out.println(filename + "    " + totalTime);
    return totalTime;
  }
}
