package physicalobject;

public class Electron extends PhysicalObject {

  private String atomName;

  /**
   *电子.
   */
  public Electron(String atomicName) {
    super("electron", null);
    this.atomName = atomicName;
  }

  public String getAtomName() {
    return atomName;
  }

  @Override
  public int hashCode() {
    return getObjectName().hashCode() * 31 + atomName.hashCode();
  }
}
