package pd.fiit.gui;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JList;
import javax.swing.JButton;

import pd.fiit.subtitlenet.DownloadHandler;
import pd.fiit.subtitlenet.LanguageHandler;
import pd.fiit.subtitlenet.LogInHandler;
import pd.fiit.subtitlenet.SearchHandler;
import pd.fiit.subtitlenet.Subtitle;
import pd.fiit.subtitlenet.Main;

/** main GUI */
public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private DefaultListModel fileListModel = null;
	private DefaultListModel subtitleListModel = null;
	private JPanel jContentPane = null;
	private JScrollPane treeScrollPane = null;
	private JTree folderTree = null;
	private JList fileList = null;
	private JList subtitleList = null;
	private JComboBox languageCombo = null;
	private JTextField inputSearch = null;
	private JCheckBox inputCheck = null;
	private JScrollPane fileListScrollPane = null;
	private JScrollPane subtitleListScrollPane = null;
	private JButton searchButton = null;
	private JButton downloadButton = null;
	
	private WorkingDialog working = null;
	private ExecutorService exec = Executors.newCachedThreadPool();
	private String selectedFolder = null;
	private List<Subtitle> subtitles = null;
	private Future<List<Subtitle>> searchThread = null;
	private LanguageHandler language = null;
	private String token = null;
	private Future<String> logInThread = null;
	private static final Logger logger = Logger.getLogger(GUI.class.getName());

	public GUI() {
		super();
		initialize();
	}

	/** initialization of application window */
	private void initialize() {
		this.setSize(613, 440);
		this.setContentPane(getJContentPane());
		this.setTitle("SubtitleNET " + new Main().getVersion());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/img/movieIcon.png")));
		this.setVisible(true);
		Callable<String> task = new LogInHandler();
		logInThread = exec.submit(task);
	}

	/** add components into application window */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(2, 1));
			
			jContentPane.add(new JPanel(){
				private static final long serialVersionUID = 1L;
				{
					setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
					
					c.ipadx = 1;
					c.ipady = 1;
					c.gridx = 0;
					c.gridy = 0;
					add(new JPanel(), c);
					
					c.fill = GridBagConstraints.BOTH;
					c.gridx = 1;
					c.gridy = 1;
					c.weightx = 0.5;
					add(getTreeScrollPane(), c);
					
					c.ipadx = 1;
					c.ipady = 1;
					c.gridx = 2;
					c.gridy = 1;
					c.weightx = 0;
					add(new JPanel(), c);
					
					c.weighty = 1;
					c.fill = GridBagConstraints.BOTH;
					c.gridx = 3;
					c.gridy = 1;
					c.weightx = 0.5;
					add(getFileListScrollPane(), c);
					
					c.ipadx = 1;
					c.ipady = 1;
					c.gridx = 4;
					c.gridy = 3;
					c.weightx = 0;
					c.weighty = 0;
					add(new JPanel(), c);
				}
			});
			jContentPane.add(new JPanel(){
				private static final long serialVersionUID = 1L;
				{
					setLayout(new GridBagLayout());
					GridBagConstraints gridLayout = new GridBagConstraints();
					
					gridLayout.ipadx = 1;
					gridLayout.ipady = 1;
					gridLayout.gridx = 0;
					gridLayout.gridy = 1;
					add(new JPanel(), gridLayout);	

					gridLayout.gridx = 1;
					gridLayout.gridy = 0;
					add(getInputCheck(), gridLayout);
					
					gridLayout.weightx = 1;
					gridLayout.weighty = 0;
					gridLayout.fill = GridBagConstraints.HORIZONTAL;
				//	gridLayout.ipadx = 200;
					gridLayout.gridx = 2;
					gridLayout.gridy = 0;
					add(getInputSearch(), gridLayout);	
					
					gridLayout.weightx = 1;
					gridLayout.weighty = 0;
					gridLayout.fill = GridBagConstraints.HORIZONTAL;
					gridLayout.gridx = 3;
					gridLayout.gridy = 0;
					add(getLanguageCombo(), gridLayout);
					
					gridLayout.weightx = 1;
					gridLayout.weighty = 0;
					gridLayout.fill = GridBagConstraints.HORIZONTAL;
					gridLayout.gridx = 4;
					gridLayout.gridy = 0;
					add(getSearchButton(), gridLayout);

					gridLayout.weightx = 1;
					gridLayout.weighty = 1;
					gridLayout.fill = GridBagConstraints.BOTH;
					gridLayout.gridwidth = 4;
					gridLayout.gridx = 1;
					gridLayout.gridy = 2;
					add(getSubtitleListScrollPane(), gridLayout);
					
					gridLayout.ipadx = 1;
					gridLayout.ipady = 1;
					gridLayout.gridx = 5;
					gridLayout.gridy = 3;
					gridLayout.weightx = 0;
					gridLayout.weighty = 0;
					add(new JPanel(), gridLayout);
					
					gridLayout.weightx = 1;
					gridLayout.weighty = 0;
					gridLayout.gridx = 4;
					gridLayout.gridy = 4;
					add(getDownloadButton(), gridLayout);
					
					gridLayout.ipadx = 1;
					gridLayout.ipady = 1;
					gridLayout.gridx = 0;
					gridLayout.gridy = 5;
					add(new JPanel(), gridLayout);
				}
			});
		}
		return jContentPane;
	}
	
	/** initialization of checkbox toggling custom (not file) search */
	public JCheckBox getInputCheck() {
		if (inputCheck == null) {
			inputCheck = new JCheckBox();
		}
		
		inputCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					inputSearch.setText("");
					inputSearch.setEnabled(true);
					fileList.setEnabled(false);
					folderTree.setEnabled(false);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					inputSearch.setText("Search subtitles without file.");
					inputSearch.setEnabled(false);
					fileList.setEnabled(true);
					folderTree.setEnabled(true);
				}
			}
			
		});
		
		return inputCheck;
	}
	
	/** initialization of custom search input field */
	public JTextField getInputSearch() {
		if (inputSearch == null) {
			inputSearch = new JTextField();
			inputSearch.setText("Search subtitles without file.");
			inputSearch.setEnabled(false);
			inputSearch.setMinimumSize(new Dimension(150, 30));
		}
		return inputSearch;
	}

	/** initialization of language combo box, handler in LanguageHandler class */
	private JComboBox getLanguageCombo() {
		if (languageCombo == null) {
			this.language = new LanguageHandler();
			String[] langlist = getLanguage().getLanguageNames();
			
			languageCombo = new JComboBox(langlist);
			languageCombo.setSelectedIndex(1);
		}
		
		return languageCombo;
	}

	/** constructor for treeScrollPane */
	private JScrollPane getTreeScrollPane() {
		if (treeScrollPane == null) {
			treeScrollPane = new JScrollPane();
			treeScrollPane.setViewportView(getFolderTree());
		}
		return treeScrollPane;
	}

	/** gets tree component and sets the selection listener */
	private JTree getFolderTree() {
		if (folderTree == null) {
			final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Computer"); // default actions
			File[] roots = File.listRoots();
			
			for (File drive : roots) { // add drive letters
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(drive);
				root.add(child);
			}
			
			renderTree(root); // just basic functionality and visual outcome
			
			folderTree.addTreeSelectionListener(new TreeSelectionListener() { // listener
				public void valueChanged(TreeSelectionEvent e) {
			    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			    		folderTree.getLastSelectedPathComponent(); // get selected node
			    	
			    	if (folderTree == null) return;
			    	TreeHandler treeHandle = new TreeHandler(folderTree, fileListModel);

			    	selectedFolder = treeHandle.getFullFolderPath(e); // get path of node
			    	treeHandle.getFolderComponents(node, getSelectedFolder()); // traverse it
			    	treeHandle.updateFolderTree(root); // finally update the tree
			    	if (fileListModel.size() > 0) fileList.setSelectedIndex(0);
			    }
			});
		}
		
		return folderTree;
	}

	/** sets basic functionality and visual outcome of tree */
	private void renderTree(final DefaultMutableTreeNode root) {
		UIManager.put("Tree.expandedIcon",  new ImageIcon("")); // disable collapse and expansion via + -
		UIManager.put("Tree.collapsedIcon", new ImageIcon(""));
		
		folderTree = new JTree(root);
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer(); // set icons (same for leaves and folders)
		ImageIcon treeIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/img/folderIcon.png")));
		
		renderer.setLeafIcon(treeIcon);
		renderer.setClosedIcon(treeIcon);
		renderer.setOpenIcon(treeIcon);
		folderTree.setCellRenderer(renderer);
	}

	/** creates listbox for found video files and adds selection listener */
	public JList getFileList() {
		if (fileList == null) {
			fileListModel = new DefaultListModel();
			fileList = new JList(fileListModel);
		}
		
		fileList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (fileList.getSelectedIndex() == -1 && inputCheck.getSelectedObjects() == null) {
						JOptionPane.showMessageDialog(null, "You haven't selected any file.");
						return;
					}
				
					initiateSearch();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		
		return fileList;
	}
	
	/** constructor of listbox for found subtitles */
	private JList getSubtitleList() {
		if (subtitleList == null) {
			this.subtitleListModel = new DefaultListModel();
			subtitleList = new JList(getSubtitleListModel());
		}
		
		subtitleList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (getSubtitleListModel().size() == 0 || subtitleList.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(null, "Nothing to download (no subtitles were found or no search performed).", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					initiateDownload();
				}
			}

			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			
		});
		
		return subtitleList;
	}

	/** constructor for fileListScrollPane */
	private JScrollPane getFileListScrollPane() {
		if (fileListScrollPane == null) {
			fileListScrollPane = new JScrollPane();
			fileListScrollPane.setViewportView(getFileList());
		}
		return fileListScrollPane;
	}
	
	/** constructor for subtitleListScrollPane */
	private JScrollPane getSubtitleListScrollPane() {
		if (subtitleListScrollPane == null) {
			subtitleListScrollPane = new JScrollPane();
			subtitleListScrollPane.setViewportView(getSubtitleList());
		}
		return subtitleListScrollPane;
	}

	/** creates search button and sets mouse listener */
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton("Search subtitles");
		}
		
		searchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (fileList.getSelectedIndex() == -1 && inputCheck.getSelectedObjects() == null) {
					JOptionPane.showMessageDialog(null, "You haven't selected any file.");
					return;
				}
				initiateSearch();
			}

			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			
		});
		
		return searchButton;
	}
	
	/** initialization of download button with listener */
	private JButton getDownloadButton() {
		if (downloadButton == null) {
			downloadButton = new JButton("Download selected subtitles");
		}
		
		downloadButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (getSubtitleListModel().size() == 0 || subtitleList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Nothing to download (no subtitles were found or no search performed).", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				initiateDownload();
			}

			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			
		});
		
		return downloadButton;
	}
	
	/** runs search thread */
	private void initiateSearch() {	
		working = new WorkingDialog(this);
		working.getCancelButton().setEnabled(false);
		working.setVisible(true);
		
		getLanguage().setSelectedIndex(languageCombo.getSelectedIndex()); // get selected language
		Callable<List<Subtitle>> task = new SearchHandler(this);
		
		searchThread = exec.submit(task);
//		working.setThread(searchThread);
	}

	/** checks for input and starts download thread */
	private void initiateDownload() {
		try {
			subtitles = getSearchThread().get(); // waiting for subtitles
		} catch (InterruptedException e) {
			logger.severe("subtitle thread interupted.");
		} catch (ExecutionException e) {
			logger.warning("cannot obtain subtitle thread result.");
		}
		
		@SuppressWarnings("unchecked")
		Callable<String> task = new DownloadHandler(subtitles, subtitleList.getSelectedIndex());
		exec.submit(task);				
	}

	public String getSelectedFolder() {
		return selectedFolder;
	}

	public DefaultListModel getSubtitleListModel() {
		return subtitleListModel;
	}

	public LanguageHandler getLanguage() {
		return language;
	}

	public WorkingDialog getWorking() {
		return working;
	}

	public Future<String> getLogInThread() {
		return logInThread;
	}

	public Future<List<Subtitle>> getSearchThread() {
		return searchThread;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
} 

