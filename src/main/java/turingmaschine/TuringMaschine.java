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
        // TODO überlegen, ob man hier noch prüft, dass nur eine Konfiguration
        // herauskommt und falls nicht, ne exception?
        final Set<Konfiguration> konfigurationSet = this.simuliere(eingaben);
        return konfigurationSet.iterator().next();
    }

    public Konfiguration simuliereDeterministisch(final List<Band> baender) {
        final Konfiguration startKonfiguration = this.createStartKonfiguration(baender);
        return this.lasseMaschineLaufen(startKonfiguration).iterator().next();

    }

    private Set<Konfiguration> step(final Konfiguration konfiguration) {
        return this.ueberfuehrungsfunktion.stream().filter(e -> e.istPassendeUeberfuehrungZu(konfiguration))
                .map(konfiguration::doUeberfuehrung).collect(Collectors.toSet());
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
        final List<Zeichen> beliebigeZeichenFuerT1 = new ArrayList<>();
        IntStream.range(0, this.anzahlDerBaender).forEach(i -> beliebigeZeichenFuerT1.add(BeliebigesZeichen.getInstance()));

        // Überführungen der 1 Maschine übernehmen
        result.addAll(this.ueberfuehrungsfunktion.stream()
                .map(elementDerUeberfuehrungsfunktion -> this.erstelleUeberfuhrungMitBeliebigenZeichenHinten(elementDerUeberfuehrungsfunktion, beliebigeZeichenFuerT1))
                .collect(Collectors.toSet()));

        final List<Zeichen> beliebigeZeichenFuerT2 = new ArrayList<>();
        IntStream.range(0, t2.anzahlDerBaender).forEach(i -> beliebigeZeichenFuerT2.add(BeliebigesZeichen.getInstance()));

        // Überführungen der 2 Maschine übernehmen
        result.addAll(t2.ueberfuehrungsfunktion.stream()
                .map(elementDerUeberfuehrungsfunktion -> t2.erstelleUeberfuhrungMitBeliebigenZeichenVorne(elementDerUeberfuehrungsfunktion, beliebigeZeichenFuerT2))
                .collect(Collectors.toSet()));
        // Überführungen der 1 Maschine in die 2, wenn man bei der ersten in einem Endzustand ist.
        beliebigeZeichenFuerT1.addAll(beliebigeZeichenFuerT2);
        this.endZustaende.forEach(endzustandAusT1 -> result.add(ElementDerUeberfuehrungsfunktion.create(endzustandAusT1,
                t2.startZustand,
                beliebigeZeichenFuerT1,
                beliebigeZeichenFuerT1,
                beliebigeZeichenFuerT1.stream().map(beliebigesZeichen -> Lesekopfbewegung.N).collect(Collectors.toList()
                ))));

        return result;
    }

    private ElementDerUeberfuehrungsfunktion erstelleUeberfuhrungMitBeliebigenZeichenVorne(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion,
                                                                                           final List<Zeichen> beliebigeZeichenFuerT2) {
        final List<Zeichen> eingaben = elementDerUeberfuehrungsfunktion.getEingaben();
        eingaben.addAll(0, beliebigeZeichenFuerT2);

        final List<Zeichen> zuSchreibendeZeichen = elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen();
        zuSchreibendeZeichen.addAll(0, beliebigeZeichenFuerT2);

        final List<Lesekopfbewegung> lesekopfBewegungen = elementDerUeberfuehrungsfunktion.getLesekopfBewegungen();
        beliebigeZeichenFuerT2.forEach(beliebigesZeichen -> lesekopfBewegungen.add(0, Lesekopfbewegung.N));

        return ElementDerUeberfuehrungsfunktion.create(elementDerUeberfuehrungsfunktion.getVonZustand(),
                elementDerUeberfuehrungsfunktion.getZuZustand(),
                eingaben,
                zuSchreibendeZeichen,
                lesekopfBewegungen);
    }

    private ElementDerUeberfuehrungsfunktion erstelleUeberfuhrungMitBeliebigenZeichenHinten(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion,
                                                                                            final List<Zeichen> beliebigeZeichenFuerT1) {
        final List<Zeichen> eingaben = elementDerUeberfuehrungsfunktion.getEingaben();
        eingaben.addAll(beliebigeZeichenFuerT1);

        final List<Zeichen> zuSchreibendeZeichen = elementDerUeberfuehrungsfunktion.getZuSchreibendeZeichen();
        zuSchreibendeZeichen.addAll(beliebigeZeichenFuerT1);

        final List<Lesekopfbewegung> lesekopfBewegungen = elementDerUeberfuehrungsfunktion.getLesekopfBewegungen();
        beliebigeZeichenFuerT1.forEach(beliebigesZeichen -> lesekopfBewegungen.add(Lesekopfbewegung.N));

        return ElementDerUeberfuehrungsfunktion.create(elementDerUeberfuehrungsfunktion.getVonZustand(),
                elementDerUeberfuehrungsfunktion.getZuZustand(),
                eingaben,
                zuSchreibendeZeichen,
                lesekopfBewegungen);
    }

    boolean isEndzustand(final Zustand moeglicherEndzustand) {
        return this.endZustaende.contains(moeglicherEndzustand);
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
}
