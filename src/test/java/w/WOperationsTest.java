package w;

import org.junit.Test;
import turingmaschine.TuringMaschineMitBand;
import turingmaschine.TuringMaschinen;
import turingmaschine.band.ChangeableBand;

import static org.junit.Assert.assertEquals;

public class WOperationsTest {


    @Test
    public void testMultiply() {
        final ChangeableBand f1 = ChangeableBand.create("5");
        final ChangeableBand f2 = ChangeableBand.create("3");
        final ChangeableBand f11 = ChangeableBand.create("0");
        final ChangeableBand f21 = ChangeableBand.create("0");

        final TuringMaschineMitBand c1 = TuringMaschinen.createCopy(f1, f11);
        final TuringMaschineMitBand c2 = TuringMaschinen.createCopy(f2, f21);
        final TuringMaschineMitBand add1 = TuringMaschinen.createAdd(f1, f11, f1);
        final TuringMaschineMitBand decrement = TuringMaschinen.createSub(f21, ChangeableBand.create("1"), f21);
        final TuringMaschineMitBand w = TuringMaschinen.createWhile(f21,TuringMaschinen.createSeq(add1, decrement));
        TuringMaschinen.createSeq(c1, TuringMaschinen.createSeq(c2, w)).simuliere();

        assertEquals("15", f1.toString());
    }

    @Test
    public void oneMultiplikation() {
        final ChangeableBand f1 = ChangeableBand.create("5");
        final ChangeableBand f11 = ChangeableBand.create("2");
        final ChangeableBand f21 = ChangeableBand.create("3");

        TuringMaschinen.createWhile(f21, TuringMaschinen.createSeq(TuringMaschinen.createAdd(f1, f11, f1),
                TuringMaschinen.createSub(f21, ChangeableBand.create("1"), f21)))
                .simuliere();

        assertEquals("11", f1.toString());
        assertEquals("0", f21.toString());
    }

    @Test
    public void testAddierer() {
        final ChangeableBand result = ChangeableBand.create();
        TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
        assertEquals("8", result.toString());

    }

    @Test
    public void testSubtrahierer() {
        final ChangeableBand ret = ChangeableBand.create();
        final TuringMaschineMitBand subtract3From5 = TuringMaschinen.createSub(ChangeableBand.create("389"), ChangeableBand.create("211"), ret);
        subtract3From5.simuliere();
        assertEquals("178", ret.toString());

        final ChangeableBand xMinus7 = ChangeableBand.create("17");
        TuringMaschinen.createSub(xMinus7, ChangeableBand.create("7"), xMinus7).simuliere();
        assertEquals("10", xMinus7.toString());
    }

    @Test
    public void thereIsNoProblem() {
        final ChangeableBand z = ChangeableBand.create("JJTT sind alles Spacken");
        TuringMaschinen.createSub(ChangeableBand.create("17"), ChangeableBand.create("5"), z).simuliere();
        assertEquals("12", z.toString());
    }

    @Test
    public void testWhile() {
        final ChangeableBand x = ChangeableBand.create("5");
        final ChangeableBand y = ChangeableBand.create("0");
        final TuringMaschineMitBand decrementX = TuringMaschinen.createDecrement(x);
        final TuringMaschineMitBand whileYNicht0Decrement = TuringMaschinen.createWhile(y, decrementX);

        whileYNicht0Decrement.simuliere();

        assertEquals("0", y.toString());
        assertEquals("5", x.toString());
    }

    @Test
    public void testAddiererMaschineMitRecycletemBand() {
        final ChangeableBand result = ChangeableBand.create();
        final TuringMaschineMitBand m1 = TuringMaschinen.createAdd(ChangeableBand.create("5"),
                ChangeableBand.create("3"), result);
        final TuringMaschineMitBand m2 = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
        TuringMaschinen.createSeq(m1, m2).simuliere();
        assertEquals("13", result.toString());
    }

    @Test
    public void testAddiererMaschineMitRecycletemBandEinfach() {
        final ChangeableBand result = ChangeableBand.create("1");
        final TuringMaschineMitBand m = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
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
        final TuringMaschineMitBand xMachine = TuringMaschinen.createAdd(ChangeableBand.create("288"),
                ChangeableBand.create("88"), x);
        final TuringMaschineMitBand yMachine = TuringMaschinen.createAdd(x, ChangeableBand.create("888"), y);
        final TuringMaschineMitBand seqMachine = TuringMaschinen.createSeq(xMachine, yMachine);
        seqMachine.simuliere();
        assertEquals("376", x.toString());
        assertEquals("1264", y.toString());
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

        final ChangeableBand z = ChangeableBand.create();
        final TuringMaschineMitBand zGleichY = TuringMaschinen.createCopy(y, z);
        x.wipe();
        y.wipe();
        final TuringMaschineMitBand zGleichYGleichXGleich5 = TuringMaschinen.createSeq(yGleichXGleich5, zGleichY);
        zGleichYGleichXGleich5.simuliere();

        assertEquals("5", x.toString());
        assertEquals("5", y.toString());
        assertEquals("5", z.toString());

    }
}
