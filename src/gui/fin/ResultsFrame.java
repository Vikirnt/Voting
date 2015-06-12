package gui.fin;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

import main.Main;

/**
 * Frame which hods the results content table.
 * Tabbed pain for experiments :3
 * 
 * @author admin
 *
 */
public class ResultsFrame extends JFrame {
	
	private final JTabbedPane tabbedPane;
	
	private final JTable contentTable;
	
	/**
	 * Construct results frame.
	 */
	public ResultsFrame() {
		// Properties.
		super ("Resuls");
		
		setIconImage(new ImageIcon (Main.class.getResource("assets/cup.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize (450, 450);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.getMainFrame().setVisible (true);
				super.windowClosing(e);
			}
		});;
		
		// Content pane.
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		// Table pane.
		
		JScrollPane tablePane = new JScrollPane();
		tabbedPane.addTab("Votes", null, tablePane, null);
		
		contentTable = new ResultsTable();
		tablePane.setViewportView(contentTable);
	}
	
}
