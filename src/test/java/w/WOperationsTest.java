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

    /**
     * main() { x = 5 + 3 }
     */
    @Test
    public void testAddiererMaschineMitRecycletemBand() {
        final ChangeableBand result = ChangeableBand.create();
        TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
        TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result).simuliere();
        assertEquals("13", result.toString());

        // x = TuringMaschinen.createAdd().simuliereDeterministisch("5", "3",
        // "").getLetztesBand().toString();
        // x = TuringMaschinen.createAdd().simuliereDeterministisch("7", "3",
        // "").getLetztesBand().toString();
        // System.out.println(x);
    }

    @Test
    public void testSequenceAdd() {
        final ChangeableBand x = ChangeableBand.create();
        final ChangeableBand y = ChangeableBand.create();
        final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), x);
        final TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("5"), y);
        final TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
        seqMachine.simuliere();
        assertEquals("8", x.toString());
        assertEquals("10", y.toString());
    }

    @Test
    public void testSequenceAddReusingVars() {
        final ChangeableBand x = ChangeableBand.create();
        final ChangeableBand y = ChangeableBand.create();
        final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), x);
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
