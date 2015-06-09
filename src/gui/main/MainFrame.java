package gui.main;

import gui.fin.ResultsFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;
import core.DB;

/**
 * Main frame for the project.
 * 
 * @author admin
 *
 */
public class MainFrame extends JFrame {

	private final JPanel contentPane;
	
	private JTextField searcher;
	private JTable contentTable;

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
					((MainTable) contentTable).changeSorter ("");
				} else {
					((MainTable) contentTable).changeSorter (text);
				}
				((MainTable) contentTable).repaint ();
				
				// Commands.
				checkCommands(text, e);
				
			}
		});
		
		getContentPane().add(searcher, BorderLayout.NORTH);
		
	}
	
	private void checkCommands (String text, KeyEvent e) {
		if (text.startsWith("$")) {
			searcher.setForeground(new Color(2, 132, 130));
			
			if (text.contains(DB.getPassword ()) && e.getKeyChar() == KeyEvent.VK_ENTER) {
				if (text.startsWith ("$results")) {
					setVisible(false);
					new ResultsFrame ().setVisible(true);;
				}
				
				if (text.startsWith ("$edit")) {
					setVisible(false);
					((MainTable) contentTable).changeSorter ("");
					Main.getInitFrame().setVisible(true);
				}				
			}
			
		} else {
			searcher.setForeground(new Color(0, 0, 0));
		}
	}
	
	public JTable getContentTable () {
		return contentTable;
	}
	public JTextField getSearchField () {
		return searcher;
	}

}
