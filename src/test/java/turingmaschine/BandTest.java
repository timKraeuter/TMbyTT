package turingmaschine;

import org.junit.Test;
import turingmaschine.band.ImmutableBand;
import turingmaschine.band.zeichen.Zeichen;

import static org.junit.Assert.assertEquals;

public class BandTest {
	
	@Test
	public void testEmptyBandCreation() {
		final ImmutableBand actualBand = ImmutableBand.create("");
		final ImmutableBand expectedBand = ImmutableBand.create();
		assertEquals(expectedBand, actualBand);
	}
	
	@Test
	public void testBandCreation() {
		final ImmutableBand actualBand = ImmutableBand.create("abc");
		final ImmutableBand expectedBand = ImmutableBand.create();
		expectedBand.addInitialeZeichen(Zeichen.create('a'));
		expectedBand.addInitialeZeichen(Zeichen.create('b'));
		expectedBand.addInitialeZeichen(Zeichen.create('c'));
		assertEquals(expectedBand, actualBand);
	}
}
