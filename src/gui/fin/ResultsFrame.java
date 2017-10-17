package gui.fin;

import gui.main.Main;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Frame which hods the results content table.
 * Tabbed pain for experiments :3
 *
 * @version 1.1
 * @author vikirnt
 * @since June 2015
 */
public class ResultsFrame extends JFrame {
	
	/** Tabbed pane for fun and learn. */
	private final JTabbedPane tabbedPane;
	
	/** Overall content table for candidates. */
	private final JTable overallTable;
	
	public ResultsFrame () {
		super ("Results");
		
		// Properties.
		setIconImage (new ImageIcon (Main.class.getResource ("assets/fin.png")).getImage ());
		setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
		setSize (450, 450);
		setLocationRelativeTo (null);
		setAlwaysOnTop (true);
		
		// Content pane.
		tabbedPane = new JTabbedPane (JTabbedPane.TOP);
		getContentPane ().add (tabbedPane, BorderLayout.CENTER);
		
		// Overall view.
		overallTable = new OverallTable ();
		setupOverallTab ();
		
		// Default view.
		tabbedPane.setSelectedIndex (0);
		
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
	
	private void setupOverallTab () {
		JScrollPane tablePane = new JScrollPane ();
		tablePane.setViewportView (overallTable);
		tabbedPane.addTab ("Overview", new ImageIcon (Main.class.getResource ("assets/specs.png")), tablePane, "Contains all candidates and all data.");
	}
	
	@Override
	public void setVisible (boolean b) {
		super.setVisible (b);
		 ( (AbstractTableModel) overallTable.getModel ()).fireTableDataChanged ();
	}
	
}
