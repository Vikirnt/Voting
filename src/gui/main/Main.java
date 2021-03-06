package gui.main;

import core.DatabaseFile;
import gui.core.Command;
import gui.fin.ResultsFrame;
import gui.init.InitFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Main dialog which initiates the program.
 *
 * @version 1.1
 * @author vikirnt
 * @since June 2015
 */
public class Main extends JDialog {

	/** DB object. */
	private static DatabaseFile db = null;

	/** The primary frame. */
	private static JFrame frame = null;
	/** The initialising frame. */
	private static JFrame iframe = null;
	/** The results frame. */
	private static JFrame rframe = null;

	/** File chooser. */
	private static JFileChooser mainChooser = null;
	/** Location of DB in String form. */
	private static String loc = null;
	/** Text field for path */
	private static JTextField txtPath = null;

	/** Main constructor */
	private Main () {
		// Properties.
		setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
		setType (Type.UTILITY);
		setSize (350, 100);
		setResizable (false);
		setLocationRelativeTo (null);
		setAlwaysOnTop (true);
		setIconImage (new ImageIcon (Main.class.getResource ("assets/search.png")).getImage ());

		// Variables.
		loc = "";
		JPanel mainPanel;
		JButton loadButton;
		JButton rechooseButton;

		// Content pane.
		getContentPane ().setLayout (new BorderLayout (0, 0));
		mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		getContentPane ().add (mainPanel, BorderLayout.CENTER);

		// Folder path.
		JLabel lblFolder = new JLabel ("Folder:");
		lblFolder.setBounds (10, 11, 46, 14);
		mainPanel.add (lblFolder);

		// TestField for path display.
		txtPath = new JTextField ();
		txtPath.setEditable (false);
		txtPath.setBounds (66, 8, 258, 20);
		txtPath.setColumns (10);
		txtPath.setText ("No folder selected.");
		mainPanel.add (txtPath);

		// Buttons
		loadButton = new JButton (new ImageIcon (Main.class.getResource ("assets/tick.png")));
		loadButton.setBounds (140, 39, 75, 23);
		loadButton.setActionCommand (Command.LOAD.name ());
		mainPanel.add (loadButton);

		rechooseButton = new JButton ("Rechoose");
		rechooseButton.setBounds (225, 39, 99, 23);
		rechooseButton.setActionCommand (Command.RECHOOSE.name ());
		rechooseButton.setIcon (new ImageIcon (Main.class.getResource ("assets/folder-horizontal.png")));
		mainPanel.add (rechooseButton);

		// Action listener for buttons.
		ActionListener buttonActionListener = e -> {
				if (e.getActionCommand ().equals (Command.LOAD.name ()) && !loc.isEmpty ()) {
					db = new DatabaseFile (loc);
					dispose ();
					configure ();
				} else
				if (e.getActionCommand ().equals (Command.RECHOOSE.name ())) {
					choose ();
				}
		};

		loadButton.addActionListener (buttonActionListener);
		rechooseButton.addActionListener (buttonActionListener);

		// File picker.
		mainChooser = new JFileChooser (System.getenv ("APPDATA"));
		mainChooser.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY);
		mainChooser.setAcceptAllFileFilterUsed (false);
		mainChooser.setDialogTitle ("Choose a directory. No need for creating an empty one.");

		setVisible (true);
		choose ();

	}

	/** Initiates a choosing dialog. */
    private void choose () {
		if (mainChooser.showOpenDialog (this) == JFileChooser.APPROVE_OPTION) {
			loc = mainChooser.getSelectedFile ().getPath ().replace ("\\", "/");
			txtPath.setText (loc);
		} else {
			System.out.println ("No Selection!");
		}
	}

	/** Configures program. */
    private void configure () {

		frame = new MainFrame ();
		iframe = new InitFrame ();
		rframe = new ResultsFrame ();

		// Splash screen!
		final SplashScreen splash = SplashScreen.getSplashScreen ();
		if (splash == null) {
			System.out.println ("Splash image not found.");
		} else {
			try {
				splash.createGraphics ();
				Thread.sleep (500);
				splash.close ();
			} catch (Exception e) {
				System.err.println ("We have an interruption!");
			}
		}

		// Commence!
		frame.setLocationRelativeTo (this);
		frame.setVisible (true);
	}

	// -----

	/**
	 * Sets the base font. Times New Roman, because looks like a typical
	 * homework sheet. :P
	 *
	 * @param f: Custom font.
	 */
	private static void setUIFont (javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults ().keys ();
		while (keys.hasMoreElements ()) {
			Object key = keys.nextElement ();
			Object value = UIManager.get (key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put (key, f);
			}
		}
	}

	// -----

	/** @return Main frame object. */
	public static MainFrame getMainFrame () {
		return (MainFrame) frame;
	}
	/** @return Init frame object. */
	public static InitFrame getInitFrame () {
		return (InitFrame) iframe;
	}
	/** @return Fin frame object. */
	static ResultsFrame getFinFrame () {
		return (ResultsFrame) rframe;
	}

	/** @return DatabaseFile object. */
	public static DatabaseFile getDB () {
		return db;
	}

	// -----

	public static void main (String[] args) {
		// Look and feel.
		String landf = UIManager.getSystemLookAndFeelClassName ();
		// Set default look and feel and format.
		try {
			setUIFont (new javax.swing.plaf.FontUIResource (new Font ("Times New Roman", Font.PLAIN, 12)));
			UIManager.setLookAndFeel (landf);
		} catch (Exception e) {
			e.printStackTrace ();
		}
		javax.swing.SwingUtilities.invokeLater (Main::new);
	}


}
