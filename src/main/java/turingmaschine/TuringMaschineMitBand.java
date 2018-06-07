package turingmaschine;

import turingmaschine.band.ChangeableBand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Verbindung einer {@link TuringMaschine} mit ihren Bändern.
 */
public class TuringMaschineMitBand {
	
	private final TuringMaschine maschine;
	private final List<ChangeableBand> baender;
	
	private TuringMaschineMitBand(final TuringMaschine machine, final List<ChangeableBand> baender) {
		this.maschine = machine;
		if (this.maschine.getAnzahlDerBaender() != baender.size()) {
			throw new RuntimeException("TM mit Band muss genau so viele Bänder haben, wie die Modell TM spezifiziert!");
		}
		this.baender = baender;
		
	}
	
	public static TuringMaschineMitBand create(final TuringMaschine machine, final ChangeableBand... bands) {
		return new TuringMaschineMitBand(machine, Arrays.asList(bands));
	}
	
	public static TuringMaschineMitBand create(final TuringMaschine machine, final List<ChangeableBand> baender) {
		return new TuringMaschineMitBand(machine, baender);
	}
	
	/**
	 * Komponiert zwei Turing-Maschinen mit Band.
	 * 
	 * @param turingMaschineMitBand
	 *            , mit der this komponiert wird
	 * @return Turing-Maschine mit Band, die durch Komponieren der beiden Turing-Maschinen mit Band entsteht.
	 */
	public TuringMaschineMitBand sequence(final TuringMaschineMitBand turingMaschineMitBand) {
		final List<ChangeableBand> changeableBaender = new ArrayList<>(this.baender);
		changeableBaender.addAll(turingMaschineMitBand.baender);
		return TuringMaschineMitBand.create(this.maschine.sequence(turingMaschineMitBand.maschine), changeableBaender);
	}
	
	public void simuliere() {
		this.maschine.simuliereDeterministisch(this.baender, false);
	}

	public void simuliereMitAusgabe() {
		this.maschine.simuliereDeterministisch(this.baender, true);
	}
	
	public TuringMaschine getMaschine() {
		return this.maschine;
	}
	
	public List<ChangeableBand> getBaender() {
		return this.baender;
	}
}
