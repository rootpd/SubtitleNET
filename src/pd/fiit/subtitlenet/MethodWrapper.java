package pd.fiit.subtitlenet;

import java.util.List;

import org.w3c.dom.Element;

/** using XML-RPC class to easily wrap methods to communicate with OpenSubtitles.org server */
public final class MethodWrapper {
	/** creates login structure according to http://trac.opensubtitles.org/projects/opensubtitles/wiki/XmlRpcLogIn */
	public String logIn() {
		XmlRpc request = new XmlRpc("LogIn");
		
		request.setValue(request.addParam(), "string", ""); //login
		request.setValue(request.addParam(), "string", ""); //password
		request.setValue(request.addParam(), "string", ""); //language
		request.setValue(request.addParam(), "string", "SubtitleNET " + new Main().getVersion()); //user agent - requried
		
		return request.xmlToString();
	}
	
	/** creates search structure according to http://trac.opensubtitles.org/projects/opensubtitles/wiki/XmlRpcSearchSubtitles */
	public String searchSubtitles(String token, String language, String movieHash, String movieSize, String imdbId) {
		XmlRpc request = new XmlRpc("SearchSubtitles");
		
		request.setValue(request.addParam(), "string", token); //token - required, retrieved from login response
		List<Element> arrays = request.createArray(request.addParam(), 1);
		
		Element struct = request.createStruct(arrays.get(0));
		request.addMember(struct, "sublanguageid", "string", language);
		request.addMember(struct, "moviehash", "string", movieHash);
		request.addMember(struct, "moviebytesize", "double", movieSize);
		request.addMember(struct, "imdbid", "string", imdbId);	
		
		return request.xmlToString();
	}
	
	public String searchMoviesOnIMDb(String token, String movieName) {
		XmlRpc request = new XmlRpc("SearchMoviesOnIMDB");
		
		request.setValue(request.addParam(), "string", token); //token - required, retrieved from login response
		request.setValue(request.addParam(), "string", movieName); //token - required, retrieved from login response		
		
		return request.xmlToString();
	}
	
	/** creates download structure according to http://trac.opensubtitles.org/projects/opensubtitles/wiki/XmlRpcDownloadSubtitles */
	// never used, replaced with pd.fiit.reusable.Zip class for unzipping given .gz archive
	public String downloadSubtitles() {
		XmlRpc request = new XmlRpc("SearchSubtitles");
		int movieId = 123456; // chosen output from searchSubtitles should go here
		
		request.setValue(request.addParam(), "string", ""); //token - required, retrieved from login response
		List<Element> arrays = request.createArray(request.addParam(), 1); //
		request.setValue(arrays.get(0), "int", Integer.toString(movieId)); //
		
		return request.xmlToString();
	}
}
