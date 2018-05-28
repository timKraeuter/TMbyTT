package turingmaschine;

import persistenz.TMPersistierer;
import turingmaschine.band.Band;

import java.io.File;

public class TuringMaschinen {
    /**
     * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder 1 und 2
     * addiert und dabei das Ergebnis in das Band 3 schreibt.
     *
     * @return Additions-Turingmaschine für Dezimalzahlen.
     */
    public static TuringMaschine createAdd() {
        return (TuringMaschine) TMPersistierer.getInstance().lade(new File("src/main/resources/turingmaschinen/decimalAdditionTM.xml"));
    }

    /**
     * Erstellt eine Turingmaschine mit 3 Bändern, welche bei Ausführung die Bänder 1 und 2
     * subtrahiert und dabei das Ergebnis in das Band 3 schreibt.
     *
     * @return Subtraktions-Turingmaschine für Dezimalzahlen.
     */
    public static TuringMaschine createSub(final Band sub, final Band min, final Band result) {
        // TODO Maschine erstellen testen und hier laden.
        return null;
    }

    /**
     * Erstellt eine Turingmaschine mit 2 Bändern, welche bei Ausführung den Inhalte des
     * 1 Bandes auf das 2 Band schreibt.
     *
     * @return Kopier-Turingmaschine.
     */
    public static TuringMaschine createCopy() {
        return (TuringMaschine) TMPersistierer.getInstance().lade(new File("src/main/resources/turingmaschinen/copyTM.xml"));
    }

    /**
     * Erstellt eine Turingmaschine, welche die TuringMaschine tm solang ausführt,
     * bis das Condition-Band den Wert 0 hat.
     *
     * @param condition zu überprüfende Condition. tm wird solang wiederholt ausgeführt,
     *                  bis das Condition-Band den Wert 0 hat.
     * @param tm        wiederholt auszuführende Turingmaschine.
     * @return While-Turingmaschine.
     */
    public static TuringMaschine createWhile(final Band condition, final TuringMaschine tm) {
        // TODO noch nicht angeguckt
        return null;
    }

    /**
     * Erstellt eine Turingmaschine, welche die TuringMaschine t1 und t2
     * hintereinander ausführt.
     *
     * @param t1 erste auszuführende Turingmaschine.
     * @param t2 zweite auszuführende Turingmaschine.
     * @return Sequenz-Turingmaschine.
     */
    public static TuringMaschine createSeq(final TuringMaschine t1, final TuringMaschine t2) {
        return t1.sequence(t2);

    }

}
