package w;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import turingmaschine.TuringMaschinen;

public class WOperationsTest {

	@Test
	public void testAddierer() {
		assertTrue(TuringMaschinen.createAdd().simuliere("5", "3", "hier kann irgendwas stehen").iterator().next()
				.bandContains("8", 3));
	}
}
