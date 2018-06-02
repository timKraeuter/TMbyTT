package turingmaschine;

import persistenz.TMPersistierer;
import turingmaschine.band.ChangeableBand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.Zeichen;
import util.CollectionHelper;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TuringMaschinen {

	private static TuringMaschine addiererMaschine() {
		final URL resource = TuringMaschinen.class.getResource("decimalAdditionTM.xml");
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
	public static TuringMaschineMitBand createIncrement(final ChangeableBand band) {
		final TuringMaschine incrementMaschine = TuringMaschinen.incrementMaschine();
		return TuringMaschineMitBand.create(incrementMaschine, band);
	}

	private static TuringMaschine incrementMaschine() {
		final URL resource = TuringMaschinen.class.getResource("incrementerTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Increment Maschine, welche bei Ausführung den Wert auf dem band
	 * um 1 erhöht.
	 *
	 * @param ist0Band,
	 *            wessen Wert bei Ausführung um 1 erhöht werden soll.
	 * @return Increment-TuringMaschine
	 */
	public static TuringMaschineMitBand createaPruefe0Maschine(final ChangeableBand ist0Band,
			final ChangeableBand ausgabeBand) {
		final TuringMaschine pruefe0Maschine = TuringMaschinen.pruefe0Maschine();
		return TuringMaschineMitBand.create(pruefe0Maschine, ist0Band, ausgabeBand);
	}

	private static TuringMaschine pruefe0Maschine() {
		final URL resource = TuringMaschinen.class.getResource("pruefe0Maschine.xml");
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
	public static TuringMaschineMitBand createDecrement(final ChangeableBand band) {
		final TuringMaschine decrementMaschine = TuringMaschinen.decrementMaschine();
		return TuringMaschineMitBand.create(decrementMaschine, band);
	}

	private static TuringMaschine decrementMaschine() {
		final URL resource = TuringMaschinen.class.getResource("decrementerTM.xml");
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
		// final ChangeableBand sum11 = ChangeableBand.create();
		// final ChangeableBand sum21 = ChangeableBand.create();
		// final TuringMaschineMitBand m1 = TuringMaschinen.copy(sum1, sum11);
		// final TuringMaschineMitBand m2 = TuringMaschinen.copy(sum2, sum21);
		// final TuringMaschineMitBand m3 =
		// TuringMaschineMitBand.create(TuringMaschinen.addiererMaschine(), sum11,
		// sum21,
		// result);
		// return TuringMaschinen.createSeq(m1, TuringMaschinen.createSeq(m2, m3));

		final ChangeableBand sum1i = ChangeableBand.create();
		final ChangeableBand sum2i = ChangeableBand.create();
		final TuringMaschineMitBand c1 = TuringMaschinen.copy(sum1, sum1i);
		final TuringMaschineMitBand c2 = TuringMaschinen.copy(sum2, sum2i);
		final TuringMaschineMitBand cResult = TuringMaschinen.copy(sum1i, result);

		final TuringMaschineMitBand addierer = TuringMaschinen.createSeq(TuringMaschinen.createIncrement(sum1i),
				TuringMaschinen.createDecrement(sum2i));
		final TuringMaschineMitBand whileM = TuringMaschinen.createWhile(sum2i, addierer);

		return TuringMaschinen.createSeq(TuringMaschinen.createSeq(c1, TuringMaschinen.createSeq(c2, whileM)), cResult);
	}

	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 subtrahiert und dabei das Ergebnis in das Band 3 schreibt.
	 *
	 * @return Subtraktions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createSub(final ChangeableBand minuend, final ChangeableBand subtrahend,
			final ChangeableBand result) {
		final ChangeableBand subtrahend1 = ChangeableBand.create();
		final ChangeableBand parkplatz = ChangeableBand.create();
		final TuringMaschineMitBand einparken = TuringMaschinen.copy(minuend, parkplatz);
		final TuringMaschineMitBand copy = TuringMaschinen.copy(subtrahend, subtrahend1);
		final TuringMaschineMitBand ausparken = TuringMaschinen.copy(parkplatz, result);

		final TuringMaschineMitBand subtrahierer = TuringMaschinen.createSeq(TuringMaschinen.createDecrement(parkplatz),
				TuringMaschinen.createDecrement(subtrahend1));
		final TuringMaschineMitBand whileM = TuringMaschinen.createWhile(subtrahend1, subtrahierer);

		return TuringMaschinen.createSeq(TuringMaschinen.createSeq(einparken, TuringMaschinen.createSeq(copy, whileM)),
				ausparken);
	}

	private static TuringMaschine copyMaschine() {
		final URL resource = TuringMaschinen.class.getResource("copyTM.xml");
		return (TuringMaschine) TMPersistierer.getInstance().lade(resource);
	}

	/**
	 * Erstellt eine Turingmaschine mit 2 Bändern, welche bei Ausführung den Inhalte
	 * des 1 Bandes auf das 2 ImmutableBand schreibt.
	 *
	 * @return Kopier-Turingmaschine.
	 */
	public static TuringMaschineMitBand copy(final ChangeableBand from, final ChangeableBand to) {
		return TuringMaschineMitBand.create(TuringMaschinen.copyMaschine(), from, to);
	}

	/**
	 * Erstellt eine Turingmaschine, welche die TuringMaschine tm solang ausführt,
	 * bis das Condition-ImmutableBand den Wert 0 hat.
	 *
	 * @param bandWelchesAuf0GeprueftWird
	 *            tm wird solang wiederholt ausgeführt, bis das
	 *            bandWelchesAuf0GeprueftWird den Wert 0 hat.
	 * @param tm
	 *            wiederholt auszuführende Turingmaschine.
	 * @return While-Turingmaschine.
	 */
	public static TuringMaschineMitBand createWhile(final ChangeableBand bandWelchesAuf0GeprueftWird,
			final TuringMaschineMitBand tm) {
		final TuringMaschinenBuilder builder = TuringMaschine.builder();
		final Zustand stop = Zustand.create("stop");
		final TuringMaschineMitBand pruefe0Maschine = TuringMaschinen
				.createaPruefe0Maschine(bandWelchesAuf0GeprueftWird, ChangeableBand.create(""));
		builder.startZustand(pruefe0Maschine.getMaschine().getStartZustand());
		builder.addEndZustand(stop);
		builder.anzahlDerBaender(pruefe0Maschine.getBaender().size() + tm.getBaender().size());
		builder.ueberfuehrungsfunktion(TuringMaschinen.ueberfuehrungVonWhileBerechnen(pruefe0Maschine, tm, stop));
		final TuringMaschine whileTM = builder.build();

		final List<ChangeableBand> baender = new ArrayList<>(pruefe0Maschine.getBaender());
		baender.addAll(tm.getBaender());

		return TuringMaschineMitBand.create(whileTM, baender);
	}

	private static Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungVonWhileBerechnen(
			final TuringMaschineMitBand pruefe0Maschine, final TuringMaschineMitBand tm,
			final Zustand endZustandWhile) {
		final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion = new HashSet<>();

		ueberfuehrungsfunktion
				.addAll(TuringMaschinen.neueUeberfuehrungenBerechnen(pruefe0Maschine, tm, endZustandWhile));
		ueberfuehrungsfunktion.addAll(TuringMaschinen.alteUeberfuehrungenErweitern(pruefe0Maschine, tm));

		return ueberfuehrungsfunktion;
	}

	private static Set<ElementDerUeberfuehrungsfunktion> alteUeberfuehrungenErweitern(
			final TuringMaschineMitBand pruefe0Maschine, final TuringMaschineMitBand tm) {
		final Set<ElementDerUeberfuehrungsfunktion> alteUeberfuehrungenErweitertUmNeutral = new HashSet<>();

		alteUeberfuehrungenErweitertUmNeutral.addAll(pruefe0Maschine
				.getMaschine().getUeberfuehrungsfunktion().stream().map(ueberfuehrung -> TuringMaschine
						.erstelleUeberfuhrungMitBeliebigenZeichen(ueberfuehrung, tm.getBaender().size(), false))
				.collect(Collectors.toSet()));

		alteUeberfuehrungenErweitertUmNeutral.addAll(tm.getMaschine().getUeberfuehrungsfunktion().stream()
				.map(ueberfuehrung -> TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(ueberfuehrung,
						pruefe0Maschine.getBaender().size(), true))
				.collect(Collectors.toSet()));

		return alteUeberfuehrungenErweitertUmNeutral;
	}

	private static Set<ElementDerUeberfuehrungsfunktion> neueUeberfuehrungenBerechnen(
			final TuringMaschineMitBand pruefe0Maschine, final TuringMaschineMitBand tm,
			final Zustand endZustandWhile) {

		final Zustand endZustand = pruefe0Maschine.getMaschine().getEndZustaende().iterator().next();
		final ElementDerUeberfuehrungsfunktion inTMMit1 = ElementDerUeberfuehrungsfunktion.create(endZustand,
				tm.getMaschine().getStartZustand(),
				CollectionHelper.createList(BeliebigesZeichen.getInstance(), Zeichen.create('1')),
				CollectionHelper.createList(BeliebigesZeichen.getInstance(), Zeichen.create('1')),
				CollectionHelper.createList(Lesekopfbewegung.N, Lesekopfbewegung.N));
		final ElementDerUeberfuehrungsfunktion inStopMit0 = ElementDerUeberfuehrungsfunktion.create(endZustand,
				endZustandWhile, CollectionHelper.createList(BeliebigesZeichen.getInstance(), Zeichen.create('0')),
				CollectionHelper.createList(BeliebigesZeichen.getInstance(), Zeichen.create('0')),
				CollectionHelper.createList(Lesekopfbewegung.N, Lesekopfbewegung.N));

		final Set<ElementDerUeberfuehrungsfunktion> neueUeberfuehrungen = new HashSet<>();

		neueUeberfuehrungen
				.add(TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(inTMMit1, tm.getBaender().size(), false));
		neueUeberfuehrungen.add(
				TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(inStopMit0, tm.getBaender().size(), false));

		final Set<ElementDerUeberfuehrungsfunktion> nachPruefe0Neutral = tm.getMaschine().getEndZustaende().stream()
				.map(endZustandVonTM -> ElementDerUeberfuehrungsfunktion.create(endZustandVonTM,
						pruefe0Maschine.getMaschine().getStartZustand(),
						CollectionHelper.createList(BeliebigesZeichen.getInstance(), BeliebigesZeichen.getInstance()),
						CollectionHelper.createList(BeliebigesZeichen.getInstance(), BeliebigesZeichen.getInstance()),
						CollectionHelper.createList(Lesekopfbewegung.N, Lesekopfbewegung.N)))
				.collect(Collectors.toSet());

		neueUeberfuehrungen
				.addAll(nachPruefe0Neutral
						.stream().map(ueberfuehrung -> TuringMaschine
								.erstelleUeberfuhrungMitBeliebigenZeichen(ueberfuehrung, tm.getBaender().size(), true))
						.collect(Collectors.toSet()));

		return neueUeberfuehrungen;
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
