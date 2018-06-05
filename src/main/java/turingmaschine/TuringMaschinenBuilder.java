package turingmaschine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Klasse nach dem Builder-Pattern zum Erstellen von {@link TuringMaschine TuringMaschinen}.
 */
public class TuringMaschinenBuilder {
	
	private Zustand startZustand;
	private final Set<Zustand> endZustaende;
	private Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;
	private Integer anzahlDerBaender;
	
	private TuringMaschinenBuilder() {
		this.endZustaende = new HashSet<>();
	}
	
	public static TuringMaschinenBuilder create() {
		return new TuringMaschinenBuilder();
	}
	
	public TuringMaschine build() {
		if (Objects.isNull(this.startZustand)) {
			throw new RuntimeException("Bitte Startzustand der TM setzen.");
		}
		if (this.endZustaende.isEmpty()) {
			throw new RuntimeException("Bitte Endzustände der TM setzen.");
		}
		if (Objects.isNull(this.ueberfuehrungsfunktion)) {
			throw new RuntimeException("Bitte Überführungsfunktion der TM setzen.");
		}
		if (Objects.isNull(this.anzahlDerBaender)) {
			throw new RuntimeException("Bitte Anzahl der Bänder der TM setzen.");
		}
		return TuringMaschine.create(this.startZustand, this.endZustaende, this.ueberfuehrungsfunktion, this.anzahlDerBaender);
	}
	
	public TuringMaschinenBuilder startZustand(final Zustand startZustand) {
		this.startZustand = startZustand;
		return this;
	}
	
	public TuringMaschinenBuilder addEndZustand(final Zustand endZustaend) {
		this.endZustaende.add(endZustaend);
		return this;
	}
	
	public TuringMaschinenBuilder ueberfuehrungsfunktion(final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
		this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
		return this;
	}
	
	public TuringMaschinenBuilder anzahlDerBaender(final int anzahlDerBaender) {
		this.anzahlDerBaender = anzahlDerBaender;
		return this;
	}
	
}
