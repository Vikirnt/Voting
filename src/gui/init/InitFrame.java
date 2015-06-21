package gui.init;

import gui.main.Main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Frame for initialising the database.
 * 
 * @author admin
 *
 */
public class InitFrame extends JFrame {

	/** Main content panel. */
	private final JPanel mainPanel;
	
	/** Content table for candidate names. */
	private final JTable contentTable;
	private final JPanel formPanel;
	
	/** Search field. */
	private final JTextField searcher;

	/**
	 * Create the frame.
	 */
	public InitFrame () {
		super ("Initialise");
		
		// Properties.
		setIconImage (new ImageIcon (Main.class.getResource ("assets/settings.png")).getImage ());
		setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		setSize (714, 455);
		setLocationRelativeTo (null);
		setResizable (true);
		
		// Content panel.
		mainPanel = new JPanel ();
		mainPanel.setBorder (new EmptyBorder (5, 5, 5, 5));
		mainPanel.setLayout (new BorderLayout (5, 5));
		setContentPane (mainPanel);
		
		// Form.
		formPanel = new InitFormPanel ();
		mainPanel.add (formPanel, BorderLayout.EAST);
		
		// Table panel.
		JPanel tablePanel = new JPanel (new BorderLayout (5, 5));
		mainPanel.add (tablePanel, BorderLayout.CENTER);
		
		// Table.
		contentTable = new InitTable ();
		JScrollPane tableScrollPane = new JScrollPane (contentTable);
		tablePanel.add (tableScrollPane, BorderLayout.CENTER);
		
		// Table search bar.
		searcher = new JTextField ();
		searcher.setToolTipText ("Search");
		searcher.setLayout (new BorderLayout (2, 2));
		
		ImageIcon searchIcon = new ImageIcon (Main.class.getResource ("assets/search.png")); // load the image to a imageIcon
		Image newimg = searchIcon.getImage ().getScaledInstance (15, 15,  java.awt.Image.SCALE_SMOOTH); // scale the image the smooth way  
		searchIcon = new ImageIcon (newimg);  // transform it back
		searcher.add (new JLabel (searchIcon), BorderLayout.EAST);
		
		searcher.addKeyListener (new KeyAdapter () {
			@Override
			public void keyTyped (KeyEvent e) {
								
				String text = searcher.getText ();
				
				// Search filter.
				if (text.length () == 0) {
					getContentTable ().setSorter ("");
				} else {
					getContentTable ().setSorter (text);
				}
				getContentTable ().repaint ();
				
				// Limit.
				if (text.length () > 20) {
					searcher.setText ("");
					getContentTable ().setSorter ("");
				}
				
			}
		});
		
		tablePanel.add (searcher, BorderLayout.NORTH);
		
		// Closing out.
		addWindowListener (new WindowAdapter () {
			@Override
			public void windowClosing (WindowEvent e) {
				super.windowClosing (e);
				Main.getMainFrame ().setVisible (true);
				Main.getMainFrame ().getSearchField ().setText ("");
			}
		});
	}
	
	/** Returns content table reference. */
	public InitTable getContentTable () {
		return (InitTable) contentTable;
	}
	/** Returns form panel reference. */
	public InitFormPanel getFormPanel () {
		return (InitFormPanel) formPanel;
	}
	
}
