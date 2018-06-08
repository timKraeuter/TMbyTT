package turingmaschine.band;

import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.List;

/**
 * Band einer TM, das verändert werden kann bzw. Band, dessen Attribut band umgesetzt werden kann.
 */
public class ChangeableBand extends Band {
	

	private ChangeableBand(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
	    super(inhalteDesBands, positionDesSchreibLeseKopfes);
	}
	
	public static ChangeableBand create(final String eingabe) {
        final ChangeableBand band = new ChangeableBand(new ArrayList<>(), 0);
        eingabe.chars().forEach(c -> band.addInitialeZeichen(Zeichen.create((char) c)));
        return band;
	}
	
	public static ChangeableBand create() {
		return ChangeableBand.create("");
	}
	
	/**
	 * Verarbeitet die übergebenen Parameter und setzt this.band neu.
	 */
	@Override
	public Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung, final Zeichen gelesenesZeichen) {
        if (this.getInhalteDesBands().isEmpty()) {
            this.getInhalteDesBands().add(0, zuSchreibendesZeichen);
        } else {
            this.schreibeZeichen(this.getInhalteDesBands(), zuSchreibendesZeichen, gelesenesZeichen);
        }
        switch (lesekopfBewegung) {
            case R:
                this.setPositionDesSchreibLeseKopfes(this.getPositionDesSchreibLeseKopfes() + 1);
                if (this.getInhalteDesBands().size() == this.getPositionDesSchreibLeseKopfes()) { // Rechts raus laufen
                    this.getInhalteDesBands().add(Blank.getInstance());
                }
                break;
            case L:
                this.setPositionDesSchreibLeseKopfes(this.getPositionDesSchreibLeseKopfes() - 1);
                if (this.getPositionDesSchreibLeseKopfes() == -1) { // Links raus laufen.
                    this.getInhalteDesBands().add(0, Blank.getInstance());
                    this.setPositionDesSchreibLeseKopfes(this.getPositionDesSchreibLeseKopfes() + 1);
                }
                break;
            case N:
                break;
            default:
                throw new RuntimeException("Should not happen!");
        }
		return this;
	}

    public void reset() {
        this.getInhalteDesBands().clear();
        this.setPositionDesSchreibLeseKopfes(0);
    }

}
