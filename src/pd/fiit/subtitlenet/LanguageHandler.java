package pd.fiit.subtitlenet;

public final class LanguageHandler {
	private int selectedIndex;
	
	private String[] languageNames = 
			{"ALL", "SK | CZ", "SK | CZ | EN", "Albanian", "Arabic", "Armenian", "Bengali", "Bosnian",
			"Bulgarian", "Catalan", "Chinese", "Croatian", "Czech", "Danish", "Dutch", "English", 
			"Esperanto", "Estonian", "Farsi", "Finnish", "French", "Galician", "Georgian", "German",
			"Greek", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Italian", "Japanese",
			"Kazakh", "Korean", "Latvian", "Lithuanian", "Luxembourgish", "Macedonian", "Malay", "Norwegian",
			"Occitan", "Polish", "Portugese", "Portugese-BR", "Romanian", "Russian", "Serbian", "Sinhalese", 
			"Slovak", "Slovenian", "Spanish", "Swedish", "Syrian", "Tagalog", "Thai", "Turkish", 
			"Ukrainan", "Urdu", "Vietnamese"};
	
	private String[] languageIds = 
			{"all", "slo,cze", "slo,cze,eng", "alb", "ara", "arm", "ben", "bos", 
			"bul", "cat", "chi", "hrv", "cze", "dan", "dut", "eng", 
			"epo", "est", "per", "fin", "fre", "glg", "geo", "ger",
			"ell", "heb", "hin", "hun", "ice", "ind", "ita", "jpn",
			"kaz", "kor", "lav", "lit", "ltz", "mac", "may", "nor",
			"oci", "pol", "por", "pob", "rum", "rus", "scc", "sin",
			"slo", "slv", "spa", "swe", "syr", "tgl", "tha", "tur",
			"ukr", "urd", "vie"};
	
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public String[] getLanguageNames() {
		return languageNames;
	}
	
	public String[] getLanguageIds() {
		return languageIds;
	}
}
