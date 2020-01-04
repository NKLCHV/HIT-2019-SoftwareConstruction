package track;

public class AtomStructureTrack extends Track{
    private int trackNumber;
    private String atomName ;
    public AtomStructureTrack(int trackNumber,String atomName) {
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
