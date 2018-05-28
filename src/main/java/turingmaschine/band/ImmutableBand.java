package turingmaschine.band;

import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.NormalesZeichen;
import turingmaschine.band.zeichen.Zeichen;
import turingmaschine.band.zeichen.ZeichenVisitor;

import java.util.ArrayList;
import java.util.List;

public class ImmutableBand implements Band {

    private final List<Zeichen> inhalteDesBands;
    // Löwe würde jetzt BigInteger nehmen :D
    private final int positionDesSchreibLeseKopfes;


    private ImmutableBand(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
        this.inhalteDesBands = inhalteDesBands;
        this.positionDesSchreibLeseKopfes = positionDesSchreibLeseKopfes;
    }

    public static ImmutableBand create() {
        return new ImmutableBand(new ArrayList<>(), 0);
    }

    public static ImmutableBand create(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
        return new ImmutableBand(inhalteDesBands, positionDesSchreibLeseKopfes);
    }

    public static ImmutableBand create(final String eingabe) {
        final ImmutableBand band = new ImmutableBand(new ArrayList<>(), 0);
        eingabe.chars().forEach(c -> band.addZeichen(Zeichen.create((char) c)));
        return band;
    }

    public void addZeichen(final Zeichen zeichenToAdd) {
        this.inhalteDesBands.add(zeichenToAdd);
    }

    @Override
    public ImmutableBand verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen) {

        final List<Zeichen> inhalteDesNeuenBands = new ArrayList<>(this.inhalteDesBands);
        if (this.inhalteDesBands.isEmpty()) {
            inhalteDesNeuenBands.add(0, zuSchreibendesZeichen);
        } else {
            this.schreibeZeichen(inhalteDesNeuenBands, zuSchreibendesZeichen, gelesenesZeichen);
        }

        int positionDesNeuenSchreibLeseKopfes;
        switch (lesekopfBewegung) {
            case R:
                positionDesNeuenSchreibLeseKopfes = this.positionDesSchreibLeseKopfes + 1;
                if (this.inhalteDesBands.size() == positionDesNeuenSchreibLeseKopfes) {
                    inhalteDesNeuenBands.add(Blank.getInstance());
                }
                break;
            case L:
                positionDesNeuenSchreibLeseKopfes = this.positionDesSchreibLeseKopfes - 1;
                if (positionDesNeuenSchreibLeseKopfes == -1) {
                    inhalteDesNeuenBands.add(0, Blank.getInstance());
                    positionDesNeuenSchreibLeseKopfes = 0;
                }
                break;
            case N:
                positionDesNeuenSchreibLeseKopfes = this.positionDesSchreibLeseKopfes;
                break;
            default:
                throw new RuntimeException();
        }

        return ImmutableBand.create(inhalteDesNeuenBands, positionDesNeuenSchreibLeseKopfes);
        // TODO: Sonderfälle wenn die position bei 0 oder der Länge der Liste bzw. offset bei 1 ist.
    }

    private void schreibeZeichen(final List<Zeichen> inhalteDesNeuenBands, final Zeichen zuSchreibendesZeichen, final Zeichen gelesenesZeichen) {
        zuSchreibendesZeichen.accept(new ZeichenVisitor<Void>() {
            @Override
            public Void handle(final NormalesZeichen normalesZeichen) {
                inhalteDesNeuenBands.set(ImmutableBand.this.positionDesSchreibLeseKopfes, normalesZeichen);
                return null;
            }

            @Override
            public Void handle(final BeliebigesZeichen beliebigesZeichen) {
                inhalteDesNeuenBands.set(ImmutableBand.this.positionDesSchreibLeseKopfes, gelesenesZeichen);
                return null;
            }

            @Override
            public Void handle(final Blank blank) {
                inhalteDesNeuenBands.set(ImmutableBand.this.positionDesSchreibLeseKopfes, blank);
                return null;
            }
        });
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.inhalteDesBands == null) ? 0 : this.inhalteDesBands.hashCode());
        result = prime * result + this.positionDesSchreibLeseKopfes;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableBand other = (ImmutableBand) obj;
        if (this.inhalteDesBands == null) {
            if (other.inhalteDesBands != null) {
                return false;
            }
        } else if (!this.inhalteDesBands.equals(other.inhalteDesBands)) {
            return false;
        }
        return this.positionDesSchreibLeseKopfes == other.positionDesSchreibLeseKopfes;
    }


    @Override
    public Zeichen getAktuellesZeichen() {
        if (this.inhalteDesBands.isEmpty()) {
            return Blank.getInstance();
        }
        if (this.positionDesSchreibLeseKopfes >= this.inhalteDesBands.size()) {
            this.inhalteDesBands.add(Blank.getInstance());
        }
        return this.inhalteDesBands.get(this.positionDesSchreibLeseKopfes);
    }

    @Override
    public String toString() {
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
            });
        }
        return builder.toString();
    }
}