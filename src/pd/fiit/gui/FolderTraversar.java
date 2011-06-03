package pd.fiit.gui;

import java.io.File;
import java.util.ArrayList;

/** gets given folder files */
public class FolderTraversar {
	ArrayList<String> files;
	ArrayList<String> folders;
    private File sourceDir;

    public FolderTraversar(File sourceDir)
    {
        this.sourceDir = sourceDir;
    }

    /** gets folder files and directories, not recursive */
   void traverse() {
    	files = new ArrayList<String>();
    	folders = new ArrayList<String>();

    	File allFiles[] = sourceDir.listFiles();
    	if (allFiles == null)
    		return;
    	
        for (File file : allFiles) {
        	if (file.isDirectory() && !file.isHidden()) folders.add(file.getName());
        	else if (file.isFile() && !file.isHidden()) files.add(file.getName());
        }	
    }
}
