package turingmaschine;

public class Konfiguration {
	
	private final Zustand zustand;
	private final Band band;
	private final TuringMaschine tM;
	
	private Konfiguration(final Zustand zustand, final Band band, final TuringMaschine tM) {
		this.zustand = zustand;
		this.band = band;
		this.tM = tM;
	}
	
	public static Konfiguration create(final Zustand zustand, final Band band, final TuringMaschine tM) {
		return new Konfiguration(zustand, band, tM);
	}
	
	public boolean isEndKonfiguration() {
		return this.tM.isEndzustand(this.zustand);
	}
	
	public Zeichen getAktuellesZeichen() {
		return this.band.getAktuellesZeichen();
	}
	
	public Konfiguration doUeberfuehrung(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion) {
		return Konfiguration.create(
				elementDerUeberfuehrungsfunktion.getZuZustand(),
				this.band.verarbeite(
						elementDerUeberfuehrungsfunktion.getZuSchreibendesZeichen(),
						elementDerUeberfuehrungsfunktion.getLesekopfBewegung()),
				this.tM);
	}
	
}
