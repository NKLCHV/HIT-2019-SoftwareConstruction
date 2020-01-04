package physicalobject;

import ortherapis.Position;

public abstract class PhysicalObject {

  protected String objectName = new String();
  protected Position position = null;

  public PhysicalObject(String name, Position position) {
    this.objectName = name;
    this.position = position;
  }

  public String getObjectName() {
    return objectName;
  }

  public Position getPosition() {
    return position;
  }
}
