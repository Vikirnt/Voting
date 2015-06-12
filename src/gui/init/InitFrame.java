package gui.init;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import main.Main;

/**
 * Frame for initialising the database.
 * 
 * @author admin
 *
 */
public class InitFrame extends JFrame {

	private final JPanel contentPane;
	
	private final JTable contentTable;
	private final JPanel formPane;

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
		
		// Layout.
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout (5, 5));
		setContentPane(contentPane);
		
		// Form.
		
		formPane = new InitForm ();
		contentPane.add(formPane, BorderLayout.CENTER);
		
		// Table.
		
		contentTable = new InitTable ();
		contentTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				getFormPane().getNameField().requestFocus();
				getFormPane().getNameField().setText(((InitForm) formPane).getNameField().getText() + e.getKeyChar());
			}
		});
		
		JScrollPane tablePane = new JScrollPane(contentTable);
		contentPane.add(tablePane, BorderLayout.WEST);
		
	}
	
	public InitTable getContentTable () {
		return (InitTable) contentTable;
	}
	public InitForm getFormPane() {
		return (InitForm) formPane;
	}
	
}
