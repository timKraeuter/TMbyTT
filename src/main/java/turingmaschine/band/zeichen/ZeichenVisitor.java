package turingmaschine.band.zeichen;

public interface ZeichenVisitor<T> {

    T handle(NormalesZeichen normalesZeichen);
    T handle(BeliebigesZeichen beliebigesZeichen);
    T handle(Blank blank);
    T handle(BeliebigesZeichenOhneBlank beliebigesZeichenOhneBlank);
}
