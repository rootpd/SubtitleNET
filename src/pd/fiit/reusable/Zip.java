package pd.fiit.reusable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/** class for handling every kind of archive, more methods to be added in the future */
public class Zip {
	private static final Logger logger = Logger.getLogger(Zip.class.getName());
	
	/** decompress gzip archive into file, possibility to delete archive after decompression */
	public static void unGZip(String sourceFile, String targetFile, boolean toBeDeleted) {
		byte[] buffer = new byte[1024];
		 
	    try {
	    	GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(sourceFile));
	    	FileOutputStream out = new FileOutputStream(targetFile);
	 
	        int len;
	        while ((len = gzis.read(buffer)) > 0) {
	        	out.write(buffer, 0, len);
	        }
	 
	        gzis.close();
	    	out.close();
	    } catch(IOException ex) {
	     	logger.warning("could not unzip the file, check archive.");  
	    }
	    
	    if (toBeDeleted) {
		    File gzFile = new File(sourceFile);
		    gzFile.delete();
	    }
	}
}
