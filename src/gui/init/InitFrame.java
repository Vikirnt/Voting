package gui.init;

import gui.main.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Frame for initialising the database.
 *
 * @version 1.1
 * @author vikirnt
 * @since June 2015
 */
public class InitFrame extends JFrame {

	/** Content table for candidate names. */
	private final JTable contentTable;
	private final JPanel formPanel;
	
	/** Search field. */
	private final JTextField searcher;

	public InitFrame () {
		super ("Initialise");
		
		// Properties.
		setIconImage (new ImageIcon (Main.class.getResource ("assets/settings.png")).getImage ());
		setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		setSize (714, 455);
		setLocationRelativeTo (null);
		setResizable (true);
		
		// Content panel.
		/* Main content panel. */
		JPanel mainPanel = new JPanel ();
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
				Main.getMainFrame ().getSearchField ().setText ("$");
			}
		});
	}
	
	/** @return Content table reference. */
	public InitTable getContentTable () {
		return (InitTable) contentTable;
	}
	/** @return Form panel reference. */
	public InitFormPanel getFormPanel () {
		return (InitFormPanel) formPanel;
	}
	
}
