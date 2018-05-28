package w;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import turingmaschine.TuringMaschinen;
import turingmaschine.band.Band;

public class WOperationsTest {

	@Test
	public void testAddierer() {
		assertTrue(TuringMaschinen.createAdd().simuliere("5", "3", "").iterator().next().bandContains("8", 3));
		assertTrue(TuringMaschinen.createAdd().simuliere("5", "3", "_").iterator().next().bandContains("8", 3));
		assertTrue(TuringMaschinen.createAdd().simuliereDeterministisch("5", "3", "984561").bandContains("8", 3));
	}

	/**
	 * main() { x = 5 + 3 }
	 */
	@Test
	public void testAddiererMaschine() {
		String x;
		x = TuringMaschinen.createAdd().simuliereDeterministisch("5", "3", "").getLetztesBand().toString();
		x = TuringMaschinen.createAdd().simuliereDeterministisch("7", "3", "").getLetztesBand().toString();
		System.out.println(x);
	}

	/**
	 * ChangableBand x = ChangeableBand.create(); ChangableBand y =
	 * ChangeableBand.create();
	 * TouringMachine.createSeq(TuringMaschinen.createAdd().createStartKonfiguration(ChangeableBand.create("5"),
	 * ChangeableBand.create("3"), x) ,
	 * TuringMaschinen.createSub().createStartKonfiguration(ChangeableBand.create("6"),
	 * x, y) )
	 */

	@Test
	public void testCopy() {
		assertTrue(TuringMaschinen.createCopy().simuliere("5", "_").iterator().next().bandContains("5", 2));
		assertTrue(TuringMaschinen.createCopy().simuliere("", "_").iterator().next().bandContains("", 2));
		assertTrue(TuringMaschinen.createCopy().simuliere("1234567890", "_").iterator().next()
				.bandContains("1234567890", 2));
	}

}
