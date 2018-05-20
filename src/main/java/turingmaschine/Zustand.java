package turingmaschine;

import java.math.BigInteger;

public class Zustand {

    private static BigInteger counter = BigInteger.ZERO;
    private final String name;

    private Zustand(final String name) {
        this.name = name;
    }

    public static Zustand create(String name) {
        return new Zustand(name);
    }

    public static Zustand create() {
        final String generierterName = "z" + counter;
        counter = counter.add(BigInteger.ONE);
        return new Zustand(generierterName);
    }
}
