package physicalObject;

public class Electron extends PhysicalObject{
    private String atomName;

    /**
     *
     * @param atomicName
     */
    public Electron(String atomicName) {
        super("electron", null);
        this.atomName = atomicName;
    }

    public String getAtomName() {
        return atomName;
    }

    @Override
    public int hashCode(){
       return getObjectName().hashCode()*31+ atomName.hashCode();
    }
}
