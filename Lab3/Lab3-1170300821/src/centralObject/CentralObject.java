package centralObject;

public abstract class CentralObject {
    protected String name = new String();

    public CentralObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
