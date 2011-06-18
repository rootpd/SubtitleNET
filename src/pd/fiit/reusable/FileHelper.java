package pd.fiit.reusable;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class FileHelper {
	private static Set<String> suffix = null;	
	private static String extensions[] = { ".3g2", ".3gp", ".3gp2", ".3gpp",
			".60d", ".ajp", ".asf", ".asx", ".avchd", ".avi", ".bik", ".bix",
			".box", ".cam", ".dat", ".divx", ".dmf", ".dv", ".dvr-ms", ".evo",
			".flc", ".fli", ".flic", ".flv", ".flx", ".gvi", ".gvp", ".h264",
			".m1v", ".m2p", ".m2ts", ".m2v", ".m4e", ".m4v", ".mjp", ".mjpeg",
			".mjpg", ".mkv", ".moov", ".mov", ".movhd", ".movie", ".movx",
			".mp4", ".mpe", ".mpeg", ".mpg", ".mpv", ".mpv2", ".mxf", ".nsv",
			".nut", ".ogg", ".ogm", ".omf", ".ps", ".qt", ".ram", ".rm",
			".rmvb", ".swf", ".ts", ".vfw", ".vid", ".video", ".viv", ".vivo",
			".vob", ".vro", ".wm", ".wmv", ".wmx", ".wrap", ".wvx", ".wx", };
	
	static{
		suffix = new HashSet<String>();
		for(String suff : extensions)
			suffix.add(suff);
	}
	
	/** trim file path to directory names */
	public static List<String> fileToPath(File f){
		List<String> path = new LinkedList<String>();
		if(f.isFile()){
			f = f.getParentFile();
		}
		while(f != null && f.isDirectory()){
			if(f.getName().length() != 0)
				path.add(0, f.getName());
			else
				path.add(0, f.getPath());
			f = f.getParentFile();
		}
		return path;
	}
	
	/** Is File "f" video file? */
	public static boolean isSuportedFile(File f){
		if(!f.isFile())
			return false;
		int begin = f.toString().lastIndexOf(".");
		if(begin < 0)
			return false; 
		if(suffix.contains(f.toString().substring(begin)))
			return true;
		return false;
	}

	/** Is Name "fileName" video name? */
	public static boolean isSuportedName(String fileName) {
		int begin = fileName.lastIndexOf(".");
		if(begin < 0)
			return false; 
		if(suffix.contains(fileName.substring(begin)))
			return true;
		return false;
	}
}
