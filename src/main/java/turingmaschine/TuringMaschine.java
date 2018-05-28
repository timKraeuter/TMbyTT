package turingmaschine;

import com.google.common.base.Objects;
import turingmaschine.band.Band;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.util.Arrays;
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
        this.anzahlDerBaender = anzahlDerBaender;
        if (!this.parameterDerUeberfuhrungKorrekt(ueberfuehrungsfunktion)) {
            throw new RuntimeException(String.format("Bei einer Anzahl von %s Bändern, muss die Überführungsfunktion immer komplett definiert sein", this.anzahlDerBaender));
        }
        this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
    }

    private boolean parameterDerUeberfuhrungKorrekt(final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
        return ueberfuehrungsfunktion.stream()
                .allMatch(elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getLesekopfBewegungen().size() == this.anzahlDerBaender &&
                        elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen().size() == this.anzahlDerBaender &&
                        elementDerUeberfuehrungsfunktion.getEingaben().size() == this.anzahlDerBaender);

    }

    public static TuringMaschine create(final Zustand startZustand, final Set<Zustand> endZustaende,
                                        final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion,
                                        final int anzahlDerBaender) {
        return new TuringMaschine(startZustand, endZustaende, ueberfuehrungsfunktion, anzahlDerBaender);
    }
    
    public Set<Konfiguration> simuliere(final String... eingaben) {

        final List<Band> eingabeBaender = Arrays.stream(eingaben).map(Band::create).collect(Collectors.toList());
        if (eingabeBaender.size() != this.anzahlDerBaender) {
            throw new RuntimeException(String.format("Nur %s Eingabebänder erkannt, aber %s Eingabebänder sind gefordert!", eingabeBaender.size(), this.anzahlDerBaender));
        }

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
        return !this.simuliere(eingabe).isEmpty();
    }

    public void persistToFile(final String path) {
        throw new UnsupportedOperationException();
    }

    public void loadFromFile(final String path) {
        throw new UnsupportedOperationException();
    }

    boolean isEndzustand(final Zustand moeglicherEndzustand) {
        return this.endZustaende.contains(moeglicherEndzustand);
    }


    public TuringMaschine sequence(final TuringMaschine t2) {
        final TuringMaschinenBuilder builder = TuringMaschine.builder();
        builder.startZustand(this.startZustand); // Startzustand der 1 Maschine
        t2.endZustaende.forEach(builder::addEndZustand); // Endzustände  der 2 Maschine

        return builder.build();
    }

    public Set<Zeichen> getArbeitsalphabet() {
        final Set<Zeichen> arbeitsalphabet = this.ueberfuehrungsfunktion.stream()
                .flatMap(elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen().stream())
                .collect(Collectors.toSet());
        arbeitsalphabet.addAll(this.getEingabealphabet());
        arbeitsalphabet.add(Blank.getInstance());
        return arbeitsalphabet;
    }

    // TODO was bringt das Eingabealphabet und der Blank darf hier eigentlich nicht
    // rein. siehe def. seite 4 und 5
    public Set<Zeichen> getEingabealphabet() {
        return this.ueberfuehrungsfunktion.stream()
                .flatMap(elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getEingaben().stream())
                .collect(Collectors.toSet());
    }

    /**
     * @return alle Zustände des Automaten einschließlich Anfangs- und Endzustand.
     */
    public Set<Zustand> getZustaende() {
        final Set<Zustand> result = new HashSet<>(this.endZustaende);
        result.add(this.startZustand);
        for ( final ElementDerUeberfuehrungsfunktion e : this.ueberfuehrungsfunktion) {
            result.add(e.getVonZustand());
            result.add(e.getZuZustand());
        }
        return result;
    }

    public static TuringMaschinenBuilder builder() {
        return TuringMaschinenBuilder.create();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TuringMaschine)) {
            return false;
        }
        final TuringMaschine that = (TuringMaschine) o;
        return this.anzahlDerBaender == that.anzahlDerBaender &&
                Objects.equal(this.startZustand, that.startZustand) &&
                Objects.equal(this.endZustaende, that.endZustaende) &&
                Objects.equal(this.ueberfuehrungsfunktion, that.ueberfuehrungsfunktion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.startZustand, this.endZustaende, this.ueberfuehrungsfunktion, this.anzahlDerBaender);
    }
}
