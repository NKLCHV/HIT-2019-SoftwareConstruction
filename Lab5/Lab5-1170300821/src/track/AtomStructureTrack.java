package track;

public class AtomStructureTrack extends Track {

  private int trackNumber;
  private String atomName;

  /**
   * 原子轨道.
   * @param trackNumber 轨道半径
   * @param atomName 原子名
   */
  public AtomStructureTrack(int trackNumber, String atomName) {
    super(1);
    this.trackNumber = trackNumber;
    this.atomName = atomName;
  }

  public int getTrackNumber() {
    return trackNumber;
  }

  public String getAtomName() {
    return atomName;
  }
}
