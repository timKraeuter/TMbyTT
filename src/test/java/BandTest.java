import static org.junit.Assert.assertEquals;

import org.junit.Test;

import turingmaschine.Band;
import turingmaschine.NormalesZeichen;

public class BandTest {
	
	@Test
	public void testEmptyBandCreation() {
		
		final Band actualBand = Band.create("");
		final Band expectedBand = Band.create();
		assertEquals(expectedBand, actualBand);
	}
	
	@Test
	public void testBandCreation() {
		
		final Band actualBand = Band.create("abc");
		final Band expectedBand = Band.create();
		expectedBand.addZeichen(NormalesZeichen.create('a'));
		expectedBand.addZeichen(NormalesZeichen.create('b'));
		expectedBand.addZeichen(NormalesZeichen.create('c'));
		assertEquals(expectedBand, actualBand);
	}
}
