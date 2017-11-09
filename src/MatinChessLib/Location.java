package MatinChessLib;

public class Location {
    public final byte file;
    public final byte rank;

    public Location(final byte file, final byte rank)
    {
        this.file = file;
        this.rank = rank;
    }

    public boolean isValid()
    {
        return  file >= 0 && file< 8 && rank >= 0 && rank < 8;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location)
        {
            Location casted = (Location)obj;
            return file == casted.file && rank == casted.rank;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return file<<8 + rank;
    }
}
