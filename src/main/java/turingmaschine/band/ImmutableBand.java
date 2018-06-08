package turingmaschine.band;

import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.List;

/**
 * Band, das nicht verändert werden kann.
 */
public class ImmutableBand extends Band {

    private ImmutableBand(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
        super(inhalteDesBands, positionDesSchreibLeseKopfes);
    }

    public static ImmutableBand create() {
		return new ImmutableBand(new ArrayList<>(), 0);
	}
	
	public static ImmutableBand create(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		return new ImmutableBand(inhalteDesBands, positionDesSchreibLeseKopfes);
	}
	
	public static ImmutableBand create(final String eingabe) {
		final ImmutableBand band = new ImmutableBand(new ArrayList<>(), 0);
		eingabe.chars().forEach(c -> band.addInitialeZeichen(Zeichen.create((char) c)));
		return band;
	}


    @Override
    public Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen) {
        final List<Zeichen> inhalteDesNeuenBands = new ArrayList<>(this.getInhalteDesBands());
        if (this.getInhalteDesBands().isEmpty()) {
            inhalteDesNeuenBands.add(0, zuSchreibendesZeichen);
        } else {
            this.schreibeZeichen(inhalteDesNeuenBands, zuSchreibendesZeichen, gelesenesZeichen);
        }

        int positionDesNeuenSchreibLeseKopfes;
        switch (lesekopfBewegung) {
            case R:
                positionDesNeuenSchreibLeseKopfes = this.getPositionDesSchreibLeseKopfes() + 1;
                if (this.getInhalteDesBands().size() == positionDesNeuenSchreibLeseKopfes) {
                    inhalteDesNeuenBands.add(Blank.getInstance());
                }
                break;
            case L:
                positionDesNeuenSchreibLeseKopfes = this.getPositionDesSchreibLeseKopfes() - 1;
                if (positionDesNeuenSchreibLeseKopfes == -1) {
                    inhalteDesNeuenBands.add(0, Blank.getInstance());
                    positionDesNeuenSchreibLeseKopfes = 0;
                }
                break;
            case N:
                positionDesNeuenSchreibLeseKopfes = this.getPositionDesSchreibLeseKopfes();
                break;
            default:
                throw new RuntimeException("Should not happen");
        }

        return ImmutableBand.create(inhalteDesNeuenBands, positionDesNeuenSchreibLeseKopfes);
    }
}
