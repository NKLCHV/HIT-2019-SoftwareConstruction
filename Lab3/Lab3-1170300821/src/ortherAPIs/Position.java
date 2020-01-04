package ortherAPIs;

public class Position {
    public double polarAngel;
    public double polarLength;

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

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        if (other.polarLength != this.polarLength)
            return false;
        else if (other.polarAngel != this.polarAngel)
            return false;

        return true;
    }
}
