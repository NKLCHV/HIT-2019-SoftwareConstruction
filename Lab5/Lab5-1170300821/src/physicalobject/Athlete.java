package physicalobject;

public class Athlete extends PhysicalObject {

  private String name = new String();//姓名
  private int number;//号码
  private String country = new String();//国籍
  private int age;//年龄
  private double bestgrade;//本年度最好成绩
  //   private Position position = null;//位置

  /**运动员.
   * @param name 姓名
   * @param number 编号
   * @param country 国籍
   * @param age 年龄
   * @param bestgrade 最佳成绩
   */
  public Athlete(String name, int number, String country, int age, double bestgrade) {
    super(name, null);
    this.name = name;
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

  public String getName() {
    return name;
  }
}
