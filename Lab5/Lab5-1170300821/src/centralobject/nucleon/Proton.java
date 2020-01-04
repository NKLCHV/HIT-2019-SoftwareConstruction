package centralobject.nucleon;

public class Proton extends Nucleon {

  public Proton() {
    type = "Proton";
  }

  @Override
  void draw() {
    System.out.println("It's a Proton.");
  }
}
