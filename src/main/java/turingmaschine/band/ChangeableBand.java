package turingmaschine.band;

public class ChangeableBand {

	private Band band;

	public ChangeableBand(final Band band) {
		this.band = band;
	}

	public static ChangeableBand create(final String eingabe) {
		return new ChangeableBand(Band.create(eingabe));
	}

	@Override
	public String toString() {
		return this.band.toString();
	}

	public void update(final Band band) {
		this.band = band;
	}

	public static ChangeableBand create() {
		return ChangeableBand.create("");
	}

    public void wipe() {
	    this.band = Band.create("");
    }
}
