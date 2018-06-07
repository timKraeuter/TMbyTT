package turingmaschine;

import turingmaschine.band.Band;
import turingmaschine.band.ImmutableBand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.BeliebigesZeichenOhneBlank;
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

/**
 * Repräsentation einer Turing-Maschine.
 */
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

    /**
     * Prüft, ob die Anzahl der Elemente in den Elementen der Überführungsfunktion zu der Anzahl der Bänder der TuringMaschine passt.
     *
     * @param ueberfuehrungsfunktion zu prüfende Überführungsfunktion
     * @return true, wenn prüfung positiv, false falls nicht
     */
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

    /**
     * Erstellt aus den übergebenen eingaben eine StartKonfiguration und lässt diese laufen.
     *
     * @param eingaben initialer Inhalt der Bänder der TM
     * @return End-Konfigurationen
     */
    public Set<Konfiguration> simuliere(final String... eingaben) {
        final Konfiguration startConfig = this.createStartKonfiguration(Arrays.stream(eingaben).map(ImmutableBand::create).collect(Collectors.toList()));
        return this.lasseMaschineLaufen(startConfig, false);
    }

    /**
     * Lässt die TuringMaschine mit der übergebenen Konfiguration auf Basis der Überführungsfunktion laufen.
     *
     * @param startConfig Konfiguration am Anfang
     * @param ausgabe true, wenn sofort eine Ausgabe der Konfiguration gemacht werden soll.
     * @return alle Konfigurationen, die in einen Endzustand geführt haben.
     */
    private Set<Konfiguration> lasseMaschineLaufen(final Konfiguration startConfig, final boolean ausgabe) {
        Set<Konfiguration> naechsteKonfigurationen = Collections.singleton(startConfig);

        final Set<Konfiguration> endKonfigurationen = new HashSet<>();
        while (!naechsteKonfigurationen.isEmpty()) {
            final HashSet<Konfiguration> neueNaechsteConfigs = new HashSet<>();

            for (final Konfiguration config : naechsteKonfigurationen) {
                if (ausgabe) {
                    config.printKonfigurationToConsole();
                }
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

    /**
     * Erstellt eine Start-Konfiguration zur TM mit den übergebenen Bändern.
     *
     * @param eingabeBaender der Startkonfiguration
     * @return die erzeugte Start-Konfiguration
     */
    private Konfiguration createStartKonfiguration(final List<? extends Band> eingabeBaender) {
        if (eingabeBaender.size() != this.anzahlDerBaender) {
            throw new RuntimeException(
                    String.format("Nur %s Eingabebänder erkannt, aber %s Eingabebänder sind gefordert!",
                            eingabeBaender.size(), this.anzahlDerBaender));
        }

        return Konfiguration.create(this.startZustand, eingabeBaender, this);
    }

    /**
     * Gibt genau eine Endkonfiguration des Ergebnisses von {@link #simuliere(String...) simuliere} zurück.
     *
     * @param eingaben initialer Inhalt der Bänder der TM
     * @return Endkonfiguration der TM bei der Eingabe eingaben
     */
    public Konfiguration simuliereDeterministisch(final String... eingaben) {
        final Set<Konfiguration> endKonfigurationen = this.simuliere(eingaben);
        this.checkIfDeterministisch(endKonfigurationen);
        return endKonfigurationen.iterator().next();
    }

    /**
     * Gibt genau eine Endkonfiguration des Ergebnisses von {@link #simuliere(String...) simuliere} zurück.
     *
     * @param baender initiale Bänder der TM
     * @return Endkonfiguration der TM bei der Eingabe baender
     */
    Konfiguration simuliereDeterministisch(final List<? extends Band> baender, final boolean ausgabe) {
        final Konfiguration startKonfiguration = this.createStartKonfiguration(baender);
        final Set<Konfiguration> endKonfigurationen = this.lasseMaschineLaufen(startKonfiguration, ausgabe);
        this.checkIfDeterministisch(endKonfigurationen);
        return endKonfigurationen.iterator().next();

    }

    private void checkIfDeterministisch(final Set<Konfiguration> endKonfigurationen) {
        if (endKonfigurationen.isEmpty() || endKonfigurationen.size() > 1) {
            throw new RuntimeException(
                    String.format("Die TM hatte folgende %s Endkonfigurationen. Das Eingabewort wurde nicht deterministisch erkannt.",
                            endKonfigurationen));
        }
    }

    /**
     * Macht einen Schritt auf Basis der übergebenen Konfiguration und der Überführungsfunktion der TM.
     *
     * @param konfiguration von der aus ein Schritt gemacht wird
     * @return Konfigurationen nach dem Schritt
     */
    private Set<Konfiguration> step(final Konfiguration konfiguration) {
        // TODO irgendwie muss das hier getrennt sein.
        final List<ElementDerUeberfuehrungsfunktion> ueberfuhrungen = this.ueberfuehrungsfunktion.stream()
                .filter(e -> e.istPassendeUeberfuehrungZu(konfiguration)).collect(Collectors.toList());

        return ueberfuhrungen.stream().map(konfiguration::doUeberfuehrung).collect(Collectors.toSet());
    }

    /**
     * Prüft, ob die TM die eingabe erkennt.
     *
     * @param eingabe für die geprüft wird, ob die TM sie erkennt.
     * @return true, falls die TM die eingabe erkennt, false sonst
     */
    public boolean erkenntEingabe(final String eingabe) {
        return !this.simuliere(eingabe).isEmpty();
    }

    /**
     * Komponiert bzw. Sequenziert zwei Turing-Maschinen.
     *
     * @param t2 TuringMaschine, die hinter this geschaltet wird
     * @return Turing-Maschine, die aus der Sequanzierung entsteht
     */
    TuringMaschine sequence(final TuringMaschine t2) {
        final TuringMaschinenBuilder builder = TuringMaschine.builder();
        builder.startZustand(this.startZustand); // Startzustand der 1 Maschine
        t2.endZustaende.forEach(builder::addEndZustand); // Endzustände der 2 Maschine
        builder.anzahlDerBaender(this.anzahlDerBaender + t2.anzahlDerBaender);

        builder.ueberfuehrungsfunktion(this.berechneSequenzUeberfuehrung(t2));

        return builder.build();
    }

    /**
     * Berechnet die Überführung bei Sequenzierung zweier Turing-Maschinen.
     *
     * @param t2 TuringMaschine, die hinter this geschaltet wird
     * @return Kombinierte Überführungsfunnktion von this und t2
     */
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

        this.endZustaende.forEach(endzustandAusT1 -> result.add(ElementDerUeberfuehrungsfunktion.create(
                endzustandAusT1,
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

    public Set<ElementDerUeberfuehrungsfunktion> getUeberfuehrungsfunktion() {
        return Collections.unmodifiableSet(this.ueberfuehrungsfunktion);
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

    public Set<Zeichen> getEingabealphabet() {
        final Set<Zeichen> eingabenMitBlank = this.ueberfuehrungsfunktion.stream()
                .flatMap(elementDerUeberfuehrungsfunktion -> elementDerUeberfuehrungsfunktion.getEingaben().stream())
                .collect(Collectors.toSet());
        eingabenMitBlank.remove(Blank.getInstance());
        eingabenMitBlank.remove(BeliebigesZeichen.getInstance());
        eingabenMitBlank.remove(BeliebigesZeichenOhneBlank.getInstance());
        return eingabenMitBlank;
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

    Set<Zustand> getEndZustaende() {
        return Collections.unmodifiableSet(this.endZustaende);
    }

    public int getAnzahlDerBaender() {
        return this.anzahlDerBaender;
    }

    public String toXML() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<machine>");
        builder.append("<start>").append(this.startZustand.toXML()).append("</start>");
        builder.append("<end>");
        this.endZustaende.forEach(e -> builder.append(e.toXML()));
        builder.append("</end>");
        builder.append("<delta>");
        this.ueberfuehrungsfunktion.forEach(t -> builder.append(t.toXML()));
        builder.append("</delta>");
        builder.append("</machine>");
        return builder.toString();
    }
}
