package physicalObject;

import ortherAPIs.Position;

public class Athlete extends PhysicalObject {
    // private String name = new String();//姓名
    private int number;//号码
    private String country = new String();//国籍
    private int age;//年龄
    private double bestgrade;//本年度最好成绩
    //   private Position position = null;//位置

    /**
     * @param name
     * @param number
     * @param country
     * @param age
     * @param bestgrade
     */
    public Athlete(String name, int number, String country, int age, double bestgrade) {
        super(name, null);
        this.number = number;
        this.country = country;
        this.age = age;
        this.bestgrade = bestgrade;
    }

    public int getNumber() {
        return number;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public double getBestgrade() {
        return bestgrade;
    }

//    @Override
//    public int hashCode() {
//        return getObjectName().hashCode() * 31 + position.hashCode();
//    }

    public boolean equals(Athlete athlete) {
        return athlete.getNumber() == this.number &&
                athlete.getObjectName().equals(this.objectName) &&
                athlete.getAge() == this.age &&
                athlete.getCountry().equals(this.country);
    }
}
