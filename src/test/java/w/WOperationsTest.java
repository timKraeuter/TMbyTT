package w;

import org.junit.Test;
import turingmaschine.TuringMaschinen;

import static org.junit.Assert.assertTrue;

public class WOperationsTest {

	@Test
	public void testAddierer() {
		assertTrue(TuringMaschinen.createAdd().simuliere("5", "3", "").iterator().next()
				.bandContains("8", 3));
		assertTrue(TuringMaschinen.createAdd().simuliere("5", "3", "_").iterator().next()
				.bandContains("8", 3));
	}
}
