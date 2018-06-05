package turingmaschine.band;

import turingmaschine.band.zeichen.Zeichen;

/**
 * Band einer TM, das verändert werden kann bzw. Band, dessen Attribut band umgesetzt werden kann.
 */
public class ChangeableBand implements Band {
	
	private ImmutableBand band;
	
	private ChangeableBand(final ImmutableBand band) {
		this.band = band;
	}
	
	public static ChangeableBand create(final ImmutableBand band) {
		return new ChangeableBand(band);
	}
	
	public static ChangeableBand create(final String eingabe) {
		return new ChangeableBand(ImmutableBand.create(eingabe));
	}
	
	public static ChangeableBand create() {
		return ChangeableBand.create("");
	}
	
	/**
	 * Verarbeitet die übergebenen Parameter und setzt this.band neu.
	 */
	@Override
	public Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen) {
		this.update(this.band.verarbeite(zuSchreibendesZeichen, lesekopfBewegung, gelesenesZeichen));
		return this;
	}
	
	public void update(final ImmutableBand band) {
		this.band = band;
	}
	
	public void wipe() {
		this.band = ImmutableBand.create("");
	}
	
	@Override
	public Zeichen getAktuellesZeichen() {
		return this.band.getAktuellesZeichen();
	}
	
	@Override
	public String toString() {
		return this.band.toString();
	}
}
