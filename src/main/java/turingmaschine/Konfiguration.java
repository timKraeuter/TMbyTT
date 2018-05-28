package turingmaschine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import turingmaschine.band.Band;
import turingmaschine.band.zeichen.Zeichen;

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
							elementDerUeberfuehrungsfunktion.getLesekopfBewegung(bandNummer),
                            this.baender.get(bandNummer).getAktuellesZeichen()));
		}
		return Konfiguration.create(zuZustand, neueBaender, this.tM);
	}

	public Set<Konfiguration> laufeBisEnde() {
		return this.tM.lasseMaschineLaufen(this);
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
		return this.baender.get(nummerDesBandes - 1).bandContains(bandInhalt);
	}

	@Override
	public String toString() {
		return "zustand=" + this.zustand + ", baender=" + this.baender;
	}

	public List<Band> getBaender() {
		return baender;
	}

	public Band getLetztesBand() {
		return baender.get(baender.size() - 1);
	}

	public List<String> getBaenderAsStrings() {
		return this.baender.stream().map(Band::toString).map(String::trim).collect(Collectors.toList());
	}
}
