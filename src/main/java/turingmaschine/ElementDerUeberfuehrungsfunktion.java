package turingmaschine;

import java.util.Objects;

public class ElementDerUeberfuehrungsfunktion {
	
	private final Zustand vonZustand;
	private final Zustand zuZustand;
	
	private final Zeichen eingabe;
	private final Zeichen zuSchreibendesZeichen;
	private final Lesekopfbewegung lesekopfBewegung;
	
	private ElementDerUeberfuehrungsfunktion(final Zustand vonZustand,
			final Zustand zuZustand,
			final Zeichen eingabe,
			final Zeichen zuSchreibendesZeichen,
			final Lesekopfbewegung lesekopfbewegung) {
		this.vonZustand = vonZustand;
		this.zuZustand = zuZustand;
		this.eingabe = eingabe;
		this.zuSchreibendesZeichen = zuSchreibendesZeichen;
		this.lesekopfBewegung = lesekopfbewegung;
	}
	
	public static ElementDerUeberfuehrungsfunktion create(final Zustand vonZustand,
			final Zustand zuZustand,
			final Zeichen eingabe,
			final Zeichen zuSchreibendesZeichen,
			final Lesekopfbewegung lesekopfBewegung) {
		return new ElementDerUeberfuehrungsfunktion(vonZustand, zuZustand, eingabe, zuSchreibendesZeichen, lesekopfBewegung);
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
				Objects.equals(this.eingabe, that.eingabe) &&
				Objects.equals(this.zuSchreibendesZeichen, that.zuSchreibendesZeichen) &&
				this.lesekopfBewegung == that.lesekopfBewegung;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.vonZustand, this.zuZustand, this.eingabe, this.zuSchreibendesZeichen, this.lesekopfBewegung);
	}
	
	public boolean istPassendeUeberfuehrungZu(final Konfiguration konfiguration) {
		return this.eingabe.equals(konfiguration.getAktuellesZeichen());
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
	 * Üblicher Getter für das Attribut vonZustand.
	 * 
	 * @return liefert vonZustand.
	 */
	public Zustand getVonZustand() {
		return this.vonZustand;
	}
	
	/**
	 * Üblicher Getter für das Attribut eingabe.
	 * 
	 * @return liefert eingabe.
	 */
	public Zeichen getEingabe() {
		return this.eingabe;
	}
	
	/**
	 * Üblicher Getter für das Attribut zuSchreibendesZeichen.
	 * 
	 * @return liefert zuSchreibendesZeichen.
	 */
	public Zeichen getZuSchreibendesZeichen() {
		return this.zuSchreibendesZeichen;
	}
	
	/**
	 * Üblicher Getter für das Attribut lesekopfBewegung.
	 * 
	 * @return liefert lesekopfBewegung.
	 */
	public Lesekopfbewegung getLesekopfBewegung() {
		return this.lesekopfBewegung;
	}
	
}
