package ADT;

public class Rung {

  /*
   * AF(monkey,position)=Rung;
   * RI: position must not be negative;
   * Safety from Representation Exposure: All the key fields are private and immutable.
   */
  private Monkey monkey = null;
  private int position = 0;

  private void checkRep() {
    assert position >= 0;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Monkey getMonkey() {
    return monkey;
  }

  public void setMonkey(Monkey monkey) {
    this.monkey = monkey;
  }

  /*
   * @return 该踏板上是否有猴子
   */
  public boolean hasMonkey() {
    if (monkey != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Rung [monkey=");
    builder.append(monkey);
    builder.append(", position=");
    builder.append(position);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((monkey == null) ? 0 : monkey.hashCode());
    result = prime * result + position;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Rung)) {
      return false;
    }
    Rung other = (Rung) obj;
    if (monkey == null) {
      if (other.monkey != null) {
        return false;
      }
    } else if (!monkey.equals(other.monkey)) {
      return false;
    }
    if (position != other.position) {
      return false;
    }
    return true;
  }

  public Rung(Monkey input, int position) {
    setPosition(position);
    setMonkey(input);
    checkRep();
  }

}
