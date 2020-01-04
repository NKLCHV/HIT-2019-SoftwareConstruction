import applications.TrackGame;
import java.util.Scanner;

public class Main {

  /**
   * .
   *
   * @param args .
   */
  public static void main(String[] args) {
    TrackGame trackGame = new TrackGame();
    trackGame.init("txt/TrackGame.txt");
    Scanner sc = new Scanner(System.in);
    System.out.print(sc.next());
    //GCtest();
  }

  private static void GCtest() {
    TrackGame trackGame = new TrackGame();
    trackGame.init("txt/TrackGame.txt");
    Scanner sc = new Scanner(System.in);
    System.out.print(sc.next());
    System.out.println(trackGame.getTrackGameOrbit().gameGroup.size());
    trackGame.changeGroups(1,1,1,1,1,3);
    Scanner sc1 = new Scanner(System.in);
    System.out.print(sc1.next());
  }
}
