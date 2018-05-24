package turingmaschine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TuringMaschinenBuilder {

    private Zustand startZustand;
    private Set<Zustand> endZustaende;
    private Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;

    private TuringMaschinenBuilder() {
        this.endZustaende = new HashSet<>();
    }

    public static TuringMaschinenBuilder create() {
        return new TuringMaschinenBuilder();
    }

    public TuringMaschine build() {
        if (Objects.isNull(startZustand)) {
           throw new RuntimeException("Bitte Startzustand der TM setzen.");
        }
        if (endZustaende.isEmpty()) {
           throw new RuntimeException("Bitte Endzustände der TM setzen.");
        }
        if (Objects.isNull(ueberfuehrungsfunktion)) {
           throw new RuntimeException("Bitte Überführungsfunktion der TM setzen.");
        }
        return TuringMaschine.create(startZustand, endZustaende, ueberfuehrungsfunktion);
    }

    public void startZustand(final Zustand startZustand) {
        this.startZustand = startZustand;
    }

    public void addEndZustand(final Zustand endZustaend) {
        this.endZustaende.add(endZustaend);
    }

    public void ueberfuehrungsfunktion(final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
        this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
    }

}
