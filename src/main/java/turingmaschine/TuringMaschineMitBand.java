package turingmaschine;

import turingmaschine.band.Band;
import turingmaschine.band.ChangeableBand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuringMaschineMitBand {

	private final TuringMaschine maschine;
	private final List<Band> baender;

	private TuringMaschineMitBand(final TuringMaschine machine, final List<Band> baender) {
		this.maschine = machine;
		this.baender = baender;

	}

	public static TuringMaschineMitBand create(final TuringMaschine machine, final ChangeableBand... bands) {
		return new TuringMaschineMitBand(machine, Arrays.asList(bands));
	}

    public static TuringMaschineMitBand create(final TuringMaschine machine, final List<Band> baender) {
        return new TuringMaschineMitBand(machine, baender);
    }

	public void simuliere() {
        this.maschine.simuliereDeterministisch(this.baender);
	}

    public TuringMaschineMitBand sequence(final TuringMaschineMitBand turingMaschineMitBand) {
        final List<Band> changeableBaender = new ArrayList<>(this.baender);
        changeableBaender.addAll(turingMaschineMitBand.baender);
        return TuringMaschineMitBand.create(this.maschine.sequence(turingMaschineMitBand.maschine),changeableBaender);
    }
}
