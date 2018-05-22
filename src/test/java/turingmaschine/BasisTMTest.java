package turingmaschine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BasisTMTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void leereEingabeWirdErkannt() {
		final Zustand startZustand = Zustand.create();
		final Zustand endZustand = Zustand.create();
		final TuringMaschine turingMaschine = TuringMaschine.create(startZustand,
				Collections.emptySet(),
				Collections.singleton(endZustand),
				Collections.singleton(ElementDerUeberfuehrungsfunktion.create(startZustand,
						endZustand,
						Blank.getInstance(),
						Blank.getInstance(),
						Lesekopfbewegung.Neutral)));
		assertTrue(turingMaschine.erkenntEingabe(""));
		assertFalse(turingMaschine.erkenntEingabe("a"));
		assertFalse(turingMaschine.erkenntEingabe("b"));
		assertFalse(turingMaschine.erkenntEingabe("König ist der beste Prof der Welt"));
		
	}
	
	@Test
	public void tmErkennt123() {
		final Zustand startZustand = Zustand.create();
		final Zustand endZustand = Zustand.create();
		final Set<Zustand> zustaende = new HashSet<>();
		final Zustand z1 = Zustand.create();
		final Zustand z2 = Zustand.create();
		final Zustand z3 = Zustand.create();
		
		zustaende.add(z1);
		zustaende.add(z2);
		zustaende.add(z3);
		
		final Set<ElementDerUeberfuehrungsfunktion> ueberfuehrungsfunktion = new HashSet<>();
		ueberfuehrungsfunktion.add(ElementDerUeberfuehrungsfunktion.create(startZustand,
				z1,
				NormalesZeichen.create('1'),
				Blank.getInstance(),
				Lesekopfbewegung.Rechts));
		ueberfuehrungsfunktion.add(ElementDerUeberfuehrungsfunktion.create(z1,
				z2,
				NormalesZeichen.create('2'),
				Blank.getInstance(),
				Lesekopfbewegung.Rechts));
		ueberfuehrungsfunktion.add(ElementDerUeberfuehrungsfunktion.create(z2,
				z3,
				NormalesZeichen.create('3'),
				Blank.getInstance(),
				Lesekopfbewegung.Rechts));
		ueberfuehrungsfunktion.add(ElementDerUeberfuehrungsfunktion.create(z3,
				endZustand,
				Blank.getInstance(),
				Blank.getInstance(),
				Lesekopfbewegung.Neutral));
		
		
		final TuringMaschine turingMaschine = TuringMaschine.create(startZustand,
				                                                    zustaende,
				                                                    Collections.singleton(endZustand),
				                                                    ueberfuehrungsfunktion);
		
		assertTrue(turingMaschine.erkenntEingabe("123"));
		assertFalse(turingMaschine.erkenntEingabe("1"));
		assertFalse(turingMaschine.erkenntEingabe("12"));
		assertFalse(turingMaschine.erkenntEingabe("1234"));
		assertFalse(turingMaschine.erkenntEingabe(""));
		assertFalse(turingMaschine.erkenntEingabe("König ist der beste Prof der Welt"));
		
	}
}
