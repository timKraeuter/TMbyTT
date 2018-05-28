package turingmaschine;

import org.junit.Test;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.BeliebigesZeichen;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class BeliebigesZeichenTest {

    @Test
    public void testBeliebigesZeichenTM() {
        final Zustand startZustand = Zustand.create();
        final Zustand endZustand = Zustand.create();

        final TuringMaschinenBuilder builder = TuringMaschine.builder();
        builder.startZustand(startZustand);
        builder.addEndZustand(endZustand);
        builder.anzahlDerBaender(1);
        builder.ueberfuehrungsfunktion(Collections.singleton(ElementDerUeberfuehrungsfunktion.create(startZustand,
                endZustand,
                Collections.singletonList(BeliebigesZeichen.getInstance()),
                Collections.singletonList(BeliebigesZeichen.getInstance()),
                Collections.singletonList(Lesekopfbewegung.N))));

        final TuringMaschine turingMaschine = builder.build();
        // TuringMaschine, welche beliebige 1 Ziffer Wörter erkennt und wieder so ausgibt.
        assertTrue(turingMaschine.simuliere("a").stream().findAny().get()
                .bandContains("a",1));
        assertTrue(turingMaschine.simuliere("b").stream().findAny().get()
                .bandContains("b",1));
        assertTrue(turingMaschine.simuliere("c").stream().findAny().get()
                .bandContains("c",1));
        assertTrue(turingMaschine.simuliere("d").stream().findAny().get()
                .bandContains("d",1));
        assertTrue(turingMaschine.simuliere("e").stream().findAny().get()
                .bandContains("e",1));
    }
}
