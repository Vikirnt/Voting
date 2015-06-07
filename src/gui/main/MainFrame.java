package gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;

/**
 * Main frame for the project.
 * 
 * @author admin
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	
	private JTextField searcher;
	private static JTable contentTable;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		super("VOTE!");
		
		// Properties.
		
		setIconImage(new ImageIcon (Main.class.getResource("assets/SchoolLogo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		
		// Layout.
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		// Tool bar.
		
		getContentPane().add (new MainToolBar (), BorderLayout.SOUTH);
		
		// Table of candidates.
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		contentTable = new MainTable();
		scrollPane.setViewportView(contentTable);
		
		// Search field.
		
		searcher = new JTextField ();
		
		searcher.setToolTipText("Search");
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
				
				if (text.startsWith("$>") && e.getKeyChar() == KeyEvent.VK_ENTER) {
					searcher.setForeground(new Color(2, 132, 130));
					
					// Commands now:
					
					if (text.equalsIgnoreCase ("$>results")) {
						System.err.println ("PRINTING RESULTS, I GUESS.");
					}
					
					if (text.equalsIgnoreCase ("$>edit")) {
						setVisible(false);
						((MainTable) contentTable).changeSorter ("");
						Main.initf.setVisible(true);
					}
					
				} else {
					searcher.setForeground(new Color(0, 0, 0));
				}
				if (text.length () > 10) {
					text.setText ("");
				}
				
			}
		});
		
		getContentPane().add(searcher, BorderLayout.NORTH);
		
	}
	
	public static JTable getContentTable () {
		return contentTable;
	}
	public JTextField getSearchField () {
		return searcher;
	}

}
