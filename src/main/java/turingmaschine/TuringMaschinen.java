package turingmaschine;

import persistenz.TMPersistierer;
import turingmaschine.band.ChangeableBand;
import turingmaschine.band.ImmutableBand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TuringMaschinen {
	private static final String PATH_TO_TMs = "src/main/resources/turingmaschinen/";

	private final static TuringMaschine addiererMaschine() {
		return (TuringMaschine) TMPersistierer.getInstance()
				.lade(new File(TuringMaschinen.PATH_TO_TMs + "decimalAdditionTM.xml"));
	}

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 addiert und dabei das Ergebnis in das ImmutableBand 3 schreibt.
	 *
	 * @return Additions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createAdd(final ChangeableBand sum1, final ChangeableBand sum2,
			final ChangeableBand result) {
		return TuringMaschineMitBand.create(TuringMaschinen.addiererMaschine(), sum1, sum2, result);
	}

	public static void main(String[] args) {
		Zustand nachR = Zustand.create("nachRechts");
		Zustand add = Zustand.create("add");
		Zustand fertig = Zustand.create("fertig");
		Set<Zustand> endZustaende = Collections.singleton(fertig);
		Set<ElementDerUeberfuehrungsfunktion> delta = new HashSet<>();
		List<Lesekopfbewegung> R = Collections.singletonList(Lesekopfbewegung.R);
		List<Lesekopfbewegung> L = Collections.singletonList(Lesekopfbewegung.L);
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(0), liste(0), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(1), liste(1), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(2), liste(2), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(3), liste(3), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(4), liste(4), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(5), liste(5), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(6), liste(6), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(7), liste(7), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(8), liste(8), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, nachR, liste(9), liste(9), R));
		delta.add(ElementDerUeberfuehrungsfunktion.create(nachR, add, Collections.singletonList(Blank.getInstance()),
				Collections.singletonList(Blank.getInstance()), L));
		delta.add(ElementDerUeberfuehrungsfunktion.create(add, add, liste(9), liste(0), L));
		delta.add(ElementDerUeberfuehrungsfunktion.create(add, fertig, liste(9), liste(0), L));
		TuringMaschine.create(nachR, endZustaende, delta, 1);
	}

	private static List<Zeichen> liste(Integer zahl) {
		return Collections.singletonList(Zeichen.create(zahl.toString().charAt(0)));
	}

	private final static TuringMaschine copyMaschine() {
		return (TuringMaschine) TMPersistierer.getInstance().lade(new File(TuringMaschinen.PATH_TO_TMs + "copyTM.xml"));
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
	public static TuringMaschineMitBand createWhile(final ImmutableBand condition, final TuringMaschineMitBand tm) {
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
