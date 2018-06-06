package turingmaschine.band.zeichen;

/**
 * Repräsentation eines Zeichens im Kontext von Turing-Maschinen.
 */
public interface Zeichen {
	
	char BLANK = '_';
	char BELIEBIGES_ZEICHEN = '*';
	char BELIEBIGES_ZEICHEN_OHNE_BLANK = '~';
	
	static Zeichen create(final char eingabeZeichen) {
		if (eingabeZeichen == Zeichen.BLANK) {
			return Blank.getInstance();
		}
		if (eingabeZeichen == Zeichen.BELIEBIGES_ZEICHEN) {
			return BeliebigesZeichen.getInstance();
		}
		if (eingabeZeichen == Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK) {
			return BeliebigesZeichenOhneBlank.getInstance();
		}
		return NormalesZeichen.create(eingabeZeichen);
	}
	
	/**
	 * @return Buchstabe, der durch das Zeichen gekapselt wird.
	 */
	Character getZeichen();
	
	/**
	 * Prüft auf Gleichheit der Zeichen.
	 * 
	 * @param zeichen
	 *            mit dem this verglichen wird
	 * @return Ergebnis der Prüfung
	 */
	boolean matches(Zeichen zeichen);
	
	/**
	 * Typische accept-Operation des Visitor-Patterns.
	 * 
	 * @param visitor
	 *            zum Visitieren von Zeichen
	 * @return Rückgabe vom Typ des TypeParameters des Visitors
	 */
	<T> T accept (ZeichenVisitor<T> visitor);

    String toXML();
}
