package gui.main;

import gui.core.Action;
import gui.core.Command;
import gui.fin.ResultsFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import main.Main;

/**
 * Main frame for the project.
 * 
 * @author admin
 *
 */
public class MainFrame extends JFrame {

	private final JPanel contentPane;
	
	private final JTextField searcher;
	private final JTable contentTable;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		super("VOTE!");
		
		// Properties.
		
		setIconImage(new ImageIcon (Main.class.getResource("assets/SchoolLogo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		setLocationRelativeTo(null);
		
		// Layout.
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		// Tool bar.
		
		getContentPane().add (new MainToolBar (), BorderLayout.SOUTH);
		
		// Table of candidates.
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		contentTable = new MainTable();
		contentTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				searcher.requestFocus();
				searcher.setText(searcher.getText() + e.getKeyChar());
			}
		});
		scrollPane.setViewportView(contentTable);
		
		contentPane.add(scrollPane);
		
		// Search field.
		
		searcher = new JTextField ();
		searcher.setToolTipText("Search");
		searcher.setLayout(new BorderLayout(2, 2));
		
		ImageIcon searchIcon = new ImageIcon(Main.class.getResource("assets/search.png")); // load the image to a imageIcon
		Image newimg = searchIcon.getImage().getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH); // scale the image the smooth way  
		searchIcon = new ImageIcon(newimg);  // transform it back
		searcher.add(new JLabel (searchIcon), BorderLayout.EAST);
		
		searcher.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
								
				String text = searcher.getText();
				
				// Search filter.
				
				if (text.length() == 0) {
					getContentTable().changeSorter ("");
				} else {
					getContentTable().changeSorter (text);
				}
				getContentTable().repaint ();
				
				// Commands.
				checkCommands(text, e);
				
			}
		});
		
		getContentPane().add(searcher, BorderLayout.NORTH);
		
	}
	
	private void checkCommands (String text, KeyEvent e) {
		if (text.startsWith("$")) {
			searcher.setForeground(new Color(2, 132, 130));
			
			if (text.contains(Main.getDB().getPassword ()) && e.getKeyChar() == KeyEvent.VK_ENTER) {
				if (text.startsWith ("$results")) {
					setVisible(false);
					new ResultsFrame ().setVisible(true);;
				}
				
				if (text.startsWith ("$edit")) {
					setVisible(false);
					getContentTable().changeSorter ("");
					Main.getInitFrame().setVisible(true);
				}				
			}
			
		} else {
			searcher.setForeground(new Color(0, 0, 0));
		}
	}
	
	public MainTable getContentTable () {
		return (MainTable) contentTable;
	}
	public JTextField getSearchField () {
		return searcher;
	}
	
	private final class MainToolBar extends JToolBar {

		public MainToolBar () {
			
			setFloatable(false);
			setOrientation(JToolBar.HORIZONTAL);
			setVisible(true);
			setOpaque(true);
			setLayout(new BorderLayout (5, 5));
			
			// Double click info.
			
			JLabel lblDoubleClick = new JLabel("Double click to finalize vote.");
			lblDoubleClick.setEnabled(false);
			add(lblDoubleClick, BorderLayout.WEST);
			
			JButton btnVote = new JButton ();
			btnVote.setToolTipText("Choose wisely!");
			try {
				btnVote.setIcon (new ImageIcon (Main.class.getResource ("assets/tick.png")));
			}  catch (Exception e) {
				System.err.println("Could not find tick.png");
				btnVote.setText ("VOTE!");
			}
			btnVote.setActionCommand(Command.VOTE);
			btnVote.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.getMainFrame().getContentTable().getSelectedRow () != -1) {
						Action.execute(e.getActionCommand());
					}
				}
			});
			add (btnVote, BorderLayout.EAST);
			
		}
		
	}

}
