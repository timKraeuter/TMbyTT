package turingmaschine;

import java.util.List;

//test kommenater
public class Band {
	
	private final List<Zeichen> inhalteDesBands;
	// Löwe würde jetzt BigInteger nehmen :D
	private final int positionDesSchreibLeseKopfes;
	
	
	private Band(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		this.inhalteDesBands = inhalteDesBands;
		this.positionDesSchreibLeseKopfes = positionDesSchreibLeseKopfes;
	}
	
	public static Band create(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		return new Band(inhalteDesBands, positionDesSchreibLeseKopfes);
	}
	
	public Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung) {
		// Sonderfälle wenn die position bei 0 oder der Länge der Liste bzw. offset bei 1 ist.
		throw new UnsupportedOperationException();
	}
}
