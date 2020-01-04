package ortherapis;

public class Position {

  private double polarAngel = 0;
  private double polarLength = 0;

  public Position(double polarAngel, double polarLength) {
    this.polarAngel = polarAngel;
    this.polarLength = polarLength;
  }

  public double getPolarAngel() {
    return polarAngel;
  }

  public double getPolarLength() {
    return polarLength;
  }
}
