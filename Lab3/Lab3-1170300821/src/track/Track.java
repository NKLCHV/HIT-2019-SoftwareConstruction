package track;

public abstract class Track {
    protected int trackRadius;//轨道半径

    public Track(int trackRadius) {
        this.trackRadius = trackRadius;
    }

    public int getTrackRadius() {
        return trackRadius;
    }

    public boolean equal(Track track) {
        return this.trackRadius == track.getTrackRadius();
    }
}
