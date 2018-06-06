package turingmaschine;

import turingmaschine.band.Band;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repräsentation der Konfiguration einer TM.
 */
public class Konfiguration {

    private final Zustand zustand;
    private final List<? extends Band> baender;
    private final TuringMaschine tM;
    private final Konfiguration vorKonfiguration;

    private Konfiguration(final Zustand zustand, final List<? extends Band> baender, final TuringMaschine tM, final Konfiguration vorKonfiguration) {
        this.zustand = zustand;
        this.baender = baender;
        this.tM = tM;
        this.vorKonfiguration = vorKonfiguration;
    }

    public static Konfiguration create(final Zustand zustand, final List<? extends Band> baender, final TuringMaschine tM) {
        return new Konfiguration(zustand, baender, tM, null);
    }

    public static Konfiguration create(final Zustand zustand, final List<? extends Band> baender, final TuringMaschine tM, final Konfiguration vorKonfiguration) {
        return new Konfiguration(zustand, baender, tM, vorKonfiguration);
    }

    /**
     * Vollzieht eine Überführung gemäß des übergebenen Elementes der Überführungsfunktion.
     *
     * @param elementDerUeberfuehrungsfunktion das vorgibt, wie die Überführung vollzogen wird
     * @return neue Konfiguration nach der Überführung
     */
    Konfiguration doUeberfuehrung(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion) {
        final List<Band> neueBaender = new ArrayList<>();
        final Zustand zuZustand = elementDerUeberfuehrungsfunktion.getZuZustand();
        for (int bandNummer = 0; bandNummer < this.baender.size(); bandNummer++) {
            neueBaender.add(
                    this.baender.get(bandNummer).verarbeite(
                            elementDerUeberfuehrungsfunktion.getZuSchreibendesZeichen(bandNummer),
                            elementDerUeberfuehrungsfunktion.getLesekopfBewegung(bandNummer),
                            this.baender.get(bandNummer).getAktuellesZeichen()));
        }
        return Konfiguration.create(zuZustand, neueBaender, this.tM, this);
    }

    boolean isEndKonfiguration() {
        return this.tM.isEndzustand(this.zustand);
    }

    public boolean bandContains(final String bandInhalt, final int nummerDesBandes) {
        return this.baender.get(nummerDesBandes - 1).bandContains(bandInhalt);
    }

    /**
     * Üblicher Getter für das Attribut zustand.
     *
     * @return liefert zustand.
     */
    Zustand getZustand() {
        return this.zustand;
    }

    Zeichen getAktuellesZeichen(final int bandNummer) {
        return this.baender.get(bandNummer).getAktuellesZeichen();
    }

    @Override
    public String toString() {
        return "zustand=" + this.zustand + ", baender=" + this.baender;
    }

    public void printKonfigurationsfolge() {
        if (Objects.nonNull(this.vorKonfiguration)) {
            this.vorKonfiguration.printKonfigurationsfolge();
        }
        this.printKonfigurationToConsole();
    }

    private void printKonfigurationToConsole() {
        final StringBuilder baenderString = new StringBuilder();
        for (int i = 0; i < this.baender.size(); i++) {
            baenderString.append("Band" + i + ": ");
            final Band b = this.baender.get(i);
            baenderString.append(b.toString());
            baenderString.append(System.lineSeparator());
        }
        final String s = "Zustand= " + this.zustand.toString() + ", " + System.lineSeparator()
                + "	Bänder= " + baenderString.toString();
        System.out.println(s);
    }
}
