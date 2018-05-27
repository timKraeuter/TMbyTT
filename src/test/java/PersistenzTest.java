import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import persistenz.Persistierer;
import turingmaschine.ElementDerUeberfuehrungsfunktion;
import turingmaschine.Zustand;
import turingmaschine.band.Lesekopfbewegung;
import turingmaschine.band.zeichen.Zeichen;

public class PersistenzTest {
	
	@Test
	public void testElementDerUeberFuehrungsfunktion() {
		final Zustand vonZustand = Zustand.create("vonZustand");
		final Zustand vonZustandVonXML = (Zustand) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(vonZustand));
		final Zustand endZustand = Zustand.create("endZustand");
		final Zustand endZustandVonXML = (Zustand) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(endZustand));
		final Zeichen zuLesendesZeichen = Zeichen.create('a');
		final List<Zeichen> zuLesendeZeichen = new ArrayList<>();
		zuLesendeZeichen.add(zuLesendesZeichen);
		final List<Zeichen> zuLesendeZeichenVonXML =
				(List<Zeichen>) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(zuLesendeZeichen));
		final Zeichen zuSchreibendesZeichen = Zeichen.create('b');
		final List<Zeichen> zuSchreibendeZeichen = new ArrayList<>();
		zuSchreibendeZeichen.add(zuSchreibendesZeichen);
		final List<Zeichen> zuSchreibendeZeichenVonXML =
				(List<Zeichen>) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(zuSchreibendeZeichen));
		final Lesekopfbewegung lesekopfbewegung = Lesekopfbewegung.N;
		final List<Lesekopfbewegung> lesekopfBewegungen = new ArrayList<>();
		lesekopfBewegungen.add(lesekopfbewegung);
		final List<Lesekopfbewegung> lesekopfBewegungenVonXML =
				(List<Lesekopfbewegung>) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(lesekopfBewegungen));
		
		final ElementDerUeberfuehrungsfunktion e =
				ElementDerUeberfuehrungsfunktion.create(vonZustand, endZustand, zuLesendeZeichen, zuSchreibendeZeichen, lesekopfBewegungen);
		final ElementDerUeberfuehrungsfunktion eVonXML =
				(ElementDerUeberfuehrungsfunktion) Persistierer.getInstance().vonXML(Persistierer.getInstance().zuXML(e));
		
		assertEquals(e, eVonXML);
	}
	
}
