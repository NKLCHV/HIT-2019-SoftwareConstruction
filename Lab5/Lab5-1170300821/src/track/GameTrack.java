package track;

public class GameTrack extends Track {

  private int group;
  private int trackRadius;

  public GameTrack(int trackRadius) {
    super(trackRadius);
    this.trackRadius = trackRadius;
  }

  @Override
  public int getTrackRadius() {
    return trackRadius;
  }

  public void setTrackRadius(int trackRadius) {
    this.trackRadius = trackRadius;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  public int getGroup() {
    return group;
  }
}
