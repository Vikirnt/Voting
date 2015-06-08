package gui.init;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		setSize(688, 455);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				Main.getFrame().setVisible (true);
			}
		});
		
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
