package main;

import gui.init.InitFrame;
import gui.main.MainFrame;

import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	public static JFrame f = null;
	
	/**
	 * Initialise Database if no items.
	 */
	public static JFrame initf = null;

	/**
	 * Main method to start the program.
	 * 
	 * @param args Startup arguments.
	 * 
	 *            -nosplash = No splash screen.
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

		initf = new InitFrame ();
		f = new MainFrame ();

		// Splash screen!
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			try {
				if (args[0].equals("-nosplash")) {
					JOptionPane.showMessageDialog(
									null,
									"SplashScreen could not be found :( I worked hard on it.",
									"Y U DO DIS TO ME?",
									JOptionPane.QUESTION_MESSAGE);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("SPLASH SCREEN NOT FOUND.");
			}
		} else {
			try {
				Thread.sleep(500);
				splash.close();
			} catch (InterruptedException e) {
				System.err.println("We have an interruption!");
			}
		}
		
		if (true) {
			initf.setVisible(true);
			initf.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					((MainFrame) f).setVisible(true);
				}
			});
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

}
