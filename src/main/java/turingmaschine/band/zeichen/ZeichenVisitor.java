package turingmaschine.band.zeichen;

/**
 * Typischer Vistor nach dem Visitor-Pattern für Zeichen.
 * 
 * @param <T>
 *            Typisierung für den RückgabeTyp der handle-Operationen des Visitors.
 */
public interface ZeichenVisitor<T> {
	
	T handle(NormalesZeichen normalesZeichen);
	T handle(BeliebigesZeichen beliebigesZeichen);
	T handle(Blank blank);
	T handle(BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank);
}
