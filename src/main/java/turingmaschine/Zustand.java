package turingmaschine;

import java.math.BigInteger;

public class Zustand {
	
	private static BigInteger counter = BigInteger.ZERO;
	private final String name;
	
	private Zustand(final String name) {
		this.name = name;
	}
	
	public static Zustand create(final String name) {
		return new Zustand(name);
	}
	
	public static Zustand create() {
		final String generierterName = "z" + counter;
		counter = counter.add(BigInteger.ONE);
		return new Zustand(generierterName);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final Zustand other = (Zustand) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
}
