package physicalObject;

import ortherAPIs.Position;

public class Friend extends PhysicalObject{
    private int age;
    private String gender;//出于民主和人权的考虑，可拓展
    private String name;

    public Friend (String name, int age, String gender) {
        super(name, null);
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}