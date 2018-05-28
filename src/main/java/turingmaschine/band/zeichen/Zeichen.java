package turingmaschine.band.zeichen;

public interface Zeichen {

    char BLANK = '_';
    char BELIEBIGES_ZEICHEN = '*';

    static Zeichen create(final char eingabeZeichen) {
        if (eingabeZeichen == Zeichen.BLANK) {
            return Blank.getInstance();
        }
        return NormalesZeichen.create(eingabeZeichen);
    }

    Character getZeichen();

    <T> T accept (ZeichenVisitor<T> visitor);

    boolean matches(Zeichen zeichen);
	
}
