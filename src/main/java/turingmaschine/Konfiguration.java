package turingmaschine;

import turingmaschine.band.Band;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.List;

public class Konfiguration {
	
	private final Zustand zustand;
	private final List<Band> baender;
	private final TuringMaschine tM;
	
	private Konfiguration(final Zustand zustand, final List<Band> baender, final TuringMaschine tM) {
		this.zustand = zustand;
		this.baender = baender;
		this.tM = tM;
	}
	
	public static Konfiguration create(final Zustand zustand, final List<Band> baender, final TuringMaschine tM) {
		return new Konfiguration(zustand, baender, tM);
	}
	
	boolean isEndKonfiguration() {
		return this.tM.isEndzustand(this.zustand);
	}
	
	Zeichen getAktuellesZeichen(final int bandNummer) {
		return this.baender.get(bandNummer).getAktuellesZeichen();
	}
	
	Konfiguration doUeberfuehrung(final ElementDerUeberfuehrungsfunktion elementDerUeberfuehrungsfunktion) {
		final List<Band> neueBaender = new ArrayList<>();
		final Zustand zuZustand = elementDerUeberfuehrungsfunktion.getZuZustand();
		for (int bandNummer = 0; bandNummer < this.baender.size(); bandNummer++) {
			neueBaender.add(
					this.baender.get(bandNummer).verarbeite(
							elementDerUeberfuehrungsfunktion.getZuSchreibendesZeichen(bandNummer),
							elementDerUeberfuehrungsfunktion.getLesekopfBewegung(bandNummer)));
		}
		return Konfiguration.create(zuZustand, neueBaender, this.tM);
	}
	
	/**
	 * Üblicher Getter für das Attribut zustand.
	 * 
	 * @return liefert zustand.
	 */
	 Zustand getZustand() {
		return this.zustand;
	}
	
	public boolean bandContains(final String bandInhalt, final int nummerDesBandes) {
		return this.baender.get(nummerDesBandes).bandContains(bandInhalt);
	}

    @Override
    public String toString() {
        return "zustand=" + zustand +
                ", baender=" + baender;
    }
}
