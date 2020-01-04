package centralobject;

public class SocialNetworkCentralObject extends CentralObject {

  private int age;
  private String gender;//出于民主和人权的考虑，可拓展

  /**
   * 中心用户.
   *
   * @param name .
   * @param age .
   * @param gender .
   */
  public SocialNetworkCentralObject(String name, int age, String gender) {
    super(name);
    this.age = age;
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }
}
