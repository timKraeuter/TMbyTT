package turingmaschine;

public class NormalesZeichen implements Zeichen {

    private final Character zeichen;

    public NormalesZeichen(final Character zeichen) {
        this.zeichen = zeichen;
    }

    public static NormalesZeichen create(Character zeichen) {
        return new NormalesZeichen(zeichen);
    }
}
