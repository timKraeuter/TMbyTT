package turingmaschine;

public class NormalesZeichen implements Zeichen {
	
	private final Character zeichen;
	
	public NormalesZeichen(final Character zeichen) {
		this.zeichen = zeichen;
	}
	
	public static NormalesZeichen create(final Character zeichen) {
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
			if (other.zeichen != null) {
				return false;
			}
		} else if (!this.zeichen.equals(other.zeichen)) {
			return false;
		}
		return true;
	}
	
}
