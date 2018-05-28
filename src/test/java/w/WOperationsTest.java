package w;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import turingmaschine.TuringMaschineMitBand;
import turingmaschine.TuringMaschinen;
import turingmaschine.band.ChangeableBand;

public class WOperationsTest {

	@Test
	public void testAddierer() {
		ChangeableBand result = ChangeableBand.create();
		TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
		assertEquals("8", result.toString());

	}

	/**
	 * main() { x = 5 + 3 }
	 */
	@Test
	public void testAddiererMaschineMitRecycletemBand() {
		ChangeableBand result = ChangeableBand.create();
		TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
		TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result).simuliere();
		assertEquals(13, result.toString());

		// x = TuringMaschinen.createAdd().simuliereDeterministisch("5", "3",
		// "").getLetztesBand().toString();
		// x = TuringMaschinen.createAdd().simuliereDeterministisch("7", "3",
		// "").getLetztesBand().toString();
		// System.out.println(x);
	}

	@Test
	public void testSequence() {
		ChangeableBand x = ChangeableBand.create();
		ChangeableBand y = ChangeableBand.create();
		TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), x);
		TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("5"), y);
		TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
		seqMachine.simuliere();
		assertEquals("8", x.toString());
		assertEquals("10", y.toString());
	}

	@Test
	public void testCopy() {
		// assertTrue(TuringMaschinen.createCopy().simuliere("5",
		// "_").iterator().next().bandContains("5", 2));
		// assertTrue(TuringMaschinen.createCopy().simuliere("",
		// "_").iterator().next().bandContains("", 2));
		// assertTrue(TuringMaschinen.createCopy().simuliere("1234567890",
		// "_").iterator().next()
		// .bandContains("1234567890", 2));
	}

}
