package main;

import gui.init.InitFrame;
import gui.main.MainFrame;

import java.awt.Font;
import java.awt.SplashScreen;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;

import core.DBFile;

/**
 * Initializer.
 * 
 * @author Vikrant.
 *
 */

public class Main {
	
	/**
	 * DB object.
	 */
	private static File db = null;
	
	/**
	 * The primary frame.
	 */
	private static JFrame frame = null;
	/**
	 * The initialising frame.
	 */
	private static JFrame iframe= null;

	/**
	 * Main method to start the program.
	 * 
	 * @param args Startup arguments.
	 * 			-nosplash = No splash screen.
	 */
	public static void main(String[] args) {
		
		// Set default look and feel and format.
		try {
			setUIFont(new javax.swing.plaf.FontUIResource(new Font("Times New Roman", Font.PLAIN, 12)));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Initialise items.
		db = new DBFile ("cont.dat");
		getDB().initialiseFile();
		getDB().getFields().load();

		frame = new MainFrame ();
		iframe = new InitFrame();

		// Splash screen!
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			System.err.println("SPLASH SCREEN NOT FOUND.");
		} else {
			try {
				splash.createGraphics();
				Thread.sleep(500);
				splash.close();
			} catch (Exception e) {
				System.err.println("We have an interruption!");
			}
		}
		
		// Frame.
		
		iframe.setVisible(false);
		frame.setVisible(true);
		
	}

	/**
	 * Sets the base font. Times New Roman, because looks like a typical
	 * homework sheet. :P
	 * 
	 * @param f
	 */
	private static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}
	
	// -----
	
	public static MainFrame getMainFrame () {
		return (MainFrame) frame;
	}
	public static InitFrame getInitFrame() {
		return (InitFrame) iframe;
	}
	public static DBFile getDB() {
		return (DBFile) db;
	}

}
