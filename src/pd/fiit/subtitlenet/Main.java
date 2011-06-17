package pd.fiit.subtitlenet;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pd.fiit.gui.GUI;

/** you should run this to get proper functionality */
public class Main {
	private String version = "v0.9.7a";

	public static void main(String args[]) {
		setLookAndFeel();
		new GUI();
	}

	public String getVersion() { return this.version; }
	
	private static void setLookAndFeel() { 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} 
	}
}
