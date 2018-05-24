package turingmaschine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TuringMaschine {

    private final Zustand startZustand;
    private final Set<Zustand> endZustaende;

    private final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;

    private TuringMaschine(final Zustand startZustand,
                           final Set<Zustand> endZustaende,
                           final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
        this.startZustand = startZustand;
        this.endZustaende = endZustaende;
        this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
    }

    public static TuringMaschine create(final Zustand startZustand,
                                        final Set<Zustand> endZustaende,
                                        final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
        return new TuringMaschine(startZustand, endZustaende, ueberfuehrungsfunktion);
    }

    // Wir gehen erstmal von deterministisch aus
    // Hier wird wild ein Band erzeugt und so ein Gedöns.
    public Set<Konfiguration> simuliere(final String eingabe) {
        final Band eingabeBand = Band.create(eingabe);
        Konfiguration startConfig = Konfiguration.create(this.startZustand, eingabeBand, this);
        Set<Konfiguration> naechsteKonfigurationen = Collections.singleton(startConfig);

        final Set<Konfiguration> endKonfigurationen = new HashSet<>();
        while (!naechsteKonfigurationen.isEmpty()) {
            final HashSet<Konfiguration> neueNaechsteConfigs = new HashSet<>();

            for (Konfiguration config : naechsteKonfigurationen) {
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
        return this.ueberfuehrungsfunktion.stream()
                .filter(e -> e.istPassendeUeberfuehrungZu(konfiguration))
                .map(konfiguration::doUeberfuehrung)
                .collect(Collectors.toSet());
    }

    public boolean erkenntEingabe(final String eingabe) {
        return !this.simuliere(eingabe).isEmpty();
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

    /**
     * @return alle Zustände des Automaten einschließlich Anfangs- und Endzustand.
     */
    public Set<Zustand> getZustaende() {
        throw new UnsupportedOperationException();
    }

    public static TuringMaschinenBuilder builder() {
        return TuringMaschinenBuilder.create();
    }
}
