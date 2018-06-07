import org.junit.Test;
import turingmaschine.ElementDerUeberfuehrungsfunktion;
import turingmaschine.TuringMaschine;
import turingmaschine.Zustand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistenzTest {

    @Test
    public void testPersistTristan() {
        final Zeichen aL = Zeichen.create('a');
        final Zeichen bL = Zeichen.create('b');
        final List<Zeichen> zuLesendeZeichen = new ArrayList<>();
        zuLesendeZeichen.add(aL);
        zuLesendeZeichen.add(bL);

        final Zeichen bW = Zeichen.create('b');
        final Zeichen cW = Zeichen.create('c');
        final List<Zeichen> zuSchreibendeZeichen = new ArrayList<>();
        zuSchreibendeZeichen.add(bW);
        zuSchreibendeZeichen.add(cW);

        final Lesekopfbewegung l1 = Lesekopfbewegung.N;
        final Lesekopfbewegung l2 = Lesekopfbewegung.R;
        final List<Lesekopfbewegung> lesekopfBewegungen = new ArrayList<>();
        lesekopfBewegungen.add(l1);
        lesekopfBewegungen.add(l2);

        final ElementDerUeberfuehrungsfunktion e =
                ElementDerUeberfuehrungsfunktion.create(
                        Zustand.create("von"),
                        Zustand.create("zu"),
                        zuLesendeZeichen,
                        zuSchreibendeZeichen,
                        lesekopfBewegungen);

        final ElementDerUeberfuehrungsfunktion e2 =
                ElementDerUeberfuehrungsfunktion.create(
                        Zustand.create("von"),
                        Zustand.create("zuwasanderem"),
                        zuLesendeZeichen,
                        zuSchreibendeZeichen,
                        lesekopfBewegungen);

        final Set<Zustand> end = new HashSet<>();
        end.add(Zustand.create("ende1"));
        end.add(Zustand.create("ende2"));

        final Set<ElementDerUeberfuehrungsfunktion> es = new HashSet<>();
        es.add(e);
        es.add(e2);

        System.out.println(TuringMaschine.create(
                Zustand.create("start"),
                end,
                es,
                2).toXML());
    }

}
