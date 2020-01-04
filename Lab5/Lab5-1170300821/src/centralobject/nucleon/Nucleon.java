package centralobject.nucleon;

public abstract class Nucleon implements Cloneable {

  private String atomName;
  protected String type;

  abstract void draw();
  public String getAtomName() {  return atomName;  }
  public String getType() {  return type;  }
  public void setAtomName(String atomName) {  this.atomName = atomName;  }

  public Object clone() {
    Object clone = null;
    try {
      clone = super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return clone;
  }
}
