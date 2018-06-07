package turingmaschine;

import org.junit.Test;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.BeliebigesZeichenOhneBlank;
import turingmaschine.band.zeichen.Blank;
import turingmaschine.band.zeichen.Zeichen;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
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
        final ElementDerUeberfuehrungsfunktion o = ElementDerUeberfuehrungsfunktion.create(startZustand,
                endZustand,
                Collections.singletonList(BeliebigesZeichen.getInstance()),
                Collections.singletonList(BeliebigesZeichen.getInstance()),
                Collections.singletonList(Lesekopfbewegung.N));
        assertEquals(o,o);

        builder.ueberfuehrungsfunktion(Collections.singleton(o));

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

    @Test
    public void beliebigesZeichen() {
        assertEquals(Zeichen.BLANK, (char) Blank.getInstance().getZeichen());
        assertEquals(Zeichen.BELIEBIGES_ZEICHEN, (char) BeliebigesZeichen.getInstance().getZeichen());
        assertEquals(Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK, (char) BeliebigesZeichenOhneBlank.getInstance().getZeichen());

        assertEquals(String.valueOf(Zeichen.BLANK), Blank.getInstance().toString());
        assertEquals(String.valueOf(Zeichen.BELIEBIGES_ZEICHEN), BeliebigesZeichen.getInstance().toString());
        assertEquals(String.valueOf(Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK), BeliebigesZeichenOhneBlank.getInstance().toString());

        assertEquals("blank", Blank.getInstance().toXML());
        assertEquals( "_", BeliebigesZeichen.getInstance().toXML());
        assertEquals( "_", BeliebigesZeichenOhneBlank.getInstance().toXML());
    }
}
