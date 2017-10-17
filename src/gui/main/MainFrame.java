package gui.main;

import gui.core.Action;
import gui.core.Command;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Main frame for the project.
 *
 * @version 1.1
 * @author vikirnt
 * @since June 2015
 */
public class MainFrame extends JFrame {

	// Search field.
	/** A text field for searching through the table. */
	private final JTextField searcher = new JTextField ();
	/** Main content table with the list of all candidates. */
	private final JTable contentTable;

	MainFrame () {
		super ("VOTE!");
		
		// Properties.
		setIconImage (new ImageIcon (Main.class.getResource ("assets/SchoolLogo.png")).getImage ());
		setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		setBounds (100, 100, 450, 420);
		setLocationRelativeTo (null);
		getContentPane ().setLayout (new BorderLayout (5, 5));
		
		// Panel.
		/* Main content panel. */
		JPanel mainPanel = new JPanel ();
		mainPanel.setBorder (new EmptyBorder (5, 5, 5, 5));
		mainPanel.setLayout (new BorderLayout (5, 5));
		setContentPane (mainPanel);
		
		// Tool bar.
		getContentPane ().add (new MainToolBar (), BorderLayout.SOUTH);
		
		// Table of candidates.
		JScrollPane scrollPane = new JScrollPane ();
		
		contentTable = new MainTable ();
		
		scrollPane.setViewportView (contentTable);
		mainPanel.add (scrollPane);

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
				
				// Commands.
				checkCommands (text, e);
				
				// Limit.
				if (text.length () > 20) {
					searcher.setText ("");
					getContentTable ().setSorter ("");
				}
			}
		});
		
		getContentPane ().add (searcher, BorderLayout.NORTH);
	}
	
	/**
	 * Check if the user has entered any commands.
	 * 
	 * How commands work:
	 *  - $ sign indicates a command.
	 *  - after a $ sign, enter the command keyword.
	 *  - then type the password.
	 * 
	 * Current commands:
	 *  - results: shows the results frame.
	 *  - edit: shows the edit frame.
	 *  - exit: exits the app.
	 *  - cleanslate: reset db.
	 * 
	 */
	private void checkCommands (String text, KeyEvent e) {
		if (text.startsWith ("$")) {
			searcher.setForeground (new Color (2, 132, 130));
			if (text.contains (core.DatabaseFile.password) && e.getKeyChar () == KeyEvent.VK_ENTER) {
				if (text.startsWith ("$results")) {
					setVisible (false);
					getContentTable ().setSorter ("");
					Main.getFinFrame ().setVisible (true);
				} else
				if (text.startsWith ("$edit")) {
					setVisible (false);
					getContentTable ().setSorter ("");
					Main.getInitFrame ().setVisible (true);
				} else
				if (text.startsWith ("$cleanslate")) {
					Action.execute (Command.CLEANSLATE);
				} else
				if (text.startsWith ("$exit")) {
					System.exit (0);
				}
			}
			
		} else {
			searcher.setForeground (new Color (0, 0, 0));
		}
	}
	
	/** Information tool bar. */
	private final class MainToolBar extends JToolBar {
		
		/** Initialising the tool bar. */
		MainToolBar () {
			super ("Information.");
			
			// Properties.
			setFloatable (false);
			setOrientation (JToolBar.HORIZONTAL);
			setVisible (true);
			setOpaque (true);
			setLayout (new BorderLayout (5, 5));
			
			// Double click info.
			JLabel lblDoubleClick = new JLabel ("Double click to finalize vote.");
			lblDoubleClick.setEnabled (false);
			add (lblDoubleClick, BorderLayout.WEST);
			
			// Vote button.
			JButton btnVote = new JButton ();
			btnVote.setToolTipText ("Choose wisely!");
			try {
				btnVote.setIcon (new ImageIcon (Main.class.getResource ("assets/tick.png")));
			}  catch (Exception e) {
				System.err.println ("Could not find tick.png");
				btnVote.setText ("VOTE!");
			}
			btnVote.setActionCommand (Command.VOTE.name ());
			btnVote.addActionListener (e -> {
                if (Main.getMainFrame ().getContentTable ().getSelectedRow () != -1) {
                    Action.execute (Command.valueOf (e.getActionCommand ()));
                }
            });
			add (btnVote, BorderLayout.EAST);
		}
		
	}
	
	/**
	 * @return Main content table.
	 */
	public MainTable getContentTable () {
		return (MainTable) contentTable;
	}
	/**
	 * @return Search text field.
	 */
	public JTextField getSearchField () {
		return searcher;
	}

}
