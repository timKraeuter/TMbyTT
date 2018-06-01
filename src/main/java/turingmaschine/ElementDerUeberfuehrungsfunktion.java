package turingmaschine;

import com.google.common.base.Objects;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.util.Collections;
import java.util.List;

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
    }

    public static ElementDerUeberfuehrungsfunktion create(final Zustand vonZustand,
                                                          final Zustand zuZustand,
                                                          final List<Zeichen> eingaben,
                                                          final List<Zeichen> zuSchreibendeZeichen,
                                                          final List<Lesekopfbewegung> lesekopfBewegungen) {
        return new ElementDerUeberfuehrungsfunktion(vonZustand, zuZustand, eingaben, zuSchreibendeZeichen, lesekopfBewegungen);
    }

    boolean istPassendeUeberfuehrungZu(final Konfiguration konfiguration) {
        return this.vonZustand.equals(konfiguration.getZustand()) && this.passendeEingaben(konfiguration);
    }

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

    @Override
    public String toString() {
        return "(" + this.vonZustand + ")," + this.eingaben + this.lesekopfBewegungen +  "=" + this.zuZustand;
    }
}
