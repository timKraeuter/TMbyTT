package turingmaschine.band.zeichen;

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
		return BLANK;
	}
}
