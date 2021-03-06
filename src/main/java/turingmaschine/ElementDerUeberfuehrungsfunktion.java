package turingmaschine;

import com.google.common.base.Objects;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Repräsentation eines Elementes der Überführung einer TM.
 */
public class ElementDerUeberfuehrungsfunktion {

    private final Zustand vonZustand;
    private final Zustand zuZustand;

    private final List<Zeichen> eingaben;
    private final List<Zeichen> zuSchreibendeZeichen;
    private final List<Lesekopfbewegung> lesekopfBewegungen;

    private ElementDerUeberfuehrungsfunktion(final Zustand vonZustand,
                                             final Zustand zuZustand,
                                             final List<Zeichen> eingaben,
                                             final List<Zeichen> zuSchreibendesZeichen,
                                             final List<Lesekopfbewegung> lesekopfbewegungen) {
        this.vonZustand = vonZustand;
        this.zuZustand = zuZustand;
        this.eingaben = eingaben;
        this.zuSchreibendeZeichen = zuSchreibendesZeichen;
        this.lesekopfBewegungen = lesekopfbewegungen;
        this.checkKonsistenz();
    }

    private void checkKonsistenz() {
        if (!(this.eingaben.size() == this.zuSchreibendeZeichen.size() && this.zuSchreibendeZeichen.size() == this.lesekopfBewegungen.size())) {
            throw new RuntimeException("Überführungsfunktion muss gleich viele Zeichen lesen, schreiben und SchreibeLeseKopfbewegungen haben");
        }
    }

    public static ElementDerUeberfuehrungsfunktion create(final Zustand vonZustand,
                                                          final Zustand zuZustand,
                                                          final List<Zeichen> eingaben,
                                                          final List<Zeichen> zuSchreibendeZeichen,
                                                          final List<Lesekopfbewegung> lesekopfBewegungen) {
        return new ElementDerUeberfuehrungsfunktion(vonZustand, zuZustand, eingaben, zuSchreibendeZeichen, lesekopfBewegungen);
    }

    /**
     * @param konfiguration für die zu prüfen ist, ob sie zu this passt.
     * @return true, falls Zustand und Bänder der Konfiguration zu this passen, sonst false
     */
    boolean istPassendeUeberfuehrungZu(final Konfiguration konfiguration) {
        return this.vonZustand.equals(konfiguration.getZustand()) && this.passendeEingaben(konfiguration);
    }

    /**
     * Prüft, ob die Zeichen der Bänder der übergebenen Konfiguration an der Position der Schreiblesekopfe zu den eingaben von this passen.
     *
     * @param konfiguration für die zu prüfen ist, ob sie (bis auf ihren Zustand) zu this passt
     * @return true falls ja, sonst false
     */
    private boolean passendeEingaben(final Konfiguration konfiguration) {
        for (int i = 0; i < this.eingaben.size(); i++) {
            if (!this.eingaben.get(i).matches(konfiguration.getAktuellesZeichen(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Üblicher Getter für das Attribut vonZustand.
     *
     * @return liefert vonZustand.
     */
    public Zustand getVonZustand() {
        return this.vonZustand;
    }

    /**
     * Üblicher Getter für das Attribut zuZustand.
     *
     * @return liefert zuZustand.
     */
    public Zustand getZuZustand() {
        return this.zuZustand;
    }

    /**
     * Read-only Getter für das Attribut eingaben.
     *
     * @return liefert eingaben.
     */
    public List<Zeichen> getEingaben() {
        return Collections.unmodifiableList(this.eingaben);
    }

    /**
     * Read-only Getter für das Attribut zuSchreibendeZeichen.
     *
     * @return liefert zuSchreibendeZeichen.
     */
    public List<Zeichen> getZuSchreibendeZeichen() {
        return Collections.unmodifiableList(this.zuSchreibendeZeichen);
    }

    /**
     * Read-only Getter für das Attribut lesekopfBewegungen.
     *
     * @return liefert lesekopfBewegungen.
     */
    List<Lesekopfbewegung> getLesekopfBewegungen() {
        return Collections.unmodifiableList(this.lesekopfBewegungen);
    }

    Zeichen getZuSchreibendesZeichen(final int bandNummer) {
        return this.zuSchreibendeZeichen.get(bandNummer);
    }

    Lesekopfbewegung getLesekopfBewegung(final int bandNummer) {
        return this.lesekopfBewegungen.get(bandNummer);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementDerUeberfuehrungsfunktion)) {
            return false;
        }
        final ElementDerUeberfuehrungsfunktion that = (ElementDerUeberfuehrungsfunktion) o;
        return Objects.equal(this.vonZustand, that.vonZustand) &&
                Objects.equal(this.zuZustand, that.zuZustand) &&
                Objects.equal(this.eingaben, that.eingaben) &&
                Objects.equal(this.zuSchreibendeZeichen, that.zuSchreibendeZeichen) &&
                Objects.equal(this.lesekopfBewegungen, that.lesekopfBewegungen);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.vonZustand, this.zuZustand, this.eingaben, this.zuSchreibendeZeichen, this.lesekopfBewegungen);
    }

    public String toXML() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<transition>");
        builder.append("<from>").append(this.vonZustand.toString()).append("</from>");
        builder.append("<inputs>");
        this.zuSchreibendeZeichen.forEach(z -> {
            builder.append("<input>");
            builder.append(z.toXML());
            builder.append("</input>");
        });
        builder.append("</inputs>");
        builder.append("<to>").append(this.zuZustand.toXML()).append("/to");

        builder.append("<tasks>");
        final Iterator<Zeichen> zI = this.zuSchreibendeZeichen.iterator();
        final Iterator<Lesekopfbewegung> bI = this.lesekopfBewegungen.iterator();

        while (zI.hasNext() && bI.hasNext()) {
            builder.append("<task>");
            builder.append("<write>").append(zI.next().toXML()).append("</write>");
            builder.append("<move>").append(bI.next().toString()).append("</move>");
            builder.append("</task>");
        }
        builder.append("</tasks");

        builder.append("</transition>");
        return builder.toString();
    }
}
