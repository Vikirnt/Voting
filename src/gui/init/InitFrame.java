package gui.init;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import main.Main;

public class InitFrame extends JFrame {

	private JPanel contentPane;
	
	private static JTable contentTable;
	private static JPanel formPane;

	/**
	 * Create the frame.
	 */
	public InitFrame() {
		
		super ("Initialise");
		
		// Properties.
		
		setIconImage(new ImageIcon (Main.class.getResource("assets/file-exe-icon.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 688, 455);
		setResizable(false);
		
		// Layout.
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout (5, 5));
		setContentPane(contentPane);
		
		// Table.
		
		contentTable = new InitTable ();
		JScrollPane tablePane = new JScrollPane(contentTable);
		
		contentPane.add(tablePane, BorderLayout.WEST);
		
		// Form.
		
		formPane = new InitForm ();
		contentPane.add(formPane, BorderLayout.CENTER);
		
	}
	
	public static JTable getContentTable () {
		return contentTable;
	}
	public static JPanel getFormPane() {
		return formPane;
	}
	
}
