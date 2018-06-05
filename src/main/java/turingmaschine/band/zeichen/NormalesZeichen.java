package turingmaschine.band.zeichen;

/**
 * Normales Zeichen im Kontext von Turing-Maschinen, also nicht etwa der Blank.
 */
public class NormalesZeichen implements Zeichen {
	
	private final Character zeichen;
	
	public NormalesZeichen(final Character zeichen) {
		this.zeichen = zeichen;
	}
	
	static NormalesZeichen create(final char zeichen) {
		if (zeichen == Zeichen.BLANK) {
			throw new RuntimeException("Normales Zeichen darf nicht Blank sein!");
		}
		return new NormalesZeichen(zeichen);
	}
	
	@Override
	public Character getZeichen() {
		return this.zeichen;
	}
	
	@Override
	public <T> T accept(final ZeichenVisitor<T> visitor) {
		return visitor.handle(this);
	}
	
	@Override
	public boolean matches(final Zeichen zeichen) {
		return zeichen.accept(new ZeichenVisitor<Boolean>() {
			@Override
			public Boolean handle(final NormalesZeichen normalesZeichen) {
				return NormalesZeichen.this.equals(normalesZeichen);
			}
			
			@Override
			public Boolean handle(final BeliebigesZeichen beliebigesZeichen) {
				return true;
			}
			
			@Override
			public Boolean handle(final Blank blank) {
				return false;
			}
			
			@Override
			public Boolean handle(final BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank) {
				return true;
			}
		});
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
		} else {
			return this.zeichen.equals(other.zeichen);
		}
	}
	
	@Override
	public String toString() {
		return this.zeichen.toString();
	}
}
