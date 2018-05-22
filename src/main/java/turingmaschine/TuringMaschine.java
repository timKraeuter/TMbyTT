package turingmaschine;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TuringMaschine {
	
	private final Zustand startZustand;
	private final Set<Zustand> zustaende;
	private final Set<Zustand> endZustaende;
	
	private final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;
	
	private TuringMaschine(final Zustand startZustand,
			final Set<Zustand> zustaende,
			final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
		this.startZustand = startZustand;
		this.zustaende = zustaende;
		this.endZustaende = endZustaende;
		this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
	}
	
	public static TuringMaschine create(final Zustand startZustand,
			final Set<Zustand> zustaende,
			final Set<Zustand> endZustaende,
			final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
		return new TuringMaschine(startZustand, zustaende, endZustaende, ueberfuehrungsfunktion);
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

	public Set<Zeichen> getArbeitsalphabet() {
        final Set<Zeichen> arbeitsalphabet = this.ueberfuehrungsfunktion.stream()
                .map(ElementDerUeberfuehrungsfunktion::getZuSchreibendesZeichen)
                .collect(Collectors.toSet());
        arbeitsalphabet.addAll(this.getEingabealphabet());
        arbeitsalphabet.add(Blank.getInstance());
        return arbeitsalphabet;
    }

	// TODO was bringt das Eingabealphabet und der Blank darf hier eigentlich nicht rein. siehe def. seite 4 und 5
    public Set<Zeichen> getEingabealphabet() {
	    return this.ueberfuehrungsfunktion.stream()
                .map(ElementDerUeberfuehrungsfunktion::getEingabe)
                .collect(Collectors.toSet());
    }
}
