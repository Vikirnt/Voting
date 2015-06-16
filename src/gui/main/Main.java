package gui.main;

import gui.core.Command;
import gui.init.InitFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import core.DBFile;

/**
 * Main dialog which initiates the program.
 * 
 * @author Vikrant.
 *
 */
public class Main extends JDialog {
	
	/** DB object. */
	private static File db = null;
	
	/** The primary frame. */
	private static JFrame frame = null;
	/** The initialising frame. */
	private static JFrame iframe= null;
	
	/** File chooser. */
	private static JFileChooser mainChooser = null;
	/** Location of DB in String form. */
	private static String loc = null;
	/** Text field for path */
	private static JTextField txtPath = null;
	
	/** Main constructor */
	public Main() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setSize(350, 100);
		setResizable(false);
		setLocationRelativeTo(null);
			
		// Variables.
		loc = "";
		JPanel mainPanel = null;
		JButton loadButton = null;
		JButton rechooseButton = null;
		
		// Content pane.
		getContentPane().setLayout(new BorderLayout(0, 0));
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		// Folder path.
		JLabel lblFolder = new JLabel("Folder:");
		lblFolder.setBounds(10, 11, 46, 14);
		mainPanel.add(lblFolder);
		
		// TestField for path display.
		txtPath = new JTextField();
		txtPath.setEditable(false);
		txtPath.setBounds(66, 8, 258, 20);
		txtPath.setColumns(10);
		txtPath.setText("No folder selected.");
		mainPanel.add(txtPath);
		
		// Buttons
		loadButton = new JButton(new ImageIcon(Main.class.getResource("assets/tick.png")));
		loadButton.setBounds(140, 39, 75, 23);
		loadButton.setActionCommand(Command.LOAD);
		mainPanel.add(loadButton);
		
		rechooseButton = new JButton("Rechoose");
		rechooseButton.setBounds(225, 39, 99, 23);
		rechooseButton.setActionCommand(Command.RECHOOSE);
		rechooseButton.setIcon(new ImageIcon(Main.class.getResource("assets/folder-horizontal.png")));
		mainPanel.add(rechooseButton);
		
		// Action listener for buttons.
		ActionListener buttonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()) {
					case Command.LOAD:
						if(!loc.isEmpty()) {
							setDB(new DBFile(loc));
							dispose();
							configure();
						}
					break;
					case Command.RECHOOSE:
						choose();
					break;
				}
			}
		};
		
		loadButton.addActionListener(buttonActionListener);
		rechooseButton.addActionListener(buttonActionListener);
		
		// File picker.
		mainChooser = new JFileChooser(System.getenv("APPDATA"));
		mainChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		mainChooser.setAcceptAllFileFilterUsed(false);
		mainChooser.setDialogTitle("Choose a directory. No need for creating an empty one.");
		
		setVisible(true);
		choose();
		
	}
	
	/** Initiates a choosing dialog. */
	public void choose() {
		if(mainChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			loc = mainChooser.getSelectedFile().getPath().replace("\\", "/");
			txtPath.setText(loc);
		} else {
			System.out.println("No Selection!");
		}
	}
	
	/** Configures program. */
	public void configure() {
		// Initialise items.
		getDB().initialiseFile();
		getDB().getFields().load();

		frame = new MainFrame();
		iframe = new InitFrame();

		// Splash screen!
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if(splash == null) {
			System.err.println("SPLASH SCREEN NOT FOUND.");
		} else {
			try {
				splash.createGraphics();
				Thread.sleep(500);
				splash.close();
			} catch(Exception e) {
				System.err.println("We have an interruption!");
			}
		}
		
		// Commence!
		frame.setLocationRelativeTo(this);
		frame.setVisible(true);
	}
	
	// -----

	/**
	 * Sets the base font. Times New Roman, because looks like a typical
	 * homework sheet. :P
	 * 
	 * @param f
	 */
	private static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}
	
	// -----
	
	public static MainFrame getMainFrame() {
		return(MainFrame) frame;
	}
	public static InitFrame getInitFrame() {
		return(InitFrame) iframe;
	}
	
	public static DBFile getDB() {
		return(DBFile) db;
	}
	public static void setDB(File f) {
		db = f;
	}
	
	// -----
	
	public static void main(String[] args) {
		// Set default look and feel and format.
		try {
			setUIFont(new javax.swing.plaf.FontUIResource(new Font("Times New Roman", Font.PLAIN, 12)));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		new Main();
	}

}