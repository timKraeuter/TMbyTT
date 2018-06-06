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
 * Band, das nicht verändert werden kann.
 */
public class ImmutableBand implements Band {
	
	private final List<Zeichen> inhalteDesBands;
	private final int positionDesSchreibLeseKopfes;
	
	private ImmutableBand(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		this.inhalteDesBands = new ArrayList<>(inhalteDesBands);
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
			
			@Override
			public Void handle(final BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank) {
				inhalteDesNeuenBands.set(ImmutableBand.this.positionDesSchreibLeseKopfes, gelesenesZeichen);
				return null;
			}
		});
	}
	
	public void addZeichen(final Zeichen zeichenToAdd) {
		this.inhalteDesBands.add(zeichenToAdd);
	}
	
	@Override
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
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.inhalteDesBands.size(); i++) {
			builder.append(" | ");
			final boolean istAktuellesZeichen = i == this.positionDesSchreibLeseKopfes;
			final Zeichen zeichen = this.inhalteDesBands.get(i);
			if (istAktuellesZeichen) {
				builder.append(ImmutableBand.ANSI_RED + "[" + ImmutableBand.ANSI_RESET);
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
				builder.append(ImmutableBand.ANSI_RED + "]" + ImmutableBand.ANSI_RESET);
			}
		}
		builder.append(" | ");
		return builder.toString();
	}


    @Override
    public String getBandInhalt() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.inhalteDesBands.size(); i++) {
            final Zeichen zeichen = this.inhalteDesBands.get(i);
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
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final ImmutableBand that = (ImmutableBand) o;
		return this.positionDesSchreibLeseKopfes == that.positionDesSchreibLeseKopfes &&
				Objects.equals(this.inhalteDesBands, that.inhalteDesBands);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(this.inhalteDesBands, this.positionDesSchreibLeseKopfes);
	}
}
