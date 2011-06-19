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
import pd.fiit.gui.WorkingDialog;

@SuppressWarnings("rawtypes")
/** downloading and unzipping subtitles, .srt files will appear in folder with selected movie */
public final class DownloadHandler implements Callable {
	private WorkingDialog working = null;
	private String targetFolder = null;
	private List<Subtitle> subtitles;
	private int index;
	private static final Logger logger = Logger.getLogger(DownloadHandler.class.getName());
	
	public DownloadHandler (List<Subtitle> subtitles, int index, WorkingDialog working) {
		this.subtitles = subtitles;
		this.index = index;
		this.working = working;
	}

	@Override
	public String call() {
		working.setMessage("Downloading subtitles, please wait.");
		working.getCancelButton().setEnabled(false);
		
		int sumCD = Integer.parseInt(subtitles.get(index).getSubSumCD());
		targetFolder = subtitles.get(0).getTargetFolder();
		
		try {
			if (targetFolder == null) { // text search
				for (int i=0; i<sumCD; i++)
					downloadSubtitle(subtitles.get(index+i), true);
			} else { // hash search 
				working.setVisible(true);
				downloadSubtitle(subtitles.get(index), false);
			}
			
		} catch (IOException e) {
			logger.severe("could not download subtitles, connection failure or just API server online.");
			JOptionPane.showMessageDialog(null, "Could not download subtitles.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (NullPointerException e) {
			logger.info("subtitle download canceled.");
			return null;
		} finally {
			working.setVisible(false);
		}
			
		JOptionPane.showMessageDialog(null, "Subtitles successfully downloaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
		
		return "OK";
	}

	/** downloads subtitle file
	 * 
	 * @param subtitle > structure with information about subtitle
	 * @param origFileName > if true, subtitle file name will be retrieved from API server, otherwise movie name will be used
	 * @return > true if search got cancelled while selecting target folder, false otherwise
	 * @throws IOException 
	 */
	private void downloadSubtitle(Subtitle subtitle, boolean origFileName) throws IOException {
		String downloadLink = subtitle.getSubDownloadLink();
		String gzFileName = "tmpsub.gz";		
		String subFileName = null;
		
		if (origFileName)
			subFileName = subtitle.getSubFileName();
		else 
			subFileName = subtitle.getSourceFileName() + "." + subtitle.getSubFormat();
		
		if (targetFolder == null) {
			if ((targetFolder = invokeSaveWindow()) == null)
				throw new NullPointerException();
			
			targetFolder += System.getProperty("file.separator");
			working.setVisible(true);
		}
		
		HttpConn.HttpDownloadFile(downloadLink, targetFolder + gzFileName);
		Zip.unGZip(targetFolder + gzFileName, targetFolder + subFileName, true);
		
		logger.log(Level.INFO, "subtitles downloaded to " + targetFolder + subFileName + ".");
		
		return;
	}

	/**
	 * Invokes window for selecting target folder for subtitles
	 * if more than 1CD, all subtitles will be saved to selected folder
	 * 
	 * @return
	 */
	private String invokeSaveWindow() {
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
