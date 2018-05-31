package persistenz;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

import turingmaschine.TuringMaschine;
import turingmaschine.band.zeichen.BeliebigesZeichen;
import turingmaschine.band.zeichen.BeliebigesZeichenOhneBlank;
import turingmaschine.band.zeichen.Blank;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

public class TMPersistierer {
	
	private static TMPersistierer instance;
	/**
	 * Das XStream-Objekt, welches für das (De-)Serialisieren zuständig ist. Es verwaltet ebenso alle weiteren relevanten Einstellungen (Security,
	 * verarbeitung von Annotations, CustomConverters, Referenzmodi).
	 */
	private final XStream xBach;
	
	private TMPersistierer() {
		this.xBach = new XStream() {
			{
				XStream.setupDefaultSecurity(this);
				this.allowTypesByWildcard(new String[] { "turingmaschine.**", "persistenz.**", });
				this.setMode(XStream.ID_REFERENCES);
			}
		};
	}
	
	public static TMPersistierer getInstance() {
		if (TMPersistierer.instance == null) {
            TMPersistierer.instance = new TMPersistierer();
            TMPersistierer.instance.addKonvertierer(Blank.getKonvertierer());
            TMPersistierer.instance.addKonvertierer(BeliebigesZeichen.getKonvertierer());
            TMPersistierer.instance.addKonvertierer(BeliebigesZeichenOhneBlank.getKonvertierer());
		}
		return TMPersistierer.instance;
	}
	
	/**
	 * Erstellt ein gültiges XML, welches wieder zu einem Objekt geparst werden kann.
	 *
	 * @param obj
	 *            Objekt, welches gespeichert werden soll.
	 * @return Liefert einen gültigen String, welcher {@code obj} repräsentiert
	 */
	public String zuXML(final Object obj) {
		return this.xBach.toXML(obj);
	}
	
	/**
	 * Erstellt ein gültiges XML, welches wieder zu einem Objekt geparst werden kann. Dieser String wird direkt in {@code location} gespeichert.
	 *
	 * @param obj
	 *            Objekt, welches gespeichert werden soll.
	 * @param location
	 *            Dateipfad, in welchem die Datei gespeichert werden soll.<br>
	 *            Beispiel: "./src/test/resources/dateiname.xml"
	 * @return Liefert einen gültigen String, welcher {@code obj} repräsentiert
	 * @throws IOException
	 *             wenn ein Fehler beim schreiben der Datei auftritt.
	 */
	public String persistiere(final Object obj, final File location) throws IOException {
		final String objectXML = TMPersistierer.instance.xBach.toXML(obj);
		Files.write(location.toPath(), objectXML.getBytes());
		return objectXML;
	}
	
	/**
	 * Zaubert aus einem Objekt-XML String ein Objekt.
	 *
	 * @param xml
	 *            Ein String, welcher ein Objekt beinhaltet.
	 * @return Liefert das geparste Objekt aus {@code xml}.
	 */
	public Object vonXML(final String xml) {
		return TMPersistierer.instance.xBach.fromXML(xml);
	}
	
	/**
	 * Zaubert aus einer Datei, welche ein Objekt-XML enthält, ein Objekt.
	 *
	 * @param xml
	 *            File mit dem Pfad zu einer XML-Datei.<br>
	 *            Beispiel: new File("./src/test/resources/dateiname.xml")
	 * @return Liefert das geparste Objekt aus {@code xml}.
	 */
	public Object lade(final File xml) {
		return TMPersistierer.instance.xBach.fromXML(xml);
	}

	public Object lade(URL resource) {
		return TMPersistierer.instance.xBach.fromXML(resource);
	}
	
	/**
	 * @param konvertierer
	 *            Array der Konvertierer, welche Sonderhandling für das Speichern/ Laden bestimmter Objekte (wie bspw. Singletons) übernehmen. Die
	 *            hier übergebenen Konvertierer bleiben permanent im TMPersistierer enthalten.
	 */
	public void addKonvertierer(final Converter... konvertierer) {
		Arrays.stream(konvertierer).forEach(TMPersistierer.instance.xBach::registerConverter);
	}
	
	/**
	 * @param klassen
	 *            Klassen, zu welchen Annotationen wie bspw {@code @XStreamAlias("lulu")} verarbeitet werden sollen. Diese müssen bereits vor dem
	 *            Speichern/ Laden gesetzt werden.
	 */
	public void verarbeiteAnnotationen(final Class<?>... klassen) {
        TMPersistierer.instance.xBach.processAnnotations(klassen);
	}

	
}
