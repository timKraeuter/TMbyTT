package turingmaschine;

import persistenz.TMPersistierer;
import turingmaschine.band.Band;
import turingmaschine.band.ChangeableBand;

import java.io.File;

public class TuringMaschinen {
	private static final String PATH_TO_TMs = "src/main/resources/turingmaschinen/";
	// return (TuringMaschine) TMPersistierer.getInstance().lade(new
	// File(TuringMaschinen.PATH_TO_TMs + "copyTM.xml"));

	private final static TuringMaschine addiererMaschine = (TuringMaschine) TMPersistierer.getInstance()
			.lade(new File(TuringMaschinen.PATH_TO_TMs + "decimalAdditionTM.xml"));

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 addiert und dabei das Ergebnis in das Band 3 schreibt.
	 *
	 * @return Additions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createAdd(ChangeableBand sum1, ChangeableBand sum2, ChangeableBand result) {
		return TuringMaschineMitBand.create(addiererMaschine, sum1, sum2, result);
	}

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 subtrahiert und dabei das Ergebnis in das Band 3 schreibt.
	 *
	 * @return Subtraktions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createSub(final Band sub, final Band min, final Band result) {
		// TODO Maschine erstellen testen und hier laden.
		return null;
	}

	/**
	 * Erstellt eine Turingmaschine mit 2 Bändern, welche bei Ausführung den Inhalte
	 * des 1 Bandes auf das 2 Band schreibt.
	 *
	 * @return Kopier-Turingmaschine.
	 */
	public static TuringMaschineMitBand createCopy() {
		return null;
	}

	/**
	 * Erstellt eine Turingmaschine, welche die TuringMaschine tm solang ausführt,
	 * bis das Condition-Band den Wert 0 hat.
	 *
	 * @param condition
	 *            zu überprüfende Condition. tm wird solang wiederholt ausgeführt,
	 *            bis das Condition-Band den Wert 0 hat.
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
		return null;

	}

}
