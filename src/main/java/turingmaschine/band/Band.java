package turingmaschine.band;

import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.BeliebigesZeichenOhneBlank;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.NormalesZeichen;
import turingmaschine.band.zeichen.Zeichen;
import turingmaschine.band.zeichen.ZeichenVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repräsentation des Bandes einer TM.
 */
public abstract class Band {


    private final List<Zeichen> inhalteDesBands;
    private int positionDesSchreibLeseKopfes;


    protected Band(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
        this.inhalteDesBands = new ArrayList<>(inhalteDesBands);
        this.positionDesSchreibLeseKopfes = positionDesSchreibLeseKopfes;
    }

    void schreibeZeichen(final List<Zeichen> bandInhalt, final Zeichen zuSchreibendesZeichen, final Zeichen gelesenesZeichen) {
        zuSchreibendesZeichen.accept(new ZeichenVisitor<Void>() {
            @Override
            public Void handle(final NormalesZeichen normalesZeichen) {
                bandInhalt.set(Band.this.positionDesSchreibLeseKopfes, normalesZeichen);
                return null;
            }

            @Override
            public Void handle(final BeliebigesZeichen beliebigesZeichen) {
                bandInhalt.set(Band.this.positionDesSchreibLeseKopfes, gelesenesZeichen);
                return null;
            }

            @Override
            public Void handle(final Blank blank) {
                bandInhalt.set(Band.this.positionDesSchreibLeseKopfes, blank);
                return null;
            }

            @Override
            public Void handle(final BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank) {
                bandInhalt.set(Band.this.positionDesSchreibLeseKopfes, gelesenesZeichen);
                return null;
            }
        });
    }

    /**
     * @return Zeichen an der Position des SchreibLeseKopfes des Bandes.
     */
    public Zeichen getAktuellesZeichen() {
        if (this.inhalteDesBands.isEmpty()) {
            return Blank.getInstance();
        }
        if (this.positionDesSchreibLeseKopfes >= this.inhalteDesBands.size()) {
            System.out.println("Should not happen :D !");
            this.inhalteDesBands.add(Blank.getInstance());
        }
        return this.inhalteDesBands.get(this.positionDesSchreibLeseKopfes);
    }

    /**
     * Verarbeitet gelesenesZeichen, schriebt zuSchreibendesZeichen und vollzieht lesekopfBewegung.
     *
     * @param zuSchreibendesZeichen Zeichen, das geschrieben wird
     * @param lesekopfBewegung      , die vollzogen wird
     * @param gelesenesZeichen      , Zeichen, das gelesen wird
     * @return das Band nach der Verarbeitung
     */
    public abstract Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen);

    /**
     * Prüft, ob this den übergebenen bandInhalt enthält.
     *
     * @param bandInhalt für den geschaut wird, ob er auf dem Band steht.
     * @return Ergebnis der Prüfung
     */
    public boolean bandContains(final String bandInhalt) {
        return this.getBandInhalt().contains(bandInhalt);
    }

    public void addInitialeZeichen(final Zeichen zeichenToAdd) {
        this.inhalteDesBands.add(zeichenToAdd);
    }

    private static final String ANSI_RESET = ""; //Farbcodes welche leider nicht in Eclipse funktionieren "\u001B[0m";
    private static final String ANSI_RED = ""; //Farbcodes welche leider nicht in Eclipse funktionieren "\u001B[31m";

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.inhalteDesBands.size(); i++) {
            builder.append(" | ");
            final boolean istAktuellesZeichen = i == this.positionDesSchreibLeseKopfes;
            final Zeichen zeichen = this.inhalteDesBands.get(i);
            if (istAktuellesZeichen) {
                builder.append(Band.ANSI_RED + "[" + Band.ANSI_RESET);
            }
            zeichen.accept(new ZeichenVisitor<Void>() {
                @Override
                public Void handle(final NormalesZeichen normalesZeichen) {
                    builder.append(normalesZeichen.getZeichen());
                    return null;
                }

                @Override
                public Void handle(final BeliebigesZeichen beliebigesZeichen) {
                    return null;
                }

                @Override
                public Void handle(final Blank blank) {
                    builder.append(Blank.getInstance().getZeichen());
                    return null;
                }

                @Override
                public Void handle(final BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank) {
                    return null;
                }
            });
            if (istAktuellesZeichen) {
                builder.append(Band.ANSI_RED + "]" + Band.ANSI_RESET);
            }
        }
        builder.append(" | ");
        return builder.toString();
    }


    public String getBandInhalt() {
        final StringBuilder builder = new StringBuilder();
        for (final Zeichen zeichen : this.inhalteDesBands) {
            zeichen.accept(new ZeichenVisitor<Void>() {
                @Override
                public Void handle(final NormalesZeichen normalesZeichen) {
                    builder.append(normalesZeichen.getZeichen());
                    return null;
                }

                @Override
                public Void handle(final BeliebigesZeichen beliebigesZeichen) {
                    return null;
                }

                @Override
                public Void handle(final Blank blank) {
                    return null;
                }

                @Override
                public Void handle(final BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank) {
                    return null;
                }
            });
        }
        return builder.toString();
    }

    List<Zeichen> getInhalteDesBands() {
        return this.inhalteDesBands;
    }

    int getPositionDesSchreibLeseKopfes() {
        return this.positionDesSchreibLeseKopfes;
    }

    void setPositionDesSchreibLeseKopfes(final int positionDesSchreibLeseKopfes) {
        this.positionDesSchreibLeseKopfes = positionDesSchreibLeseKopfes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Band that = (Band) o;
        return this.positionDesSchreibLeseKopfes == that.positionDesSchreibLeseKopfes &&
                Objects.equals(this.inhalteDesBands, that.inhalteDesBands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.inhalteDesBands, this.positionDesSchreibLeseKopfes);
    }
}
