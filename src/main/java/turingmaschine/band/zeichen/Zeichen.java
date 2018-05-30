package turingmaschine.band.zeichen;

import java.util.List;

public interface Zeichen {

    char BLANK = '_';
    char BELIEBIGES_ZEICHEN = '*';

    static Zeichen create(final char eingabeZeichen) {
        if (eingabeZeichen == Zeichen.BLANK) {
            return Blank.getInstance();
        }
        if (eingabeZeichen == Zeichen.BELIEBIGES_ZEICHEN) {
            return BeliebigesZeichen.getInstance();
        }
        return NormalesZeichen.create(eingabeZeichen);
    }

    Character getZeichen();

    <T> T accept (ZeichenVisitor<T> visitor);

    boolean matches(Zeichen zeichen);
	
}
