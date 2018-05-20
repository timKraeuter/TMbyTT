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
	
}
