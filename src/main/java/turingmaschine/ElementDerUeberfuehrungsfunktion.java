package turingmaschine;

import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.util.List;
import java.util.Objects;

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
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final ElementDerUeberfuehrungsfunktion that = (ElementDerUeberfuehrungsfunktion) o;
		return Objects.equals(this.vonZustand, that.vonZustand) &&
				Objects.equals(this.zuZustand, that.zuZustand) &&
				Objects.equals(this.eingaben, that.eingaben) &&
				Objects.equals(this.zuSchreibendeZeichen, that.zuSchreibendeZeichen)
				&& Objects.equals(this.lesekopfBewegungen, that.lesekopfBewegungen);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.vonZustand, this.zuZustand, this.eingaben, this.zuSchreibendeZeichen, this.lesekopfBewegungen);
	}
	
	public boolean istPassendeUeberfuehrungZu(final Konfiguration konfiguration) {
		return this.vonZustand.equals(konfiguration.getZustand()) && this.passendeEingaben(konfiguration);
	}
	
	// TODO evtl. schöner machen irgendwie
	private boolean passendeEingaben(final Konfiguration konfiguration) {
		for (int i = 0; i < this.eingaben.size(); i++) {
			if (!this.eingaben.get(i).equals(konfiguration.getAktuellesZeichen(i))) {
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
	 * @return liefert zuZustand.
	 */
	public Zustand getZuZustand() {
		return this.zuZustand;
	}
	
	/**
	 * Üblicher Getter für das Attribut eingaben.
	 * @return liefert eingaben.
	 */
	public List<Zeichen> getEingaben() {
		return this.eingaben;
	}
	
	/**
	 * Üblicher Getter für das Attribut zuSchreibendeZeichen.
	 * @return liefert zuSchreibendeZeichen.
	 */
	public List<Zeichen> getZuSchreibendeZeichen() {
		return this.zuSchreibendeZeichen;
	}
	
	/**
	 * Üblicher Getter für das Attribut lesekopfBewegungen.
	 * @return liefert lesekopfBewegungen.
	 */
	public List<Lesekopfbewegung> getLesekopfBewegungen() {
		return this.lesekopfBewegungen;
	}
	
	public Zeichen getZuSchreibendesZeichen(final int bandNummer) {
		return this.zuSchreibendeZeichen.get(bandNummer);
	}
	
	public Lesekopfbewegung getLesekopfBewegung(final int bandNummer) {
		return this.lesekopfBewegungen.get(bandNummer);
	}
	
	
}
