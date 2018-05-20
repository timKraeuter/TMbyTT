package turingmaschine;

import java.util.ArrayList;
import java.util.List;

public class Band {
	
	private final List<Zeichen> inhalteDesBands;
	// Löwe würde jetzt BigInteger nehmen :D
	private final int positionDesSchreibLeseKopfes;
	
	
	private Band(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		this.inhalteDesBands = inhalteDesBands;
		this.positionDesSchreibLeseKopfes = positionDesSchreibLeseKopfes;
	}
	
	public static Band create() {
		return new Band(new ArrayList<>(), 0);
	}
	
	public static Band create(final List<Zeichen> inhalteDesBands, final int positionDesSchreibLeseKopfes) {
		return new Band(inhalteDesBands, positionDesSchreibLeseKopfes);
	}
	
	public static Band create(final String eingabe) {
		final Band band = new Band(new ArrayList<>(), 0);
		eingabe.chars().forEach(c -> band.addZeichen(NormalesZeichen.create((char) c)));
		return band;
	}
	
	public List<Zeichen> getInhalteDesBands() {
		return this.inhalteDesBands;
	}
	
	public int getPositionDesSchreibLeseKopfes() {
		return this.positionDesSchreibLeseKopfes;
	}
	
	public void addZeichen(final Zeichen zeichenToAdd) {
		this.inhalteDesBands.add(zeichenToAdd);
	}
	
	public Band verarbeite(final Zeichen zuSchreibendesZeichen, final Lesekopfbewegung lesekopfBewegung) {
		// Sonderfälle wenn die position bei 0 oder der Länge der Liste bzw. offset bei 1 ist.
		throw new UnsupportedOperationException();
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
		final Band other = (Band) obj;
		if (this.inhalteDesBands == null) {
			if (other.inhalteDesBands != null) {
				return false;
			}
		} else if (!this.inhalteDesBands.equals(other.inhalteDesBands)) {
			return false;
		}
		if (this.positionDesSchreibLeseKopfes != other.positionDesSchreibLeseKopfes) {
			return false;
		}
		return true;
	}
	
	@Override
	public
	String toString() {
		final StringBuilder s = new StringBuilder();
		this.inhalteDesBands.forEach(z -> s.append(z.getZeichen()).append(" "));
		return "{ " + "Position des Schreiblesekopfes: " + this.positionDesSchreibLeseKopfes +
				", Inhalt des Bandes: " + s.toString() + " }";
	}
	
}
