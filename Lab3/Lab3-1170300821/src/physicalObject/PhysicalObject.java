package physicalObject;

import ortherAPIs.Position;

public abstract class PhysicalObject {
    protected String objectName = new String();
    protected Position position = null;

    public PhysicalObject(String name, Position position) {
        this.objectName = objectName;
        this.position = this.position;
    }

    public String getObjectName() {
        return objectName;
    }

    public Position getPosition() {
        return position;
    }

//    @Override
//    public int hashCode() {
//        return objectName.hashCode() * 31 + position.hashCode();
//    }
}
