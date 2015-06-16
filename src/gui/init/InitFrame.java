package gui.init;

import gui.main.Main;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * Frame for initialising the database.
 * 
 * @author admin
 *
 */
public class InitFrame extends JFrame {

	private final JPanel mainPanel;
	
	private final JTable contentTable;
	private final JPanel formPanel;

	/**
	 * Create the frame.
	 */
	public InitFrame() {
		
		super ("Initialise");
		
		// Properties.
		
		setIconImage(new ImageIcon (Main.class.getResource("assets/file-exe-icon.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(688, 455);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				Main.getMainFrame().setVisible (true);
			}
		});
		
		// Content panel.
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout (5, 5));
		setContentPane(mainPanel);
		
		// Form.
		
		formPanel = new InitFormPanel ();
		mainPanel.add(formPanel, BorderLayout.CENTER);
		
		// Table.
		
		contentTable = new InitTable ();
		JScrollPane tablePane = new JScrollPane(contentTable);
		mainPanel.add(tablePane, BorderLayout.WEST);
		
	}
	
	public InitTable getContentTable() {
		return (InitTable) contentTable;
	}
	public InitFormPanel getFormPanel() {
		return (InitFormPanel) formPanel;
	}
	
}
