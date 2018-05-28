package turingmaschine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import turingmaschine.band.Band;
import turingmaschine.band.ChangeableBand;

public class TuringMaschineMitBand {

	private TuringMaschine machine;
	private List<ChangeableBand> bands;

	public TuringMaschineMitBand(TuringMaschine machine, ChangeableBand[] bands) {
		this.machine = machine;
		this.bands = Arrays.asList(bands);

	}

	public static TuringMaschineMitBand create(TuringMaschine machine, ChangeableBand... bands) {
		return new TuringMaschineMitBand(machine, bands);
	}

	public void simuliere() {
		List<Band> baender = machine.simuliereDeterministisch(
				this.bands.stream().map(ChangeableBand::toString).collect(Collectors.toList())).getBaender();

		IntStream.range(0, baender.size()).forEach(i -> bands.get(i).update(baender.get(i)));
	}

}
