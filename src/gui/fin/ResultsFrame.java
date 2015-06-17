package gui.fin;

import gui.main.Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Frame which hods the results content table.
 * Tabbed pain for experiments :3
 * 
 * @author admin
 *
 */
public class ResultsFrame extends JFrame {
	
	/** Tabbed pane for fun and learn. */
	private final JTabbedPane tabbedPane;
	
	/** Main content table for candidates. */
	private final JTable contentTable;
	
	/**
	 * Construct results frame.
	 */
	public ResultsFrame() {
		super("Resuls");
		
		// Properties.
		setIconImage(new ImageIcon(Main.class.getResource("assets/cup.png")).getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(450, 450);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		// Content pane.
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		// Table pane.
		JScrollPane tablePane = new JScrollPane();
		tabbedPane.addTab("Votes", null, tablePane, null);
		
		contentTable = new ResultsTable();
		tablePane.setViewportView(contentTable);
		
		// Closing out.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				Main.getMainFrame().setVisible(true);
				Main.getMainFrame().getSearchField().setText("");
			}
		});
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		((AbstractTableModel) contentTable.getModel()).fireTableDataChanged();
	}
	
}
