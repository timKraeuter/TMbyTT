package turingmaschine;

import java.util.Objects;

public class Blank implements Zeichen{
	private static Blank instance;
	
	private Blank() {
	}
	
	public static Zeichen getInstance() {
		if(Objects.isNull(instance)) {
			instance = new Blank();
		}
		return instance;
	}
	
	@Override
	public Character getZeichen() {
		// TODO: Wir brauchen eine char-Repräsentation denke ich: □
		return null;
	}
}
