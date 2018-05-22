package turingmaschine;

import java.util.Optional;
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
		final Band eingabeBand = Band.create(eingabe);
		
		Konfiguration laufendeConfig = Konfiguration.create(this.startZustand, eingabeBand, this);
		while (!laufendeConfig.isEndKonfiguration()) {
			final Optional<Konfiguration> naechsteConfig = this.step(laufendeConfig);
			if (!naechsteConfig.isPresent()) {
				return laufendeConfig;
			}
			laufendeConfig = naechsteConfig.get();
		}
		return laufendeConfig;
	}
	
	private Optional<Konfiguration> step(final Konfiguration konfiguration) {
		final Optional<ElementDerUeberfuehrungsfunktion> ueberfuehrung =
				this.ueberfuehrungsfunktion.stream().filter(e -> e.istPassendeUeberfuehrungZu(konfiguration)).findFirst();
		if (ueberfuehrung.isPresent()) {
			return Optional.of(konfiguration.doUeberfuehrung(ueberfuehrung.get()));
		}
		return Optional.empty();
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
	
	public boolean isEndzustand(final Zustand moeglicherEndzustand) {
		return this.endZustaende.contains(moeglicherEndzustand);
	}
	
}
