package centralobject.nucleon;

public class Neutron extends Nucleon {

  public Neutron() {
    type = "Neutron";
  }
  
  @Override
  void draw() {
    System.out.println("It's a Neutron.");
  }
}
