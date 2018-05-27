package turingmaschine.band.zeichen;

public class NormalesZeichen implements Zeichen {
	
	private final Character zeichen;
	
	public NormalesZeichen(final Character zeichen) {
		this.zeichen = zeichen;
	}

	static NormalesZeichen create(final char zeichen) {
		if (zeichen == BLANK) {
			throw new RuntimeException("Normales Zeichen darf nicht Blank sein!");
		}
		return new NormalesZeichen(zeichen);
	}

	@Override
	public Character getZeichen() {
		return this.zeichen;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.zeichen == null) ? 0 : this.zeichen.hashCode());
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
		final NormalesZeichen other = (NormalesZeichen) obj;
		if (this.zeichen == null) {
            return other.zeichen == null;
		} else return this.zeichen.equals(other.zeichen);
    }

	@Override
	public String toString() {
		return zeichen.toString();
	}
}
