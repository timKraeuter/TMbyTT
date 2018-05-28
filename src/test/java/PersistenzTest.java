import org.junit.Test;
import persistenz.TMPersistierer;
import turingmaschine.ElementDerUeberfuehrungsfunktion;
import turingmaschine.Zustand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersistenzTest {
	
	@Test
	public void testElementDerUeberFuehrungsfunktion() {
		final Zustand vonZustand = Zustand.create("vonZustand");
		final Zustand endZustand = Zustand.create("endZustand");
		final Zeichen zuLesendesZeichen = Zeichen.create('a');
		final List<Zeichen> zuLesendeZeichen = new ArrayList<>();
		zuLesendeZeichen.add(zuLesendesZeichen);
		final Zeichen zuSchreibendesZeichen = Zeichen.create('b');
		final List<Zeichen> zuSchreibendeZeichen = new ArrayList<>();
		zuSchreibendeZeichen.add(zuSchreibendesZeichen);
		final Lesekopfbewegung lesekopfbewegung = Lesekopfbewegung.N;
		final List<Lesekopfbewegung> lesekopfBewegungen = new ArrayList<>();
		lesekopfBewegungen.add(lesekopfbewegung);
		
		final ElementDerUeberfuehrungsfunktion e =
				ElementDerUeberfuehrungsfunktion.create(vonZustand, endZustand, zuLesendeZeichen, zuSchreibendeZeichen, lesekopfBewegungen);
		final ElementDerUeberfuehrungsfunktion eVonXML =
				(ElementDerUeberfuehrungsfunktion) TMPersistierer.getInstance().vonXML(TMPersistierer.getInstance().zuXML(e));
		assertEquals(e, eVonXML);
	}
	
}
