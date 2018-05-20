package turingmaschine;

import java.util.Set;

public class TuringMaschine {
	
	// TODO evtl. arbeitsalphabet von eingabealphabet unterscheiden.
	private final Set<Zeichen> arbeitsalphabet;
	
	private final Zustand startZustand;
	private final Set<Zustand> zustaende;
	private final Set<Zustand> endZustaende;
	
	private final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;
	
	private TuringMaschine(final Set<Zeichen> arbeitsalphabet,
			final Zustand startZustand,
			final Set<Zustand> zustaende,
			final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
		this.arbeitsalphabet = arbeitsalphabet;
		this.startZustand = startZustand;
		this.zustaende = zustaende;
		this.endZustaende = endZustaende;
		this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
	}
	
	public static TuringMaschine create(final Set<Zeichen> arbeitsalphabet,
			final Zustand startZustand,
			final Set<Zustand> zustaende,
			final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
		return new TuringMaschine(arbeitsalphabet, startZustand, zustaende, endZustaende, ueberfuehrungsfunktion);
	}
	
	// Wir gehen erstmal von deterministisch aus
	// Hier wird wild ein Band erzeugt und so ein Gedöns.
	public Konfiguration simuliere(final String eingabe) {
		Band.create(eingabe);
		throw new UnsupportedOperationException();
	}
	
	public boolean erkenntEingabe(final String eingabe) {
		return this.simuliere(eingabe).isEndKonfiguration();
	}
	
	public void persistToFile(final String path) {
		throw new UnsupportedOperationException();
	}
	
	
	public void loadFromFile(final String path) {
		throw new UnsupportedOperationException();
	}
	
}
