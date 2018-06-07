package features;

public class UeberfuehrungsDAO {
	
	private String vonZustand;
	private String zuZustand;
	private String eingabeZeichen;
	private String zuSchreibendeZeichen;
	private String schreibLesekopfBewegungen;
	
	public String getVonZustand() {
		return this.vonZustand;
	}
	
	public String getZuZustand() {
		return this.zuZustand;
	}
	
	public String getEingabeZeichen() {
		return this.eingabeZeichen;
	}
	
	public String getZuSchreibendeZeichen() {
		return this.zuSchreibendeZeichen;
	}
	
	public String getSchreibLesekopfBewegungen() {
		return this.schreibLesekopfBewegungen;
	}
}
