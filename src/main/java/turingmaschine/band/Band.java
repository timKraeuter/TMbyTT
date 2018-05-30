package turingmaschine.band;

import turingmaschine.band.zeichen.Zeichen;

public interface Band {

    Zeichen getAktuellesZeichen();

    Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen);

    default boolean bandContains(final String bandInhalt) {
        return this.toString().contains(bandInhalt);
    }

}
