package feature;

public class UeberfuehrungsDAO {

    private String vonZustand;
    private String zuZustand;
    private char eingabeZeichen;
    private char zuSchreibendeZeichen;
    private String schreibLesekopfBewegungen;

    public String getVonZustand() {
        return vonZustand;
    }

    public String getZuZustand() {
        return zuZustand;
    }

    public char getEingabeZeichen() {
        return eingabeZeichen;
    }

    public char getZuSchreibendeZeichen() {
        return zuSchreibendeZeichen;
    }

    public String getSchreibLesekopfBewegungen() {
        return schreibLesekopfBewegungen;
    }
}
