package turingmaschine;

public class Konfiguration {

    private final Zustand zustand;
    private final Band band;

    private Konfiguration(final Zustand zustand, final Band band) {
        this.zustand = zustand;
        this.band = band;
    }

    public static Konfiguration create(Zustand zustand, Band band) {
        return new Konfiguration(zustand, band);
    }

    public boolean isEndKonfiguration() {
        throw new UnsupportedOperationException();
    }
}
