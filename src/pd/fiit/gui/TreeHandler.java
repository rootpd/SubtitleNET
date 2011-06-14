package pd.fiit.gui;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/** getting files/folders lists and updating file tree in application */
public final class TreeHandler {
	private JTree folderTree;
	private DefaultListModel fileListModel;
	
	public TreeHandler(JTree folderTree, DefaultListModel fileListModel) {
		setFolderTree(folderTree);
		setFileListModel(fileListModel);
	}
	
	public void setFolderTree(JTree folderTree) {
		this.folderTree = folderTree;
	}
	
	public void setFileListModel(DefaultListModel fileListModel) {
		this.fileListModel = fileListModel;
	}
	
	/** tells GUI to refresh folder tree so new nodes could appear */
	void updateFolderTree(final DefaultMutableTreeNode root) {
		DefaultTreeModel model = (DefaultTreeModel) folderTree.getModel();
		model = (DefaultTreeModel) folderTree.getModel();
		model.nodeChanged(root);
	}

	/** list folders and video files in selected folder, folders will appear in tree and files in list */
	void getFolderComponents(DefaultMutableTreeNode selectedNode, String pathToTraverse) {
		FolderTraversar folder = new FolderTraversar(new File(pathToTraverse));
		folder.traverse();
		
		String extensions[] = {".3g2", ".3gp", ".3gp2", ".3gpp", ".60d", ".ajp", ".asf", ".asx",
				".avchd", ".avi", ".bik", ".bix", ".box", ".cam", ".dat", ".divx", ".dmf", ".dv",
				".dvr-ms", ".evo", ".flc", ".fli", ".flic", ".flv", ".flx", ".gvi", ".gvp",
				".h264", ".m1v", ".m2p", ".m2ts", ".m2v", ".m4e", ".m4v", ".mjp", ".mjpeg", ".mjpg",
				".mkv", ".moov", ".mov", ".movhd", ".movie", ".movx", ".mp4", ".mpe", ".mpeg", ".mpg",
				".mpv", ".mpv2", ".mxf", ".nsv", ".nut", ".ogg", ".ogm", ".omf", ".ps", ".qt", ".ram",
				".rm", ".rmvb", ".swf", ".ts", ".vfw", ".vid", ".video", ".viv", ".vivo", ".vob",
				".vro", ".wm", ".wmv", ".wmx", ".wrap", ".wvx", ".wx", };
		
		fileListModel.clear();
		for (int i=0; i<folder.files.size(); i++) {
			String fileName = folder.files.get(i);
			for (String ext : extensions) 
				if ((fileName.length() >= ext.length()) && (fileName.lastIndexOf(ext) == (fileName.length() - ext.length()))) {
					fileListModel.add(fileListModel.getSize(), fileName);
					break;
				}		
		}
		
		if (fileListModel.getSize() == 0) 
			fileListModel.add(0, "No video files in folder");
		
		if (selectedNode == null || selectedNode.getChildCount() != 0) return;
		
		for (String newFolder : folder.folders) {
			selectedNode.add(new DefaultMutableTreeNode(newFolder));
		}
	}

	/** extracts full path of selected folder */
	String getFullFolderPath(TreeSelectionEvent e) {
		TreePath newPath = e.getNewLeadSelectionPath();
    				    	
    	StringBuffer pathToTraverse = new StringBuffer();
    	while (!newPath.getLastPathComponent().toString().equals("Computer")) {
    		String folderToAppend = newPath.getLastPathComponent().toString();
    		if (folderToAppend.indexOf(System.getProperty("file.separator")) == -1)
    			folderToAppend += System.getProperty("file.separator");
    		
    		pathToTraverse.insert(0, folderToAppend);
    		newPath = newPath.getParentPath();
		}
		return pathToTraverse.toString();
	}
}
