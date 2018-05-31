package turingmaschine;

import java.net.URL;

import persistenz.TMPersistierer;
import turingmaschine.band.Band;
import turingmaschine.band.ChangeableBand;
import turingmaschine.band.ImmutableBand;

public class TuringMaschinen {

	private final static TuringMaschine addiererMaschine() {
		URL resource = TuringMaschinen.class.getResource("decimalAdditionTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Increment Maschine, welche bei Ausführung den Wert auf dem band
	 * um 1 erhöht.
	 * 
	 * @param band,
	 *            wessen Wert bei Ausführung um 1 erhöht werden soll.
	 * @return Increment-TuringMaschine
	 */
	public final static TuringMaschineMitBand createIncrement(final ChangeableBand band) {
		final TuringMaschine incrementMaschine = incrementMaschine();
		return TuringMaschineMitBand.create(incrementMaschine, band);
	}

	private final static TuringMaschine incrementMaschine() {
		URL resource = TuringMaschinen.class.getResource("incrementerTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Decrement Maschine, welche bei Ausführung den Wert auf dem Band
	 * um 1 verringert. Sollte auf dem Band eine oder mehrere 0 stehen, sodass der
	 * Wert negativ werden würde, werden alle bereits beschriebenen Felder des
	 * Bandes mit § überschrieben werden.
	 * 
	 * @param band,
	 *            wessen Wert bei Ausführung um 1 verringert werden soll.
	 * @return Decrement-TuringMaschine
	 */
	public final static TuringMaschineMitBand createDecrement(final ChangeableBand band) {
		final TuringMaschine decrementMaschine = decrementMaschine();
		return TuringMaschineMitBand.create(decrementMaschine, band);
	}

	private final static TuringMaschine decrementMaschine() {
		URL resource = TuringMaschinen.class.getResource("decrementerTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 addiert und dabei das Ergebnis in das ImmutableBand 3 schreibt.
	 *
	 * @return Additions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createAdd(final ChangeableBand sum1, final ChangeableBand sum2,
			final ChangeableBand result) {
		ChangeableBand sum11 = ChangeableBand.create();
		ChangeableBand sum21 = ChangeableBand.create();
		TuringMaschineMitBand m1 = TuringMaschinen.createCopy(sum1, sum11);
		TuringMaschineMitBand m2 = TuringMaschinen.createCopy(sum2, sum21);
		TuringMaschineMitBand m3 = TuringMaschineMitBand.create(TuringMaschinen.addiererMaschine(), sum11, sum21,
				result);
		return TuringMaschinen.createSeq(m1, TuringMaschinen.createSeq(m2, m3));
	}

	public final static TuringMaschineMitBand createSub(final ChangeableBand minuend, final ChangeableBand subtrahend,
			final ChangeableBand result) {
		return null;
	}

	private final static TuringMaschine copyMaschine() {
		URL resource = TuringMaschinen.class.getResource("copyTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Turingmaschine mit 2 Bändern, welche bei Ausführung den Inhalte
	 * des 1 Bandes auf das 2 ImmutableBand schreibt.
	 *
	 * @return Kopier-Turingmaschine.
	 */
	public static TuringMaschineMitBand createCopy(final ChangeableBand from, final ChangeableBand to) {
		return TuringMaschineMitBand.create(TuringMaschinen.copyMaschine(), from, to);
	}

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 subtrahiert und dabei das Ergebnis in das ImmutableBand 3 schreibt.
	 *
	 * @return Subtraktions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createSub(final ImmutableBand sub, final ImmutableBand min,
			final ImmutableBand result) {
		// TODO Maschine erstellen testen und hier laden.
		return null;
	}

	/**
	 * Erstellt eine Turingmaschine, welche die TuringMaschine tm solang ausführt,
	 * bis das Condition-ImmutableBand den Wert 0 hat.
	 *
	 * @param condition
	 *            zu überprüfende Condition. tm wird solang wiederholt ausgeführt,
	 *            bis das Condition-ImmutableBand den Wert 0 hat.
	 * @param tm
	 *            wiederholt auszuführende Turingmaschine.
	 * @return While-Turingmaschine.
	 */
	public static TuringMaschineMitBand createWhile(final Band condition, final TuringMaschineMitBand tm) {
		// TODO noch nicht angeguckt
		return null;
	}

	/**
	 * Erstellt eine Turingmaschine, welche die TuringMaschine t1 und t2
	 * hintereinander ausführt.
	 *
	 * @param t1
	 *            erste auszuführende Turingmaschine.
	 * @param t2
	 *            zweite auszuführende Turingmaschine.
	 * @return Sequenz-Turingmaschine.
	 */
	public static TuringMaschineMitBand createSeq(final TuringMaschineMitBand t1, final TuringMaschineMitBand t2) {
		return t1.sequence(t2);

	}

}
