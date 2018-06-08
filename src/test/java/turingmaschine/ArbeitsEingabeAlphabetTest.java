package turingmaschine;

import org.junit.Test;
import turingmaschine.band.zeichen.Zeichen;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ArbeitsEingabeAlphabetTest {

    @Test
    public void eingabeAlphabet() {
        final Set<String> erwartetesEingabealphabet = new HashSet<>();
        IntStream.range(0, 10).forEach(value -> erwartetesEingabealphabet.add(String.valueOf(value)));
        assertEquals(erwartetesEingabealphabet, TuringMaschinen.incrementMaschine().getEingabealphabet().stream().map(Object::toString).collect(Collectors.toSet()));
    }

    @Test
    public void arbeitsAlphabet() {
        final Set<String> erwartetesArbeitsalphabet = new HashSet<>();
        IntStream.range(0, 10).forEach(value -> erwartetesArbeitsalphabet.add(String.valueOf(value)));
        erwartetesArbeitsalphabet.add(String.valueOf(Zeichen.BLANK));
        erwartetesArbeitsalphabet.add(String.valueOf(Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK));
        assertEquals(erwartetesArbeitsalphabet, TuringMaschinen.incrementMaschine().getArbeitsalphabet().stream().map(Object::toString).collect(Collectors.toSet()));
    }
}
