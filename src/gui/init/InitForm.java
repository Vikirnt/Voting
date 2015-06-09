package gui.init;

import gui.core.Action;
import gui.core.Command;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Main;

/**
 * Form panel for adding new items.
 * 
 * @author admin
 *
 */
public class InitForm extends JPanel implements ActionListener {
	
	private static JTextField txtName;
	private static JTextField txtSurname;
	private static JTextField txtPost;
	private static JTextField txtClass;
	
	public static JLabel lblCandidatesCount;
	

	/**
	 * Create the panel.
	 */
	public InitForm() {
		super (null);
		
		// Name.
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 46, 14);
		add (lblName);
		
		txtName = new JTextField();
		txtName.setBounds(10, 36, 86, 20);
		add(txtName);
		txtName.setColumns(10);
		
		txtSurname = new JTextField();
		txtSurname.setBounds(106, 36, 94, 20);
		add(txtSurname);
		txtSurname.setColumns(10);
		
		// Post.
		JLabel lblPost = new JLabel("Post:");
		lblPost.setBounds(10, 67, 46, 14);
		add(lblPost);
		
		txtPost = new JTextField();
		txtPost.setBounds(10, 92, 190, 20);
		add(txtPost);
		txtPost.setColumns(10);
		
		// Class.
		JLabel lblClass = new JLabel("Class:");
		lblClass.setBounds(10, 123, 46, 14);
		add(lblClass);
		
		txtClass = new JTextField();
		txtClass.setBounds(10, 148, 190, 20);
		add(txtClass);
		txtClass.setColumns(10);
		
		// Enter listener.
		
		KeyListener enterLister = new EnterLister();
		txtName.addKeyListener(enterLister);
		txtSurname.addKeyListener(enterLister);
		txtPost.addKeyListener(enterLister);
		txtClass.addKeyListener(enterLister);
		
		// Add button.
		JButton addButton = new JButton(new ImageIcon (Main.class.getResource("assets/tick.png")));
		addButton.setText("Add");
		addButton.setActionCommand(Command.ADD);
		addButton.setBounds(10, 179, 89, 23);
		addButton.addActionListener(this);
		add(addButton);
		
		// Cancel button.
		JButton cancelButton = new JButton(new ImageIcon (Main.class.getResource("assets/cross-script.png")));
		cancelButton.setText("Clear");
		cancelButton.setActionCommand(Command.CLEAR);
		cancelButton.setBounds(109, 179, 91, 23);
		cancelButton.addActionListener(this);
		add(cancelButton);
		
		// Logo for fun.
		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("assets/SchoolLogo_Alt1.png")); // load the image to a imageIcon
		Image newimg = imageIcon.getImage().getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel lblSchoolLogo = new JLabel(imageIcon);
		lblSchoolLogo.setBounds(10, 213, 190, 190);
		add(lblSchoolLogo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!checkForEmpty () || e.getActionCommand().equals(Command.CLEAR)) {
			Action.execute(e.getActionCommand());
		}
	}
	
	private static boolean checkForEmpty () {
		if (txtName.getText().isEmpty() || txtSurname.getText().isEmpty() || txtPost.getText().isEmpty() || txtClass.getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static JTextField getNameField () {
		return txtName;
	}
	public static JTextField getSurnameField () {
		return txtSurname;
	}
	public static JTextField getPostField () {
		return txtPost;
	}
	public static JTextField getClassField () {
		return txtClass;
	}
	
	private class EnterLister extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			super.keyTyped(e);
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				if (!checkForEmpty ()) {
					Action.execute(Command.ADD);
				}
			}
		}
	}
}
