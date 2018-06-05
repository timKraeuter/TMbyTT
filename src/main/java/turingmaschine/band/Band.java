package turingmaschine.band;

import turingmaschine.band.zeichen.Zeichen;

/**
 * Repräsentation des Bandes einer TM.
 */
public interface Band {
	
	/**
	 * @return Zeichen an der Position des SchreibLeseKopfes des Bandes.
	 */
	Zeichen getAktuellesZeichen();
	
	/**
	 * Verarbeitet gelesenesZeichen, schriebt zuSchreibendesZeichen und vollzieht lesekopfBewegung.
	 * 
	 * @param zuSchreibendesZeichen
	 *            Zeichen, das geschrieben wird
	 * @param lesekopfBewegung
	 *            , die vollzogen wird
	 * @param gelesenesZeichen
	 *            , Zeichen, das gelesen wird
	 * @return das Band nach der Verarbeitung
	 */
	Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen);
	
	/**
	 * Prüft, ob this den übergebenen bandInhalt enthält.
	 * 
	 * @param bandInhalt
	 *            für den geschaut wird, ob er auf dem Band steht.
	 * @return Ergebnis der Prüfung
	 */
	default boolean bandContains(final String bandInhalt) {
		return this.toString().contains(bandInhalt);
	}
	
}
