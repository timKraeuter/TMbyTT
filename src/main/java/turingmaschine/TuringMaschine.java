package turingmaschine;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TuringMaschine {
	
	private final Zustand startZustand;
	private final Set<Zustand> endZustaende;
	
	private final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;
	
	private final int anzahlDerBaender;
	
	private TuringMaschine(final Zustand startZustand, final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion,
			final int anzahlDerBaender) {
		this.startZustand = startZustand;
		this.endZustaende = endZustaende;
		this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
		this.anzahlDerBaender = anzahlDerBaender;
	}
	
	public static TuringMaschine create(final Zustand startZustand, final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion,
			final int anzahlDerBaender) {
		return new TuringMaschine(startZustand, endZustaende, ueberfuehrungsfunktion, anzahlDerBaender);
	}
	
	// Wir gehen erstmal von deterministisch aus
	// Hier wird wild ein Band erzeugt und so ein Gedöns.
	public Set<Konfiguration> simuliere(final List<String> eingaben) {
		
		final List<Band> eingabeBaender = eingaben.stream().map(eingabe -> Band.create(eingabe)).collect(Collectors.toList());
		final Konfiguration startConfig = Konfiguration.create(this.startZustand, eingabeBaender, this);
		Set<Konfiguration> naechsteKonfigurationen = Collections.singleton(startConfig);
		
		final Set<Konfiguration> endKonfigurationen = new HashSet<>();
		while (!naechsteKonfigurationen.isEmpty()) {
			final HashSet<Konfiguration> neueNaechsteConfigs = new HashSet<>();
			
			for (final Konfiguration config : naechsteKonfigurationen) {
				if (config.isEndKonfiguration()) {
					endKonfigurationen.add(config);
				} else {
					neueNaechsteConfigs.addAll(this.step(config));
				}
			}
			
			naechsteKonfigurationen = neueNaechsteConfigs;
		}
		return endKonfigurationen;
	}
	
	private Set<Konfiguration> step(final Konfiguration konfiguration) {
		return this.ueberfuehrungsfunktion.stream().filter(e -> e.istPassendeUeberfuehrungZu(konfiguration))
				.map(konfiguration::doUeberfuehrung).collect(Collectors.toSet());
	}
	
	public boolean erkenntEingabe(final String eingabe) {
		return !this.simuliere(Collections.singletonList(eingabe)).isEmpty();
	}
	
	public void persistToFile(final String path) {
		throw new UnsupportedOperationException();
	}
	
	public void loadFromFile(final String path) {
		throw new UnsupportedOperationException();
	}
	
	public boolean isEndzustand(final Zustand moeglicherEndzustand) {
		return this.endZustaende.contains(moeglicherEndzustand);
	}
	
	public Set<Zeichen> getArbeitsalphabet() {
		// final Set<Zeichen> arbeitsalphabet = this.ueberfuehrungsfunktion.stream()
		// .flatMap(ElementDerUeberfuehrungsfunktion::getZuSchreibendeZeichen).collect(Collectors.toSet());
		// arbeitsalphabet.addAll(this.getEingabealphabet());
		// arbeitsalphabet.add(Blank.getInstance());
		// return arbeitsalphabet;
		return null;
	}
	
	// TODO was bringt das Eingabealphabet und der Blank darf hier eigentlich nicht
	// rein. siehe def. seite 4 und 5
	public Set<Zeichen> getEingabealphabet() {
		// return this.ueberfuehrungsfunktion.stream().map(ElementDerUeberfuehrungsfunktion::getEingabe)
		// .collect(Collectors.toSet());
		return null;
	}
	
	/**
	 * @return alle Zustände des Automaten einschließlich Anfangs- und Endzustand.
	 */
	public Set<Zustand> getZustaende() {
		throw new UnsupportedOperationException();
	}
	
	public static TuringMaschinenBuilder builder() {
		return TuringMaschinenBuilder.create();
	}
	
	/**
	 * Erstellt eine Turingmaschine, welche bei Ausführung die Bänder sum1 und sum2
	 * addiert und dabei das Ergebnis in das result-Band schreibt.
	 * 
	 * @param sum1
	 *            zu addierendes Band.
	 * @param sum2
	 *            zu addierendes Band.
	 * @param result
	 *            Band, auf welches bei Ausführung der TuringMaschine das Ergebnis
	 *            geschrieben wird.
	 * @return Additions-Turingmaschine.
	 */
	public static TuringMaschine createAdd(final Band sum1, final Band sum2, final Band result) {
		return null;
	}
	
	/**
	 * Erstellt eine Turingmaschine, welche bei Ausführung die Bänder sub und min
	 * voneinander subtrahiert und dabei das Ergebnis in das result-Band schreibt.
	 * 
	 * @param sub
	 *            subtrahend-Band.
	 * @param min
	 *            minuend-Band.
	 * @param result
	 *            Band, auf welches bei Ausführung der TuringMaschine das Ergebnis
	 *            geschrieben wird.
	 * @return Subtraktions-Turingmaschine.
	 */
	public static TuringMaschine createSub(final Band sub, final Band min, final Band result) {
		return null;
	}
	
	/**
	 * Erstellt eine Turingmaschine, welche bei Ausführung die Inhalte des
	 * from-Bandes auf das into-Band schreibt.
	 * 
	 * @param from
	 *            zu Kopierendes Band.
	 * @param into
	 *            Band in welches Kopiert werden soll.
	 * @return Kopier-Turingmaschine.
	 */
	public static TuringMaschine createCopy(final Band from, final Band into) {
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
	public static TuringMaschine createWhile(final Band condition, final TuringMaschine tm) {
		return null;
	}
	
	/**
	 * Erstellt eine Turingmaschine, welche die TuringMaschine t1 und t2
	 * hintereinander ausführen kann.
	 * 
	 * @param t1
	 *            erste auszuführende Turingmaschine.
	 * @param t2
	 *            zweite auszuführende Turingmaschine.
	 * @return Sequenz-Turingmaschine.
	 */
	public static TuringMaschine createSeq(final TuringMaschine t1, final TuringMaschine t2) {
		return null;
	}
}
