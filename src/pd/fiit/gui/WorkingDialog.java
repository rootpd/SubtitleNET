package pd.fiit.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.util.concurrent.Future;
import javax.swing.JLabel;

/** dialog shown while connecting to server and processing search */
public final class WorkingDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cancelButton = null;
	private JLabel textLabel = null;

	/**
	 * @param owner
	 */
	public WorkingDialog(GUI gui) {
		super(gui);
		initialize();
		this.setLocationRelativeTo(gui);
	}
	
//	@SuppressWarnings("rawtypes")
//	public void setThread(Future thread) {
//		this.thread = thread;
//	}
	
	public void setMessage(String message) {
		this.textLabel.setText(message);
		this.textLabel.repaint();
	}

	private void initialize() {
		this.setSize(312, 100);
		this.setContentPane(getJContentPane());
		this.setTitle("Working...");
		this.setAlwaysOnTop(true);
		this.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.ICONIFIED);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 * 
	 * Set visible for JPanel in swing thread, very 
	 * important for calling this method from other thread
	 */
	@Override
	public void setVisible(final boolean b) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setVisibleParent(b);
			}
		});
	}
	
	private void setVisibleParent(boolean b){
		super.setVisible(b);
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			textLabel = new JLabel();
			textLabel.setBounds(new Rectangle(40, 6, 229, 25));
			textLabel.setHorizontalAlignment(SwingConstants.CENTER);
			textLabel.setVerticalAlignment(SwingConstants.CENTER);
			
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(textLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(84, 36, 141, 26));
			cancelButton.setText("Cancel");
			cancelButton.setEnabled(false);
		}
		
		cancelButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO: cancelation possibility
			}

			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			
		});
		
		return cancelButton;
	}

}
