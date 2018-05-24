package feature;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import turingmaschine.ElementDerUeberfuehrungsfunktion;
import turingmaschine.Konfiguration;
import turingmaschine.Lesekopfbewegung;
import turingmaschine.TuringMaschinenBuilder;
import turingmaschine.Zeichen;
import turingmaschine.Zustand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TMStepdefs {

    private Map<String, TuringMaschinenBuilder> turingMaschinen;
    private Map<String, Zustand> zustaende;
    private Map<String, Set<Konfiguration>> ergebnisseBeiWorteingabe;


    @Before
    public void beforeScenario() {
        turingMaschinen = new HashMap<>();
        zustaende = new HashMap<>();
        ergebnisseBeiWorteingabe = new HashMap<>();
    }

    @Given("eine Turingmaschine mit dem Namen (.+)")
    public void eineTuringmaschineMitDemNamen(String nameDerTM) {
        turingMaschinen.put(nameDerTM, TuringMaschinenBuilder.create());
    }

    @Given("die Turingmaschine mit dem Namen (.+) hat den Startzustand (.+)")
    public void dieTuringmaschineMitDemNamenHatDenStartzustand(String nameDerTM, String nameDesStartzustandes){
        final Zustand startZustand = Zustand.create(nameDesStartzustandes);
        zustaende.put(nameDesStartzustandes, startZustand);
        this.getTM(nameDerTM).startZustand(startZustand);
    }

    @Given("die Turingmaschine mit dem Namen (.+) hat den Endzustand (.+)")
    public void dieTuringmaschineMitDemNamenHatDenEndzustand(String nameDerTM, String nameEinesEndzustands) {
        final Zustand endZustand = getOrCreateZustand(nameEinesEndzustands);
        this.getTM(nameDerTM).addEndZustand(endZustand);
    }

    @Given("die Turingmaschine mit dem Namen (.+) hat die Überführungsfunktion:")
    public void dieTuringmaschineMitDemNamenHatDieUeberfuehrungsfunktion(String nameDerTM, List<UeberfuehrungsDAO> ueberfuehrungen) {
        this.getTM(nameDerTM).ueberfuehrungsfunktion(this.parseUeberfuhrungsfunktion(ueberfuehrungen));
    }

    private TuringMaschinenBuilder getTM(String nameDerTM) {
        final TuringMaschinenBuilder turingMaschinenBuilder = this.turingMaschinen.get(nameDerTM);
        if (Objects.isNull(turingMaschinenBuilder)) {
            throw new RuntimeException(String.format("Turingmaschine mit dem Namen %s nicht gefunden", nameDerTM));
        }
        return turingMaschinenBuilder;
    }


    private Zustand getOrCreateZustand(final String nameEinesZustands) {
        final Zustand gefundenerZustand = zustaende.get(nameEinesZustands);
        if (Objects.isNull(gefundenerZustand)) {
            final Zustand neuerZustand = Zustand.create(nameEinesZustands);
            zustaende.put(nameEinesZustands, neuerZustand);
            return neuerZustand;
        }
        return gefundenerZustand;
    }


    private Set<ElementDerUeberfuehrungsfunktion> parseUeberfuhrungsfunktion(final List<UeberfuehrungsDAO> ueberfuehrungen) {
        return ueberfuehrungen.stream()
                .map(this::parseUeberfuhrung)
                .collect(Collectors.toSet());
    }

    private ElementDerUeberfuehrungsfunktion parseUeberfuhrung(final UeberfuehrungsDAO ueberfuehrungsDAO) {
        final Zustand vonZustand = this.getOrCreateZustand(ueberfuehrungsDAO.getVonZustand());
        final Zustand zuZustand = this.getOrCreateZustand(ueberfuehrungsDAO.getZuZustand());
        final Zeichen eingabe = Zeichen.create(ueberfuehrungsDAO.getEingabeZeichen());
        final Zeichen zuSchreibendesZeichen = Zeichen.create(ueberfuehrungsDAO.getZuSchreibendeZeichen());
        final Lesekopfbewegung lesekopfbewegung = Lesekopfbewegung.valueOf(ueberfuehrungsDAO.getSchreibLesekopfBewegungen());

        return ElementDerUeberfuehrungsfunktion.create(vonZustand, zuZustand, eingabe, zuSchreibendesZeichen, lesekopfbewegung);
    }

    @When("das Wort (.+) in die Turingmaschine mit dem Namen (.+) eingegeben wird")
    public void dasWortInDieTuringmaschineMitDemNamenEingegebenWird(String eingabe, String nameDerTM) {
        this.ergebnisseBeiWorteingabe.put(eingabe, this.getTM(nameDerTM).build().simuliere(eingabe));
    }

    @Then("wurde das Wort (.+) erkannt")
    public void wurdeDasWortErkannt(String eingabe) {
        assertTrue(! this.ergebnisseBeiWorteingabe.get(eingabe).isEmpty());
    }

    @Then("das Band enthält bei Eingabe von (.+) (.+)")
    public void dasBandEnthaeltBeiEingabeVon(String eingabe, String bandInhalt) {
        assertTrue(this.ergebnisseBeiWorteingabe.get(eingabe).stream()
        .anyMatch(konfiguration -> konfiguration.bandContains(bandInhalt)));
    }

}
