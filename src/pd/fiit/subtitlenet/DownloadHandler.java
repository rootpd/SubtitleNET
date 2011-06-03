package pd.fiit.subtitlenet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pd.fiit.reusable.HttpConn;
import pd.fiit.reusable.Zip;

@SuppressWarnings("rawtypes")
/** downloading and unzipping subtitles, .srt files will appear in folder with selected movie */
public final class DownloadHandler implements Callable {
	private List<Subtitle> subtitles;
	private int index;
	private static final Logger logger = Logger.getLogger(DownloadHandler.class.getName());
	
	public DownloadHandler (List<Subtitle> subtitles, int index) {
		this.subtitles = subtitles;
		this.index = index;
	}

	@Override
	public String call() {
		int sumCD = Integer.parseInt(subtitles.get(index).getSubSumCD());
		
		if (sumCD != 1 && subtitles.get(0).getTargetFolder() == null)
			for (int i=0; i<sumCD; i++) {
				downloadSubtitle(subtitles.get(index+i), true);
			}
		else {
			downloadSubtitle(subtitles.get(index), false);
		}
		
		return "OK";
	}

	/** downloads subtitle file
	 * 
	 * @param subtitle > structure with information about subtitle
	 * @param origFileName > if true, subtitle file name will be retrieved from API server, otherwise movie name will be used
	 * @return > null if something screwed, proper string otherwise
	 */
	private String downloadSubtitle(Subtitle subtitle, boolean origFileName) {
		String downloadLink = subtitle.getSubDownloadLink();
		
		String gzFileName = subtitle.getMovieName() + ".gz";
		String targetFolder = subtitle.getTargetFolder();
		
		String subFileName = null;
		if (origFileName)
			subFileName = subtitle.getSubFileName() + "." + subtitle.getSubFormat();
		else 
			subFileName = subtitle.getMovieName() + "." + subtitle.getSubFormat();
		
		if (targetFolder == null) {
			if ((targetFolder = invokeSaveWindow(targetFolder)) == null)
				return null;
			targetFolder += System.getProperty("file.separator");
		}
		
		try {
			HttpConn.HttpDownloadFile(downloadLink, targetFolder + gzFileName);
		} catch (IOException e) {
			logger.severe("could not download subtitles, connection failure or just API server online.");
			JOptionPane.showMessageDialog(null, "Could not download subtitles.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		Zip.unGZip(targetFolder + gzFileName, targetFolder + subFileName, true);
		
		JOptionPane.showMessageDialog(null, "Subtitles successfully downloaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
		logger.log(Level.INFO, "subtitles downloaded to " + targetFolder + subFileName + ".");
		
		return "OK";
	}

	private String invokeSaveWindow(String targetFolder) {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fc.getSelectedFile();
		    return file.getAbsolutePath();
	
		}
		
		return null;
	}

}
