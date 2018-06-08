package turingmaschine;

import java.math.BigInteger;

/**
 * Repräsentation des Zustandes einer TuringMaschine.
 */
public class Zustand {

    /**
     * Counter für generische Zustandsnamen mit aufsteigender Zahl z0, z1, ...
     */
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

    /**
     * @return XML-String, um das ganze bei Tristan importieren zu können von Tristan.
     */
    public String toXML() {
		return "<state>" + this.name + "</state>";
    }
}
