package pd.fiit.gui;

import java.io.File;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import pd.fiit.reusable.FileHelper;

/** getting files/folders lists and updating file tree in application */
public final class TreeHandler {
	private JTree folderTree;
	private JList fileList;
	private DefaultListModel fileListModel;
	
	public TreeHandler(JTree folderTree, JList fileList) {
		setFolderTree(folderTree);
		setFileList(fileList);
	}
	
	public void setFolderTree(JTree folderTree) {
		this.folderTree = folderTree;
	}
	
	public void setFileList(JList fileList) {
		this.fileList = fileList;
		this.fileListModel = (DefaultListModel) fileList.getModel();
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
			
		fileListModel.clear();
		for (int i=0; i<folder.files.size(); i++) {
			String fileName = folder.files.get(i);
			if(FileHelper.isSuportedName(fileName)){
				fileListModel.add(fileListModel.getSize(), fileName);
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
	
	/** Select row in folderTree and fileList be File f */
	void traverseTreePath(File f){
		if (!FileHelper.isSuportedFile(f)) 
			return;
		
		expandTreePath(FileHelper.fileToPath(f));
		if (f.isFile())
			selectFilelistNode(f.getName());
	}
	
	/** Select line in fileList with text "fileName" */
	private void selectFilelistNode(String fileName) {
		int size = fileList.getModel().getSize();
		
		for (int i=0; i<size; i++) {
		     if (fileList.getModel().getElementAt(i).equals(fileName)) {
		    	 fileList.setSelectedIndex(i);
		    	 break;
		     }
		}
	}
	
	/** expands folder three to desired path */
	private void expandTreePath(List<String> path) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) folderTree.getModel().getRoot();
		TreePath treePath = new TreePath(root);
		
		final List<DefaultMutableTreeNode> row = new LinkedList<DefaultMutableTreeNode>();
		row.add(root);
		
		folderTree.setSelectionPath(treePath);
		folderTree.expandPath(treePath);
		
		DefaultMutableTreeNode node = null;
		for(String name : path){
			node = searchNode(root, name);
			if(node != null){
				row.add(node);
				treePath = new TreePath(row.toArray());
				root = node;
				folderTree.setSelectionPath(treePath);
				folderTree.expandPath(treePath);
			}
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				folderTree.scrollPathToVisible(new TreePath(row.toArray()));
			}
		});
	}
	
	/** Search for line with name "nodeStr" in subtree "tree" */ 
	private DefaultMutableTreeNode searchNode(DefaultMutableTreeNode tree, String nodeStr) {
		DefaultMutableTreeNode node = null;
		Enumeration<?> enuma = tree.breadthFirstEnumeration();

		while (enuma.hasMoreElements()) {
			node = (DefaultMutableTreeNode) enuma.nextElement();
			if (nodeStr.equals(node.getUserObject().toString())) {
				return node;
			}
		}
		return null;
	}
}
