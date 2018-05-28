package turingmaschine.band.zeichen;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Objects;

public class Blank implements Zeichen{
	private static Blank instance;
	
	private Blank() {
	}
	
	public static Blank getInstance() {
		if(Objects.isNull(Blank.instance)) {
            Blank.instance = new Blank();
		}
		return Blank.instance;
	}
	
	@Override
	public Character getZeichen() {
		return Zeichen.BLANK;
	}

    @Override
    public <T> T accept(final ZeichenVisitor<T> visitor) {
        return visitor.handle(this);
    }

    @Override
    public boolean matches(final Zeichen zeichen) {
        return zeichen.accept(new ZeichenVisitor<Boolean>() {
            @Override
            public Boolean handle(final NormalesZeichen normalesZeichen) {
                return false;
            }

            @Override
            public Boolean handle(final BeliebigesZeichen beliebigesZeichen) {
                return true;
            }

            @Override
            public Boolean handle(final Blank blank) {
                return true;
            }
        });
    }

    /**
     * @return Liefert den First-Konvertierer, welcher benötigt wird, damit ein First-Objekt persistiert werden kann.
     *         Dieses Objekt muss dem TMPersistierer vor dem Speichern/ Laden mittels
     *         {@code TMPersistierer.addKonvertierer(First.getKonvertierer()) übergeben werden.
     */
    public static Converter getKonvertierer() {
        return new BlankKonvertierer();
    }

    /**
     * Eine private Klasse, die es dem Singleton ermöglicht persistiert zu werden.
     */
    private static class BlankKonvertierer implements Converter {
        @SuppressWarnings("rawtypes")
        @Override
        public boolean canConvert(final Class clazz) {
            return clazz.equals(Blank.class);
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
            return Blank.getInstance();
        }
    }
}
