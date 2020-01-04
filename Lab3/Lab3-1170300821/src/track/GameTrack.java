package track;

public class GameTrack extends Track {
    private int Group;
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
        Group = group;
    }

    public int getGroup() {
        return Group;
    }
}
