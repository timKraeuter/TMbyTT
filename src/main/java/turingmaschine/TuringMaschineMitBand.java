package turingmaschine;

import turingmaschine.band.Band;
import turingmaschine.band.ChangeableBand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TuringMaschineMitBand {

	private final TuringMaschine maschine;
	private final List<ChangeableBand> baender;

	private TuringMaschineMitBand(final TuringMaschine machine, final List<ChangeableBand> baender) {
		this.maschine = machine;
		this.baender = baender;

	}

	public static TuringMaschineMitBand create(final TuringMaschine machine, final ChangeableBand... bands) {
		return new TuringMaschineMitBand(machine, Arrays.asList(bands));
	}

    public static TuringMaschineMitBand create(final TuringMaschine machine, final List<ChangeableBand> baender) {
        return new TuringMaschineMitBand(machine, baender);
    }

	public void simuliere() {
        final List<String> eingaben = this.baender.stream().map(ChangeableBand::toString).collect(Collectors.toList());
        final List<Band> baender = this.maschine.simuliereDeterministisch(
                eingaben).getBaender();

		IntStream.range(0, baender.size()).forEach(i -> this.baender.get(i).update(baender.get(i)));
	}

    public TuringMaschineMitBand sequence(final TuringMaschineMitBand turingMaschineMitBand) {
        final ArrayList<ChangeableBand> changeableBaender = new ArrayList<>(this.baender);
        changeableBaender.addAll(turingMaschineMitBand.baender);
        return TuringMaschineMitBand.create(this.maschine.sequence(turingMaschineMitBand.maschine),changeableBaender);
    }
}
