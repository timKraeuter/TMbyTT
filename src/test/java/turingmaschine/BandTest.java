package turingmaschine;

import org.junit.Test;
import turingmaschine.band.Band;
import turingmaschine.band.zeichen.Zeichen;

import static org.junit.Assert.assertEquals;

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
		expectedBand.addZeichen(Zeichen.create('a'));
		expectedBand.addZeichen(Zeichen.create('b'));
		expectedBand.addZeichen(Zeichen.create('c'));
		assertEquals(expectedBand, actualBand);
	}
}
