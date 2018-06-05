package turingmaschine.band.zeichen;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Objects;

/**
 * Repräsentation für ein beliebiges Zeichen ohne den Blank im Kontext von Turing-Maschinen.
 */
public class BeliebigesZeichenOhneBlank implements Zeichen{
	private static BeliebigesZeichenOhneBlank instance;
	
	private BeliebigesZeichenOhneBlank() {
	}
	
	public static BeliebigesZeichenOhneBlank getInstance() {
		if(Objects.isNull(BeliebigesZeichenOhneBlank.instance)) {
			BeliebigesZeichenOhneBlank.instance = new BeliebigesZeichenOhneBlank();
		}
		return BeliebigesZeichenOhneBlank.instance;
	}
	
	@Override
	public Character getZeichen() {
		return Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK;
	}
	
	@Override
	public <T> T accept(final ZeichenVisitor<T> visitor) {
		return visitor.handle(this);
	}
	
	@Override
	public boolean matches(final Zeichen zeichen) {
		return !zeichen.equals(Blank.getInstance());
	}
	
	/**
	 * @return Liefert den First-Konvertierer, welcher benötigt wird, damit ein First-Objekt persistiert werden kann.
	 *         Dieses Objekt muss dem TMPersistierer vor dem Speichern/ Laden mittels
	 *         {@code TMPersistierer.addKonvertierer(First.getKonvertierer()) übergeben werden.
	 */
	public static Converter getKonvertierer() {
		return new BeliebigesZeichenOhneBlankKonvertierer();
	}
	
	/**
	 * Eine private Klasse, die es dem Singleton ermöglicht persistiert zu werden.
	 */
	private static class BeliebigesZeichenOhneBlankKonvertierer implements Converter {
		@SuppressWarnings("rawtypes")
		@Override
		public boolean canConvert(final Class clazz) {
			return clazz.equals(BeliebigesZeichenOhneBlank.class);
		}
		
		@Override
		public void marshal(final Object value,
				final HierarchicalStreamWriter writer,
				final MarshallingContext context) {
			// Kann für ein simples Singleton ohne weitere Attribute leer gelassen werden.
			// Müssen Attribute jedoch mitgespeichert werden, ist dieser (sowie die unmarshal-Methode) Code etwa nach
			// folgendem Vorbild zu erweitern: http://x-stream.github.io/converter-tutorial.html
		}
		
		@Override
		public Object unmarshal(final HierarchicalStreamReader writer, final UnmarshallingContext context) {
			return BeliebigesZeichenOhneBlank.getInstance();
		}
	}
	
	@Override
	public String toString() {
		return String.valueOf(Zeichen.BELIEBIGES_ZEICHEN_OHNE_BLANK);
	}
}
