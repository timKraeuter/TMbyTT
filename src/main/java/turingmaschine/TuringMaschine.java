package turingmaschine;

import com.google.common.base.Objects;
import turingmaschine.band.Band;
import turingmaschine.band.ImmutableBand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TuringMaschine {

    private final Zustand startZustand;
    private final Set<Zustand> endZustaende;

    private final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion;

    private final int anzahlDerBaender;

    private TuringMaschine(final Zustand startZustand, final Set<Zustand> endZustaende,
                           final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion, final int anzahlDerBaender) {
        this.startZustand = startZustand;
        this.endZustaende = endZustaende;
        this.anzahlDerBaender = anzahlDerBaender;
        if (!this.parameterDerUeberfuhrungKorrekt(ueberfuehrungsfunktion)) {
            throw new RuntimeException(String.format(
                    "Bei einer Anzahl von %s Bändern, muss die Überführungsfunktion immer komplett definiert sein",
                    this.anzahlDerBaender));
        }
        this.ueberfuehrungsfunktion = ueberfuehrungsfunktion;
    }

    private boolean parameterDerUeberfuhrungKorrekt(
            final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion) {
        return ueberfuehrungsfunktion.stream()
                .allMatch(elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getLesekopfBewegungen()
                        .size() == this.anzahlDerBaender
                        && elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen().size() == this.anzahlDerBaender
                        && elementDerUeberfuehrungsfunktion.getEingaben().size() == this.anzahlDerBaender);

    }

    public static TuringMaschine create(final Zustand startZustand, final Set<Zustand> endZustaende,
                                        final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion, final int anzahlDerBaender) {
        return new TuringMaschine(startZustand, endZustaende, ueberfuehrungsfunktion, anzahlDerBaender);
    }

    public Set<Konfiguration> simuliere(final String... eingaben) {
        final Konfiguration startConfig = this.createStartKonfiguration(Arrays.stream(eingaben).map(ImmutableBand::create).collect(Collectors.toList()));
        return this.lasseMaschineLaufen(startConfig);
    }

    public Set<Konfiguration> lasseMaschineLaufen(final Konfiguration startConfig) {
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

    public Konfiguration createStartKonfiguration(final List<Band> eingabeBaender) {
        if (eingabeBaender.size() != this.anzahlDerBaender) {
            throw new RuntimeException(
                    String.format("Nur %s Eingabebänder erkannt, aber %s Eingabebänder sind gefordert!",
                            eingabeBaender.size(), this.anzahlDerBaender));
        }

        final Konfiguration startConfig = Konfiguration.create(this.startZustand, eingabeBaender, this);
        return startConfig;
    }

    public Konfiguration simuliereDeterministisch(final String... eingaben) {
        final Set<Konfiguration> endKonfigurationen = this.simuliere(eingaben);
        this.checkIfDeterministisch(endKonfigurationen);
        return endKonfigurationen.iterator().next();
    }

    public Konfiguration simuliereDeterministisch(final List<Band> baender) {
        final Konfiguration startKonfiguration = this.createStartKonfiguration(baender);
        final Set<Konfiguration> endKonfigurationen = this.lasseMaschineLaufen(startKonfiguration);
        this.checkIfDeterministisch(endKonfigurationen);
        return endKonfigurationen.iterator().next();

    }

    private void checkIfDeterministisch(final Set<Konfiguration> endKonfigurationen) {
        if (endKonfigurationen.isEmpty() || endKonfigurationen.size() > 1) {
            throw new RuntimeException(
                    String.format("Die TM hatte folgende %s Endkonfigurationen. Dies entspricht nicht dem Verhalten einer deterministischen Maschine",
                    endKonfigurationen));
        }
    }

    private Set<Konfiguration> step(final Konfiguration konfiguration) {
        // TODO orgendwie muss das hier getrennt sein.
        final List<ElementDerUeberfuehrungsfunktion> ueberfuhrungen = this.ueberfuehrungsfunktion.stream().filter(e -> e.istPassendeUeberfuehrungZu(konfiguration)).collect(Collectors.toList());
        return ueberfuhrungen.stream().map(konfiguration::doUeberfuehrung).collect(Collectors.toSet());
    }

    public boolean erkenntEingabe(final String eingabe) {
        return !this.simuliere(eingabe).isEmpty();
    }

    public TuringMaschine sequence(final TuringMaschine t2) {
        final TuringMaschinenBuilder builder = TuringMaschine.builder();
        builder.startZustand(this.startZustand); // Startzustand der 1 Maschine
        t2.endZustaende.forEach(builder::addEndZustand); // Endzustände der 2 Maschine
        builder.anzahlDerBaender(this.anzahlDerBaender + t2.anzahlDerBaender);

        builder.ueberfuehrungsfunktion(this.berechneSequenzUeberfuehrung(t2));

        return builder.build();
    }

    private Set<ElementDerUeberfuehrungsfunktion> berechneSequenzUeberfuehrung(final TuringMaschine t2) {
        // Change Names for Debugging
        this.getZustaende().forEach(zustand -> zustand.addToName("1"));
        t2.getZustaende().forEach(zustand -> zustand.addToName("2"));


        final Set<ElementDerUeberfuehrungsfunktion> result = new HashSet<>();

        // Überführungen der 1 Maschine übernehmen
        result.addAll(this.ueberfuehrungsfunktion.stream()
                .map(elementDerUeberfuehrungsfunktion -> TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(elementDerUeberfuehrungsfunktion, t2.anzahlDerBaender, false))
                .collect(Collectors.toSet()));


        // Überführungen der 2 Maschine übernehmen
        result.addAll(t2.ueberfuehrungsfunktion.stream()
                .map(elementDerUeberfuehrungsfunktion -> TuringMaschine.erstelleUeberfuhrungMitBeliebigenZeichen(elementDerUeberfuehrungsfunktion, this.anzahlDerBaender, true))
                .collect(Collectors.toSet()));

        final List<Zeichen> beliebigeZeichen = new ArrayList<>();
        IntStream.range(0, this.anzahlDerBaender + t2.anzahlDerBaender).forEach(i -> beliebigeZeichen.add(BeliebigesZeichen.getInstance()));
        // Überführungen der 1 Maschine in die 2, wenn man bei der ersten in einem Endzustand ist.

        this.endZustaende.forEach(endzustandAusT1 -> result.add(ElementDerUeberfuehrungsfunktion.create(endzustandAusT1,
                t2.startZustand,
                beliebigeZeichen,
                beliebigeZeichen,
                beliebigeZeichen.stream().map(beliebigesZeichen -> Lesekopfbewegung.N).collect(Collectors.toList()
                ))));

        return result;
    }

    /**
     * @param elementDerUeberfuehrungsfunktion Überführungsfunktion, welche erweitert werden soll.
     * @param anzahlBeliebigeZeichen           Anzahl der beliebigen Zeichen, die Vorne oder Hinten eingefügt werden sollen.
     * @param vorneEinfuegen                   true, wenn die beliebigenZeichenFuerT2 vorne in die Überführungsfunktion eingefügt werden, sonst hinten.
     * @return Überführungsfunktion entsprechend der Eingabe, jedoch erweitert um beliebigenZeichenFuerT2 als Eingabe und neutrale Schreiblesekopfbewegungen hinten oder vorne.
     */
    static ElementDerUeberfuehrungsfunktion erstelleUeberfuhrungMitBeliebigenZeichen(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion,
                                                                                      final int anzahlBeliebigeZeichen,
                                                                                      final boolean vorneEinfuegen) {

        final List<Zeichen> beliebigeZeichen = new ArrayList<>();
        IntStream.range(0, anzahlBeliebigeZeichen).forEach(i -> beliebigeZeichen.add(BeliebigesZeichen.getInstance()));

        final List<Zeichen> neueEingaben = new ArrayList<>(elementDerUeberfuehrungsfunktion.getEingaben());
        final List<Zeichen> neueZuSchreibendeZeichen = new ArrayList<>(elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen());
        final List<Lesekopfbewegung> neueLesekopfBewegungen = new ArrayList<>(elementDerUeberfuehrungsfunktion.getLesekopfBewegungen());
        if (vorneEinfuegen) {
            neueEingaben.addAll(0, beliebigeZeichen);
            neueZuSchreibendeZeichen.addAll(0, beliebigeZeichen);
            beliebigeZeichen.forEach(beliebigesZeichen -> neueLesekopfBewegungen.add(0, Lesekopfbewegung.N));
        } else {
            neueEingaben.addAll(beliebigeZeichen);
            neueZuSchreibendeZeichen.addAll(beliebigeZeichen);
            beliebigeZeichen.forEach(beliebigesZeichen -> neueLesekopfBewegungen.add(Lesekopfbewegung.N));
        }


        return ElementDerUeberfuehrungsfunktion.create(elementDerUeberfuehrungsfunktion.getVonZustand(),
                elementDerUeberfuehrungsfunktion.getZuZustand(),
                neueEingaben,
                neueZuSchreibendeZeichen,
                neueLesekopfBewegungen);
    }

    boolean isEndzustand(final Zustand moeglicherEndzustand) {
        return this.endZustaende.contains(moeglicherEndzustand);
    }

    public Zustand getStartZustand() {
        return this.startZustand;
    }

    public Set<Zeichen> getArbeitsalphabet() {
        final Set<Zeichen> arbeitsalphabet = this.ueberfuehrungsfunktion.stream().flatMap(
                elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen().stream())
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
        for (final ElementDerUeberfuehrungsfunktion e : this.ueberfuehrungsfunktion) {
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
        return this.anzahlDerBaender == that.anzahlDerBaender && Objects.equal(this.startZustand, that.startZustand)
                && Objects.equal(this.endZustaende, that.endZustaende)
                && Objects.equal(this.ueberfuehrungsfunktion, that.ueberfuehrungsfunktion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.startZustand, this.endZustaende, this.ueberfuehrungsfunktion,
                this.anzahlDerBaender);
    }

    public Set<Zustand> getEndZustaende() {
        return Collections.unmodifiableSet(this.endZustaende);
    }
}
