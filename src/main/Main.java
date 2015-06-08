package main;

import gui.init.InitFrame;
import gui.main.MainFrame;

import java.awt.Font;
import java.awt.SplashScreen;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import core.DB;

/**
 * Initializer.
 * 
 * @author Vikrant.
 *
 */

public class Main {

	/**
	 * The primary frame.
	 */
	private static JFrame frame = null;

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

		// Check database existance.
		if (DB.checkExistance()) {
			DB.getFields().load();
		} else {
			JOptionPane.showMessageDialog(null,
					"Could not find database.\n New database created.",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
		}

		frame = new MainFrame ();

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
		
		if (DB.getFields().getItemsCount() < 0) {
			JFrame initf = new InitFrame();
			initf.setVisible(true);
		} else {
			frame.setVisible(true);
		}
		
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
	
	/**
	 * Get mainframe.
	 */
	public static JFrame getFrame () {
		return frame;
	}

}
