package turingmaschine;

import java.math.BigInteger;

public class Zustand {
	
	private static BigInteger counter = BigInteger.ZERO;
	private String name;
	
	private Zustand(final String name) {
		this.name = name;
	}
	
	public static Zustand create(final String name) {
		return new Zustand(name);
	}
	
	public static Zustand create() {
		final String generierterName = "z" + Zustand.counter;
        Zustand.counter = Zustand.counter.add(BigInteger.ONE);
		return new Zustand(generierterName);
	}
	
    void addToName(final String appendix) {
	    this.name = this.name + appendix;
    }

    @Override
    public String toString() {
        return this.name ;
    }
}
