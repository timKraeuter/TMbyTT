package feature;

import com.google.common.base.Splitter;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import turingmaschine.ElementDerUeberfuehrungsfunktion;
import turingmaschine.Lesekopfbewegung;
import turingmaschine.TuringMaschine;
import turingmaschine.TuringMaschinenBuilder;
import turingmaschine.Zeichen;
import turingmaschine.Zustand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class TMStepdefs {
	
	private static final char SEMIKOLOn = ';';
	private Map<String, TuringMaschinenBuilder> turingMaschinen;
	private Map<String, Zustand> zustaende;
	
	@Before
	public void beforeScenario() {
		this.turingMaschinen = new HashMap<>();
		this.zustaende = new HashMap<>();
	}
	
	@Given("eine TM mit dem Namen (.+) und (\\d+) Bändern")
	public void eineTuringmaschineMitDemNamen(final String nameDerTM, final int anzahlDerBaender) {
		this.turingMaschinen.put(nameDerTM, TuringMaschine.builder().anzahlDerBaender(anzahlDerBaender));
	}
	
	@Given("die TM mit dem Namen (.+) hat den Startzustand (.+)")
	public void dieTuringmaschineMitDemNamenHatDenStartzustand(final String nameDerTM, final String nameDesStartzustandes) {
		final Zustand startZustand = Zustand.create(nameDesStartzustandes);
		this.zustaende.put(nameDesStartzustandes, startZustand);
		this.getTM(nameDerTM).startZustand(startZustand);
	}
	
	@Given("die TM mit dem Namen (.+) hat den Endzustand (.+)")
	public void dieTuringmaschineMitDemNamenHatDenEndzustand(final String nameDerTM, final String nameEinesEndzustands) {
		final Zustand endZustand = this.getOrCreateZustand(nameEinesEndzustands);
		this.getTM(nameDerTM).addEndZustand(endZustand);
	}
	
	@Given("die TM mit dem Namen (.+) hat die Überführungsfunktion:")
	public void dieTuringmaschineMitDemNamenHatDieUeberfuehrungsfunktion(final String nameDerTM, final List<UeberfuehrungsDAO> ueberfuehrungen) {
		this.getTM(nameDerTM).ueberfuehrungsfunktion(this.parseUeberfuhrungsfunktion(ueberfuehrungen));
	}
	
	private TuringMaschinenBuilder getTM(final String nameDerTM) {
		final TuringMaschinenBuilder turingMaschinenBuilder = this.turingMaschinen.get(nameDerTM);
		if (Objects.isNull(turingMaschinenBuilder)) {
			throw new RuntimeException(String.format("Turingmaschine mit dem Namen %s nicht gefunden", nameDerTM));
		}
		return turingMaschinenBuilder;
	}
	
	private Zustand getOrCreateZustand(final String nameEinesZustands) {
		final Zustand gefundenerZustand = this.zustaende.get(nameEinesZustands);
		if (Objects.isNull(gefundenerZustand)) {
			final Zustand neuerZustand = Zustand.create(nameEinesZustands);
			this.zustaende.put(nameEinesZustands, neuerZustand);
			return neuerZustand;
		}
		return gefundenerZustand;
	}
	
	private Set<ElementDerUeberfuehrungsfunktion> parseUeberfuhrungsfunktion(final List<UeberfuehrungsDAO> ueberfuehrungen) {
		return ueberfuehrungen.stream().map(this::parseUeberfuhrung).collect(Collectors.toSet());
	}
	
	private ElementDerUeberfuehrungsfunktion parseUeberfuhrung(final UeberfuehrungsDAO ueberfuehrungsDAO) {
		final Zustand vonZustand = this.getOrCreateZustand(ueberfuehrungsDAO.getVonZustand());
		final Zustand zuZustand = this.getOrCreateZustand(ueberfuehrungsDAO.getZuZustand());
		
		// TODO aufpassen charAT 0
		final List<Zeichen> eingabe =
				this.splitSemikolon(ueberfuehrungsDAO.getEingabeZeichen()).stream().map(string -> Zeichen.create(string.charAt(0)))
				.collect(Collectors.toList());
		
		final List<Zeichen> zuSchreibendeZeichen = this.splitSemikolon(ueberfuehrungsDAO.getZuSchreibendeZeichen()).stream()
				.map(string -> Zeichen.create(string.charAt(0))).collect(Collectors.toList());
		
		final List<Lesekopfbewegung> lesekopfbewegungen = this.splitSemikolon(ueberfuehrungsDAO.getSchreibLesekopfBewegungen()).stream()
				.map(Lesekopfbewegung::valueOf).collect(Collectors.toList());
		
		return ElementDerUeberfuehrungsfunktion.create(vonZustand, zuZustand, eingabe, zuSchreibendeZeichen, lesekopfbewegungen);
	}
	
	@Then("bei Eingabe von (.+) bei der TM (.+) enthält das Band (.+)")
	public void dasBandEnthaeltBeiEingabeVon(final String eingabe, final String nameDerTM, final String bandInhalt) {
		assertTrue(
				this.getTM(nameDerTM).build().simuliere(Collections.singletonList(eingabe)).stream()
				.anyMatch(konfiguration -> konfiguration.bandContains(bandInhalt, 0)));
	}
	
	@Then("die TM (.+) erkennt die Wörter:")
	public void dieTuringmaschineErkenntDieWoerter(final String nameDerTM, final List<String> eingaben) {
		final TuringMaschine tm = this.getTM(nameDerTM).build();
		assertTrue(eingaben.stream().allMatch(tm::erkenntEingabe));
	}
	
	@Then("die TM (.+) erkennt nicht die Wörter:")
	public void dieTMErkenntNichtDieWoerter(final String nameDerTM, final List<String> eingaben) {
	    final TuringMaschine tm = this.getTM(nameDerTM).build();
	    assertTrue(eingaben.stream().noneMatch(tm::erkenntEingabe));
	}
	
	@Then("die TM mit dem Namen (.+) hat bei folgender Eingabe die folgende Ausgabe auf Band (\\d+):")
	public void dieTMMitDemNamenHatBeiFolgenderEingabeDieFolgendeAusgabeAufBand(final String nameDerTM,
			final int nummerDesAusgabeBandes,
			final List<EingabeAusgabeDAO> eingabenAusgaben) {
		final TuringMaschine tm = this.getTM(nameDerTM).build();
        /*for (EingabeAusgabeDAO eingabe : eingabenAusgaben) {
            final List<String> eingaben = new ArrayList<>(this.splitSemikolon(eingabe.getEingabe()));
            System.out.println(eingabe.getEingabe());
            final Set<Konfiguration> simuliere = tm.simuliere(eingaben);
            System.out.println(simuliere);
        }*/
		assertTrue(
				eingabenAusgaben.stream()
				.allMatch(eingabeAusgabe -> this.zurEingabeGibtEsPassendeAusgabe(tm, eingabeAusgabe, nummerDesAusgabeBandes)));
	}
	
	private boolean zurEingabeGibtEsPassendeAusgabe(final TuringMaschine tm,
			final EingabeAusgabeDAO eingabeAusgabe,
			final int nummerDesAusgabeBandes) {
		return tm.simuliere(this.splitSemikolon(eingabeAusgabe.getEingabe())).stream()
				.anyMatch(config -> config.bandContains(eingabeAusgabe.getAusgabe(), nummerDesAusgabeBandes));
	}
	
	private List<String> splitSemikolon(final String eingabe) {
		final List<String> result = new ArrayList<>();
		Splitter.on(SEMIKOLOn).split(eingabe).forEach(result::add);
		return result;
	}
}
