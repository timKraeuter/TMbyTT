package turingmaschine;

public interface Zeichen {

    char BLANK = '_';

    static Zeichen create(Character eingabeZeichen) {
        if (eingabeZeichen.equals(BLANK)) {
            return Blank.getInstance();
        }
        return NormalesZeichen.create(eingabeZeichen);
    }

    Character getZeichen();
	
}
