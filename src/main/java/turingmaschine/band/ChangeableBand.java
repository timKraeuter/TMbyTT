package turingmaschine.band;

public class ChangeableBand {

	private Band band;

	public ChangeableBand(Band band) {
		this.band = band;
	}

	public static ChangeableBand create(String eingabe) {
		return new ChangeableBand(Band.create(eingabe));
	}

	@Override
	public String toString() {
		return band.toString();
	}

	public void update(Band band) {
		this.band = band;
	}

	public static ChangeableBand create() {
		return ChangeableBand.create("");
	}
}
