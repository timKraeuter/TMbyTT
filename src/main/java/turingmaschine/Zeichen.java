package turingmaschine;

public interface Zeichen {

    char BLANK = '_';

    static Zeichen create(char eingabeZeichen) {
        if (eingabeZeichen == BLANK) {
            return Blank.getInstance();
        }
        return NormalesZeichen.create(eingabeZeichen);
    }

    Character getZeichen();
	
}
