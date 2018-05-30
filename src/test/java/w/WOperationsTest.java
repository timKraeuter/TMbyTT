package w;

import org.junit.Test;
import turingmaschine.TuringMaschineMitBand;
import turingmaschine.TuringMaschinen;
import turingmaschine.band.ChangeableBand;

import static org.junit.Assert.assertEquals;

public class WOperationsTest {
	@Test
	public void testAddierer() {
		final ChangeableBand result = ChangeableBand.create();
		TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
		assertEquals("8", result.toString());

	}

	@Test
	public void testAddiererMaschineMitRecycletemBand() {
		final ChangeableBand result = ChangeableBand.create();
		TuringMaschineMitBand m1 = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"),
				result);
		TuringMaschineMitBand m2 = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
		TuringMaschinen.createSeq(m1, m2).simuliere();
		assertEquals("13", result.toString());
	}

	@Test
	public void testAddiererMaschineMitRecycletemBandEinfach() {
		final ChangeableBand result = ChangeableBand.create("1");
		TuringMaschineMitBand m = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
		m.simuliere();
		assertEquals("6", result.toString());
	}

	@Test
	public void testSequenceAdd() {
		final ChangeableBand x = ChangeableBand.create();
		final ChangeableBand y = ChangeableBand.create();
		final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"),
				ChangeableBand.create("3"), x);
		final TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"),
				ChangeableBand.create("5"), y);
		final TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
		seqMachine.simuliere();
		assertEquals("8", x.toString());
		assertEquals("10", y.toString());
	}

	@Test
	public void testSequenceAddOhneVeraenderungDerBaender() {
		final ChangeableBand x = ChangeableBand.create();
		final ChangeableBand y = ChangeableBand.create();
		final ChangeableBand c1 = ChangeableBand.create("1");
		final ChangeableBand c2 = ChangeableBand.create("2");
		final ChangeableBand c3 = ChangeableBand.create("3");
		final ChangeableBand c4 = ChangeableBand.create("4");
		final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(c1, c2, x);
		final TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(c3, c4, y);
		final TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
		seqMachine.simuliere();
		assertEquals("3", x.toString());
		assertEquals("7", y.toString());
		assertEquals("1", c1.toString());
		assertEquals("2", c2.toString());
		assertEquals("3", c3.toString());
		assertEquals("4", c4.toString());
	}

	@Test
	public void testSequenceAddReusingVars() {
		final ChangeableBand x = ChangeableBand.create();
		final ChangeableBand y = ChangeableBand.create();
		final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"),
				ChangeableBand.create("3"), x);
		final TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(x, ChangeableBand.create("5"), y);
		final TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
		seqMachine.simuliere();
		assertEquals("8", x.toString());
		assertEquals("13", y.toString());
	}

	@Test
	public void testCopyAndCopySequence() {
		final ChangeableBand x = ChangeableBand.create();
		final ChangeableBand y = ChangeableBand.create();
		final TuringMaschineMitBand xGleich5 = TuringMaschinen.createCopy(ChangeableBand.create("5"), x);
		xGleich5.simuliere();
		assertEquals("5", x.toString());
		final TuringMaschineMitBand yGleichX = TuringMaschinen.createCopy(x, y);
		yGleichX.simuliere();
		assertEquals("5", y.toString());

		x.wipe();
		y.wipe();
		final TuringMaschineMitBand yGleichXGleich5 = TuringMaschinen.createSeq(xGleich5, yGleichX);
		yGleichXGleich5.simuliere();
		assertEquals("5", x.toString());
		assertEquals("5", y.toString());
	}
}
