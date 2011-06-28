package pd.fiit.subtitlenet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import pd.fiit.gui.GUI;
import pd.fiit.reusable.HttpConn;
import pd.fiit.subtitlenet.MethodWrapper;
import pd.fiit.subtitlenet.OpenSubtitlesHasher;
import pd.fiit.subtitlenet.Subtitle;

/** handling subtitle search, parsing xmls and creating subtitle list */
public final class SearchHandler implements Callable<List<Subtitle>> {
	private DefaultListModel subtitleListModel = null;
	private String inputSearch = null;
	private List<Subtitle> subtitles = null;
	private GUI gui = null;
	private static final Logger logger = Logger.getLogger(SearchHandler.class.getName());
	private volatile boolean dontPrint = false;
	
	public SearchHandler (GUI gui) {
		this.subtitleListModel = gui.getSubtitleListModel();
		this.gui = gui;
		
		if (gui.getInputCheck().getSelectedObjects() == null)
			inputSearch = null; // hash search
		else
			inputSearch = gui.getInputSearch().getText(); // text search
	}

	/** core of handler */
	public List<Subtitle> call() throws InterruptedException {
		try {
			obtainToken();
			initializeWorkingDialog();
			
			if (gui.getToken() == null)
				logger.warning("no token obtained.");
			
			if (inputSearch != null) {
				gui.getWorking().setMessage("Searching IMDb for movie.");
				imdbSearch(); // text search
			} else 		
				hashSearch(); // file search
			
		} catch (ParseException e) {
			gui.getWorking().setVisible(false);
			JOptionPane.showMessageDialog(null, "Service unavailable, please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
			logger.info("503 service unavailable");
			return null;
			
		} catch (NullPointerException e) {
			gui.getWorking().setVisible(false);
			JOptionPane.showMessageDialog(null, "Something went definitely wrong", "Error", JOptionPane.ERROR_MESSAGE);
			logger.severe("Null returned, possible connection error.");
			return null;
			
		} finally {
			if(!dontPrint)
				gui.getWorking().setVisible(false);
		}
		
		return subtitles;
	}

	private void initializeWorkingDialog() {
		gui.getWorking().setMessage("Searching subtitles, please wait.");			
		gui.getWorking().getCancelButton().addActionListener(
			    new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			        	dontPrint = true;
			        }
			    }
			);
		gui.getWorking().getCancelButton().setEnabled(true);
	}

	/** obtains token from Future logInThread, blocking if logInThread is still being processed */
	private void obtainToken() {
		gui.getWorking().setMessage("Connecting to subtitle server.");
		
		String token = null;
		try {
			token = gui.getToken(); // get if obtained in the past
			
			if (token == null) // or get for the first time from Future
				token = gui.getLogInThread().get(); // waiting for login finished
		} catch (InterruptedException e) {
			logger.severe("login thread interupted.");
		} catch (ExecutionException e) {
			logger.warning("cannot obtain token for some reason.");
		} finally {
			gui.setToken(token);
		}
	}

	/** searching subtitles according to imdb results of given string (movie name driven search) */
	private void imdbSearch() {
		String movieName = inputSearch;
		
		String response = sendSearchMovieOnImdbRequest(gui.getToken(), movieName);	
		if (response.indexOf("503 Service Unavailable") != -1 || response == null)
			return;
			
		ResponseHandler handler = new ResponseHandler(response);
		
		String id = null;
		if ((id = getExactMovieId(handler)) == null) return;

		response = sendSearchRequest("", gui.getToken(), "", id);
		if (response.indexOf("503 Service Unavailable") != -1 || response == null)
			return;
		
		handler.setResponse(response);
		
		getSubtitleList(handler); // update
		showSubtitles();
	}

	/**
	 * shows list of found movies and returns imdb id of picked movie, null if nothing found or search canceled
	 * 
	 * @param handler
	 * @return
	 */
	private String getExactMovieId(ResponseHandler handler) {
		List<IMDbMovie> movies = getIMDbMovieList(handler); // get matching results
		if (movies.size() == 0) {
			JOptionPane.showMessageDialog(null, "No movie found, please try again.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return null;
		} else if (movies.size() == 1) {
			gui.getWorking().setMessage("Searching subtitles, please wait.");
			return movies.get(0).getId();
		}
			
		HashMap<String, String> map = new HashMap<String, String>();
		String[] movieNames = new String[movies.size()]; // show matching results
		for (int i=0; i<movies.size(); i++) {
			map.put(movies.get(i).getName(), movies.get(i).getId());
			movieNames[i] = movies.get(i).getName();
			
			if (movieNames[i].length() > 128)
				movieNames[i] = movieNames[i].substring(0, 128) + "...";
		}
		
		String id = null;
		if (dontPrint == false) {
			gui.getWorking().setVisible(false);
			id = (String) JOptionPane.showInputDialog(gui, "Please choose desired movie", "Search", JOptionPane.PLAIN_MESSAGE, null, movieNames, movieNames[0]);
			gui.getWorking().setMessage("Searching subtitles, please wait.");
			
			id = map.get(id);
			
			if (id != null) 
				gui.getWorking().setVisible(true);
		}
		
		return id;
	}

	/** searching subtitles according to computed hash (file driven search) 
	 * @throws ParseException */
	private void hashSearch() throws ParseException {
		File file = new File(gui.getSelectedFolder() + gui.getFileList().getSelectedValue());
		String hash = null;
		
		try {
			hash = computeFileHash(file);
		} catch (FileNotFoundException e) {
			logger.severe("can not compute hash, file not found.");
		} catch (IOException e) {
			logger.severe("can not compute hash, something wrong with a file");
		}
		
		String movieSize = Long.toString(file.length()); // search
		String response = sendSearchRequest(hash, gui.getToken(), movieSize, "");
		if (response.indexOf("503 Service Unavailable") != -1 || response == null) 
			throw new ParseException("503 Service Unavailable", 0);
	
		ResponseHandler handler = new ResponseHandler(response);

		getSubtitleList(handler); // update
		showSubtitles();
	}
	
	/** show found subtitles in listbox */
	private void showSubtitles() {
		if(dontPrint) return;
		subtitleListModel.clear(); // clear the listbox
		
		if (subtitles.size() == 0) { // nothing to show
			subtitleListModel.add(0, "Nothing found...");
			return;
		}		

		for (Subtitle sub : subtitles) { // node structure
			String release = "";
			
			if (sub.getReleaseName().length() > 2) {
				release = sub.getReleaseName();
				if (release.indexOf(":") != -1)
					release = release.substring(release.indexOf(":")+1);
			}
			
			String subtitleInfo = 
				sub.getMovieName() + " (" + 
				sub.getMovieYear() + ") | " +
				sub.getLanguageName() + " | " +
				sub.getSubDlCount() + " DLs | " +
				"DISC " + sub.getSubActualCD() + "/" + sub.getSubSumCD() + " | " +
				sub.getSubAddDate() + " | " + 
				"rls: " + release;
				
			subtitleListModel.addElement(subtitleInfo);
			
			if (inputSearch == null)
				sub.setTargetFolder(gui.getSelectedFolder());
			else 
				sub.setTargetFolder(null);
			
			try { // mandatory for successful repaint of jlist with results (dont have a clue why)
				Thread.sleep(32);
			} catch (InterruptedException eaten) {}
		}
		
		return;
	}
	
	/** gets mandatory information from server response and puts it into Subtitle structure
	 * 
	 * @param handler Object containing xml-rpc response to parse
	 */
	private void getSubtitleList(ResponseHandler handler) {
		subtitles = new LinkedList<Subtitle>();
		while (true) {
			Subtitle sub = new Subtitle();
			
			sub.setMovieName(handler.getVariableValue("MovieName"));		
			sub.setMovieYear(handler.getVariableValue("MovieYear"));
			sub.setLanguageName(handler.getVariableValue("LanguageName"));
			sub.setSubActualCD(handler.getVariableValue("SubActualCD"));
			sub.setSubSumCD(handler.getVariableValue("SubSumCD"));
			sub.setSubFormat(handler.getVariableValue("SubFormat"));
			sub.setSubAddDate(handler.getVariableValue("SubAddDate"));
			sub.setSubDlCount(handler.getVariableValue("SubDownloadsCnt"));
			sub.setSubDownloadLink(handler.getVariableValue("SubDownloadLink"));
			sub.setSubFileName(handler.getVariableValue("SubFileName"));
			sub.setReleaseName(handler.getVariableValue("MovieReleaseName"));
			if (inputSearch == null) sub.setSourceFileName(gui.getFileList().getSelectedValue().toString());
			
			if (sub.getMovieName() != null) 
				subtitles.add(sub);
			else 
				return;
		}
	}
	
	/**
	 * gets complete list of matching movie titles at imdb according to given string
	 * 
	 * @param handler
	 * @return
	 */
	private List<IMDbMovie> getIMDbMovieList(ResponseHandler handler) {
		List<IMDbMovie> movies = new LinkedList<IMDbMovie>();
		while (true) {
			IMDbMovie movie = new IMDbMovie();
			
			movie.setId(handler.getVariableValue("id"));
			movie.setName(handler.getVariableValue("title"));
			
			if (movie.getName() != null) 
				movies.add(movie);
			else 
				return movies;
		}
	}
	
	/** tries to login to opensubtitles and gets token to proceed other requests 
	 * 
	 * @param token Token obtained from logging to API server
	 * @param movieName Name of the movie (obtained from textBox)
	 * @return List of movies that fits (only first is used then)
	 */
	private String sendSearchMovieOnImdbRequest(String token, String movieName) {
		MethodWrapper xmlRequest = new MethodWrapper();
		String response = null;
		
		try { // try to login
			response = HttpConn.HttpPost("api.opensubtitles.org", "xml-rpc", xmlRequest.searchMoviesOnIMDb(token, movieName));
		} catch (NullPointerException e1) {
			return null;
		} catch (UnknownHostException e) {
			logger.severe("uknown host caught when trying to send search request");
			return null;
		}
		
		response = response.substring(response.indexOf("<?xml"));
		return response;
	}
	
	/** sends search request according to hash and chosen languages 
	 * 
	 * @param hash Computed hash of a video file
	 * @param token Token obtained from logging to API server
	 * @param movieSize Size of video file in bytes
	 * @param imdbId IMDB id of video (mandatory when non-hash search is being processed)
	 * @return List of subtitles meeting given conditions
	 */
	private String sendSearchRequest(String hash, String token, String movieSize, String imdbId) {
		if(hash == null)
			throw new IllegalArgumentException("Hash mustn't be null");
		MethodWrapper xmlRequest = new MethodWrapper();
		String languages = gui.getLanguage().getLanguageIds()[gui.getLanguage().getSelectedIndex()];
		String response = null;
		
		try { 
			response = HttpConn.HttpPost("api.opensubtitles.org", "xml-rpc", xmlRequest.searchSubtitles(token, languages, hash, movieSize, imdbId));
		} catch (NullPointerException e1) {
			return null;
		} catch (UnknownHostException e2) {
			logger.severe("uknown host caught when trying to send search request");
			return null;
		}
		
		response = response.substring(response.indexOf("<?xml"));
		return response;
	}

	/** gets hash of chosen file 
	 * 
	 * @param file File you want to compute hash from
	 * @return Hash
	 * @throws IOException 
	 */
	private String computeFileHash(File file) throws IOException, FileNotFoundException{		
		if(!file.exists()){
			throw new FileNotFoundException("File not exist, cannot count hash");
		}
		try { // get hash
			return OpenSubtitlesHasher.computeHash(file);
		} catch (IOException e1) {
			//logger.severe("could not compute file hash, something wrong with file.");
			throw new IOException("could not compute file hash, something wrong with file.");
		}
	}
	
}
