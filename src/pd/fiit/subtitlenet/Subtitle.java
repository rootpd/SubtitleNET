package pd.fiit.subtitlenet;

/** structure for mandatory subtitle information */
public final class Subtitle {
	private String movieName;
	private String movieYear;
	private String languageName;
	private String SubActualCD;
	private String subSumCD;
	private String subFormat;
	private String subAddDate;
	private String subDlCount;
	private String subDownloadLink;
	private String subFileName;
	private String targetFolder;
	private String releaseName;
	
	public String getMovieName() { 
		return movieName; 
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public String getMovieYear() {
		return movieYear; 
	}

	public void setMovieYear(String movieYear) {
		this.movieYear = movieYear;
	}
	
	public String getLanguageName() { 
		return languageName;
	}
	
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	public String getSubActualCD() {
		return SubActualCD;
	}
	
	public void setSubActualCD(String SubActualCD) {
		this.SubActualCD = SubActualCD;
	}
	
	public String getSubSumCD() { 
		return subSumCD; 
	}
	
	public void setSubSumCD(String subSumCD) {
		this.subSumCD = subSumCD;
	}
	
	public String getSubFormat() {
		return subFormat; 
	}
	
	public void setSubFormat(String subFormat) {
		this.subFormat = subFormat;
	}
	
	public String getSubAddDate() {
		return subAddDate; 
	}
	
	public void setSubAddDate(String subAddDate) {
		this.subAddDate = subAddDate;
	}
	
	public String getSubDlCount() {
		return subDlCount; 
	}
	
	public void setSubDlCount(String subDlCount) {
		this.subDlCount = subDlCount;
	}
	
	public String getSubDownloadLink() {
		return subDownloadLink; 
	}
	
	public void setSubDownloadLink(String subDownloadLink) {
		this.subDownloadLink = subDownloadLink;
	}
	
	public String getTargetFolder() {
		return targetFolder; 
	}
	
	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}
	
	public String getSubFileName() {
		return subFileName; 
	}
	
	public void setSubFileName(String subFileName) {
		this.subFileName = subFileName;
	}
	
	public String getReleaseName() {
		return releaseName; 
	}
	
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
}
