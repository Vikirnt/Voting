package gui.fin;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ResultsFrame extends JFrame {
	private JTable contentTable;
	
	/**
	 * Construct results frame.
	 */
	public ResultsFrame() {
		// Properties.
		super ("Resuls");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize (450, 420);
		
		// Content pane.
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		// Table pane.
		
		JScrollPane tablePane = new JScrollPane();
		tabbedPane.addTab("Votes", null, tablePane, null);
		
		contentTable = new ResultsTable();
		tablePane.setViewportView(contentTable);
		
		// Debugging
		setVisible(true);
	}
	
}
