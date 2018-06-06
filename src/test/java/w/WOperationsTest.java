package w;

import org.junit.Test;
import turingmaschine.TuringMaschineMitBand;
import turingmaschine.TuringMaschinen;
import turingmaschine.band.ChangeableBand;

import static org.junit.Assert.assertEquals;

public class WOperationsTest {


    @Test
    public void testMultiply() {
        final long start = System.currentTimeMillis();
        for (int xi = 0; xi < 10; xi++) {
            for (int yi = 0; yi < 10; yi++) {
                final ChangeableBand f11 = ChangeableBand.create("0");
                final ChangeableBand f21 = ChangeableBand.create("0");
                final ChangeableBand f1 = ChangeableBand.create(Integer.toString(xi));
                final ChangeableBand f2 = ChangeableBand.create(Integer.toString(yi));
                TuringMaschinen
                        .createSeq(TuringMaschinen.copy(f1, f11), TuringMaschinen.createSeq(
                                TuringMaschinen.copy(ChangeableBand.create("0"), f1),
                                TuringMaschinen.createSeq(TuringMaschinen.copy(f2, f21),
                                        TuringMaschinen.createWhileNotEqual(f21,
                                                TuringMaschinen.createSeq(TuringMaschinen.createAdd(f1, f11, f1),
                                                        TuringMaschinen.createSub(f21, ChangeableBand.create("1"), f21))))))
                        .simuliere();
                assertEquals(Integer.toString(xi*yi), f1.getBandInhalt());
            }
        }
        System.out.println((System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void testKomplexeAddition() {
        final ChangeableBand x = ChangeableBand.create("5");
        TuringMaschinen.createAdd(x, x, x).simuliere();
        assertEquals("10", x.getBandInhalt());
    }

    @Test
    public void testKomplexeSubtraction() {
        final ChangeableBand x = ChangeableBand.create("5");
        TuringMaschinen.createSub(x, x, x).simuliere();
        assertEquals("0", x.getBandInhalt());
    }


    @Test
    public void testAddierer() {
        final ChangeableBand result = ChangeableBand.create();
        TuringMaschinen.createAdd(ChangeableBand.create("5"), ChangeableBand.create("3"), result).simuliere();
        assertEquals("8", result.getBandInhalt());

    }

    @Test
    public void testSubtrahierer() {
        final ChangeableBand ret = ChangeableBand.create();
        final TuringMaschineMitBand subtract3From5 = TuringMaschinen.createSub(ChangeableBand.create("389"),
                ChangeableBand.create("211"), ret);
        subtract3From5.simuliere();
        assertEquals("178", ret.getBandInhalt());

        final ChangeableBand xMinus7 = ChangeableBand.create("17");
        TuringMaschinen.createSub(xMinus7, ChangeableBand.create("7"), xMinus7).simuliere();
        assertEquals("10", xMinus7.getBandInhalt());
    }

    @Test
    public void thereIsNoProblem() {
        final ChangeableBand z = ChangeableBand.create("JJTT sind alles Spacken");
        TuringMaschinen.createSub(ChangeableBand.create("17"), ChangeableBand.create("5"), z).simuliere();
        assertEquals("12", z.getBandInhalt());
    }

    @Test
    public void testWhile() {
        final ChangeableBand x = ChangeableBand.create("5");
        final ChangeableBand y = ChangeableBand.create("0");
        final TuringMaschineMitBand decrementX = TuringMaschinen.createDecrement(x);
        final TuringMaschineMitBand whileYNicht0Decrement = TuringMaschinen.createWhileNotEqual(y, decrementX);

        whileYNicht0Decrement.simuliere();

        assertEquals("0", y.getBandInhalt());
        assertEquals("5", x.getBandInhalt());
    }

    @Test
    public void testAddiererMaschineMitRecycletemBand() {
        final ChangeableBand result = ChangeableBand.create();
        final TuringMaschineMitBand m1 = TuringMaschinen.createAdd(ChangeableBand.create("5"),
                ChangeableBand.create("3"), result);
        final TuringMaschineMitBand m2 = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
        TuringMaschinen.createSeq(m1, m2).simuliere();
        assertEquals("13", result.getBandInhalt());
    }

    @Test
    public void testAddiererMaschineMitRecycletemBandEinfach() {
        final ChangeableBand result = ChangeableBand.create("1");
        final TuringMaschineMitBand m = TuringMaschinen.createAdd(ChangeableBand.create("5"), result, result);
        m.simuliere();
        assertEquals("6", result.getBandInhalt());
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
        assertEquals("8", x.getBandInhalt());
        assertEquals("10", y.getBandInhalt());
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
        assertEquals("3", x.getBandInhalt());
        assertEquals("7", y.getBandInhalt());
        assertEquals("1", c1.getBandInhalt());
        assertEquals("2", c2.getBandInhalt());
        assertEquals("3", c3.getBandInhalt());
        assertEquals("4", c4.getBandInhalt());
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
        assertEquals("376", x.getBandInhalt());
        assertEquals("1264", y.getBandInhalt());
    }

    @Test
    public void testCopyAndCopySequence() {
        final ChangeableBand x = ChangeableBand.create();
        final ChangeableBand y = ChangeableBand.create();
        final TuringMaschineMitBand xGleich5 = TuringMaschinen.copy(ChangeableBand.create("5"), x);
        xGleich5.simuliere();
        assertEquals("5", x.getBandInhalt());
        final TuringMaschineMitBand yGleichX = TuringMaschinen.copy(x, y);
        yGleichX.simuliere();
        assertEquals("5", y.getBandInhalt());

        x.wipe();
        y.wipe();
        final TuringMaschineMitBand yGleichXGleich5 = TuringMaschinen.createSeq(xGleich5, yGleichX);
        yGleichXGleich5.simuliere();
        assertEquals("5", x.getBandInhalt());
        assertEquals("5", y.getBandInhalt());

        final ChangeableBand z = ChangeableBand.create();
        final TuringMaschineMitBand zGleichY = TuringMaschinen.copy(y, z);
        x.wipe();
        y.wipe();
        final TuringMaschineMitBand zGleichYGleichXGleich5 = TuringMaschinen.createSeq(yGleichXGleich5, zGleichY);
        zGleichYGleichXGleich5.simuliere();

        assertEquals("5", x.getBandInhalt());
        assertEquals("5", y.getBandInhalt());
        assertEquals("5", z.getBandInhalt());

    }

    @Test
    public void testXYSub() {
        for (int xi = 0; xi <= 10; xi++) {
            for (int yi = xi; yi <= 100; yi++) {
                final ChangeableBand x = ChangeableBand.create(Integer.toString(xi));
                final ChangeableBand y = ChangeableBand.create(Integer.toString(yi));
                TuringMaschinen.createSub(y, x, x).simuliere();
                assertEquals(yi + " - " + xi, Integer.valueOf(yi - xi), Integer.valueOf(x.getBandInhalt()));
            }
        }
    }

    @Test
    public void testXXSub() {
        for (int xi = 0; xi <= 100; xi++) {
            final ChangeableBand x = ChangeableBand.create(Integer.toString(xi));
            TuringMaschinen.createSub(x, x, x).simuliere();
            assertEquals(xi + " - " + xi, Integer.valueOf(0), Integer.valueOf(x.getBandInhalt()));
        }
    }

    @Test
    public void testXAdd() {
        for (int xi = 0; xi < 10; xi++) {
            for (int yi = 0; yi < 100; yi++) {
                final ChangeableBand x = ChangeableBand.create(Integer.toString(xi));
                final ChangeableBand y = ChangeableBand.create(Integer.toString(yi));
                TuringMaschinen.createAdd(x, y, x).simuliere();
                assertEquals(xi + " + " + yi, String.valueOf(xi + yi), x.getBandInhalt());
            }
        }
    }
}
