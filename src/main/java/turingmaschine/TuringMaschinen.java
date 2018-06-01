package turingmaschine;

import persistenz.TMPersistierer;
import turingmaschine.band.ChangeableBand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
     * @param band,
     *            wessen Wert bei Ausführung um 1 erhöht werden soll.
     * @return Increment-TuringMaschine
     */
    public static TuringMaschineMitBand createPruefe0Maschine(final ChangeableBand band) {
        final TuringMaschine incrementMaschine = TuringMaschinen.incrementMaschine();
        return TuringMaschineMitBand.create(incrementMaschine, band);
    }

    private static TuringMaschine createPruefe0Maschine() {
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
		final ChangeableBand sum11 = ChangeableBand.create();
		final ChangeableBand sum21 = ChangeableBand.create();
		final TuringMaschineMitBand m1 = TuringMaschinen.createCopy(sum1, sum11);
		final TuringMaschineMitBand m2 = TuringMaschinen.createCopy(sum2, sum21);
		final TuringMaschineMitBand m3 = TuringMaschineMitBand.create(TuringMaschinen.addiererMaschine(), sum11, sum21,
				result);
		return TuringMaschinen.createSeq(m1, TuringMaschinen.createSeq(m2, m3));
	}


	/**
	 * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder
	 * 1 und 2 subtrahiert und dabei das Ergebnis in das Band 3 schreibt.
	 *
	 * @return Subtraktions-Turingmaschine für Dezimalzahlen.
	 */
	public static TuringMaschineMitBand createSub(final ChangeableBand minuend, final ChangeableBand subtrahend,
			final ChangeableBand result) {
		final ChangeableBand minuend1 = ChangeableBand.create();
		final ChangeableBand subtrahend1 = ChangeableBand.create();
		final TuringMaschineMitBand m1 = TuringMaschinen.createCopy(minuend, minuend1);
		final TuringMaschineMitBand m2 = TuringMaschinen.createCopy(subtrahend, subtrahend1);

		final TuringMaschineMitBand subtrahierer = TuringMaschinen.createDecrement(minuend1);
		final TuringMaschineMitBand whileM = TuringMaschinen.createWhile(subtrahend1, subtrahierer);

		return TuringMaschinen.createSeq(m1, TuringMaschinen.createSeq(m2, whileM));
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
	public static TuringMaschineMitBand createCopy(final ChangeableBand from, final ChangeableBand to) {
		return TuringMaschineMitBand.create(TuringMaschinen.copyMaschine(), from, to);
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
	public static TuringMaschineMitBand createWhile(final ChangeableBand condition, final TuringMaschineMitBand tm) {
        final TuringMaschinenBuilder builder = TuringMaschine.builder();
        final Zustand stop = Zustand.create("stop");
        final TuringMaschineMitBand pruefe0Maschine = TuringMaschinen.createPruefe0Maschine(condition);
        builder.startZustand(pruefe0Maschine.getMaschine().getStartZustand());
        builder.addEndZustand(stop);

        builder.anzahlDerBaender(pruefe0Maschine.getBaender().size() + tm.getBaender().size());

        builder.ueberfuehrungsfunktion(TuringMaschinen.ueberfuehrungVonWhileBerechnen(pruefe0Maschine, tm));

        return null;
	}

    private static Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungVonWhileBerechnen(final TuringMaschineMitBand pruefe0Maschine, final TuringMaschineMitBand tm) {
        final HashSet<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion = new HashSet<>();
        final Zustand endZustand = pruefe0Maschine.getMaschine().getEndZustaende().iterator().next();
        final ElementDerUeberfuehrungsfunktion inTMMit11 = ElementDerUeberfuehrungsfunktion.create(endZustand,
                tm.getMaschine().getStartZustand(),
                Collections.singletonList(Zeichen.create('1')),
                Collections.singletonList(Zeichen.create('1')),
                Collections.singletonList(Lesekopfbewegung.N));
        final ElementDerUeberfuehrungsfunktion inStopMit00 = ElementDerUeberfuehrungsfunktion.create(endZustand,
                tm.getMaschine().getStartZustand(),
                Collections.singletonList(Zeichen.create('0')),
                Collections.singletonList(Zeichen.create('0')),
                Collections.singletonList(Lesekopfbewegung.N));

        ueberfuehrungsfunktion.add(TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(inTMMit11, tm.getBaender().size(), false));
        ueberfuehrungsfunktion.add(TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(inStopMit00, tm.getBaender().size(), false));

        return ueberfuehrungsfunktion;
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
