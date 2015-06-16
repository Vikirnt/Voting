package gui.init;

import gui.core.Action;
import gui.core.Command;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class InitFormPanel extends JPanel implements ActionListener {
	
	public final static int ADD = 111, EDIT = 222;
	
	private final JTextField txtName;
	private final JTextField txtSurname;
	private final JTextField txtPost;
	private final JTextField txtStdDiv;
	
	private final JButton primaryButton;
	private final JButton clearButton;

	/**
	 * Create the panel.
	 */
	public InitFormPanel() {
		super ();
		
		setLayout(null);
		
		// Name.
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 46, 14);
		add (lblName);
		
		txtName = new JTextField();
		txtName.setBounds(10, 36, 86, 20);
		txtName.setColumns(10);
		add(txtName);
		
		txtSurname = new JTextField();
		txtSurname.setBounds(106, 36, 94, 20);
		txtSurname.setColumns(10);
		add(txtSurname);
		
		// Post.
		JLabel lblPost = new JLabel("Post:");
		lblPost.setBounds(10, 67, 46, 14);
		add(lblPost);
		
		txtPost = new JTextField();
		txtPost.setBounds(10, 92, 190, 20);
		txtPost.setColumns(10);
		add(txtPost);
		
		// Class.
		JLabel lblClass = new JLabel("Class:");
		lblClass.setBounds(10, 123, 46, 14);
		add(lblClass);
		
		txtStdDiv = new JTextField();
		txtStdDiv.setBounds(10, 148, 190, 20);
		txtStdDiv.setColumns(10);
		add(txtStdDiv);
		
		// Enter listener.
		
		KeyListener enterLister = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					if (!checkForEmpty ()) {
						Action.execute(primaryButton.getActionCommand()); // Primary button has two commands.
					}
				}
			}
		};
		txtName.addKeyListener(enterLister);
		txtSurname.addKeyListener(enterLister);
		txtPost.addKeyListener(enterLister);
		txtStdDiv.addKeyListener(enterLister);
		
		// Add button.
		primaryButton = new JButton();
		primaryButton.setBounds(10, 179, 89, 23);
		primaryButton.addActionListener(this);
		add(primaryButton);
		
		// Clear button.
		clearButton = new JButton(new ImageIcon (Main.class.getResource("assets/cross-script.png")));
		clearButton.setToolTipText("Middle click twice to clear entire DB.");
		clearButton.setActionCommand(Command.CLEAR);
		clearButton.setBounds(109, 179, 91, 23);
		clearButton.addActionListener(this);
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON2) {
					Action.execute(Command.CLEANSLATE);
					changeFormState(ADD);
				}
			}
		});
		add(clearButton);
		
		{ // Default state. PLEASE DRAW YOUR ATTENTION HERE.
			changeFormState(ADD);			
		}
		
		// Logo for fun.
		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("assets/SchoolLogo_Alt1.png")); // load the image to a imageIcon
		Image newimg = imageIcon.getImage().getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel lblSchoolLogo = new JLabel(imageIcon);
		lblSchoolLogo.setBounds(10, 213, 190, 190);
		add(lblSchoolLogo);
	}
	
	private boolean checkForEmpty () {
		if (txtName.getText().isEmpty() || txtSurname.getText().isEmpty() || txtPost.getText().isEmpty() || txtStdDiv.getText().isEmpty())
			return true;
		else
			return false;
	}
	
	// -----
	
	public JTextField getNameField () {
		return txtName;
	}
	public JTextField getSurnameField () {
		return txtSurname;
	}
	public JTextField getPostField () {
		return txtPost;
	}
	public JTextField getStdDivField () {
		return txtStdDiv;
	}

	private void clearFields () {
		getNameField().setText("");
		getSurnameField().setText("");
		getPostField().setText("");
		getStdDivField().setText("");
	}
	
	// -----
	
	/**
	 * Changes icons and action commands around.
	 * 
	 * @param state - ADD for adding an item, EDIT for editing an item.
	 */
	public void changeFormState (int state) {
		switch (state) {
			case EDIT:
				primaryButton.setActionCommand(Command.EDIT);
				primaryButton.setText("Edit");
				primaryButton.setIcon(new ImageIcon (Main.class.getResource("assets/pencil-small.png")));
				clearButton.setText("Cancel");
				int selectedRow = Main.getInitFrame().getContentTable().getSelectedRow();
				txtName.setText(Main.getDB().getFields().getName().get(selectedRow));
				txtSurname.setText(Main.getDB().getFields().getSurname().get(selectedRow));
				txtPost.setText(Main.getDB().getFields().getPost().get(selectedRow));
				txtStdDiv.setText(Main.getDB().getFields().getStdDiv().get(selectedRow));
			break;
			case ADD:
			default:
				primaryButton.setActionCommand(Command.ADD);
				primaryButton.setText("Add");
				primaryButton.setIcon(new ImageIcon (Main.class.getResource("assets/tick.png")));
				clearButton.setText("Clear");
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Command.CLEAR)) {
			clearFields();
			getNameField().requestFocus();
			changeFormState(ADD);
		} else if (!checkForEmpty () || !e.getActionCommand().equals(Command.CLEAR)) {
			Action.execute(e.getActionCommand());
			clearFields();
		}
	}

}
