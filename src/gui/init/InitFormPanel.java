package gui.init;

import gui.core.Action;
import gui.core.Command;
import gui.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Form panel for adding new items.
 *
 * @version 1.1
 * @author admin
 * @since June 2015
 */
public class InitFormPanel extends JPanel implements ActionListener {

	/** Reference constants. */
	public final static int
		ADD = 111,
		EDIT = 222;
	
	private final JTextField
		txtName,
		txtSurname,
		txtPost,
		txtStdDiv;
	
	private final JButton
		primaryButton,
		clearButton;

	/**
	 * Create the panel.
	 */
	InitFormPanel () {
		super ();
		
		// Properties.
		setLayout (null);
		setPreferredSize (new Dimension (230, 400));
		setBorder (BorderFactory.createTitledBorder ("Candidate"));
		setBackground (new Color (255, 255, 255));
		
		// Name.
		txtName = new JTextField ();
		txtName.setBounds (16, 27, 90, 40);
		txtName.setColumns (10);
		txtName.setBorder (BorderFactory.createTitledBorder ("Name"));
		add (txtName);
		
		txtSurname = new JTextField ();
		txtSurname.setBounds (116, 27, 90, 40);
		txtSurname.setColumns (10);
		txtSurname.setBorder (BorderFactory.createTitledBorder ("Last name"));
		add (txtSurname);
		
		// Post.
		txtPost = new JTextField ();
		txtPost.setBounds (16, 78, 190, 40);
		txtPost.setColumns (10);
		txtPost.setBorder (BorderFactory.createTitledBorder ("Post"));
		add (txtPost);
		
		// Class
		txtStdDiv = new JTextField ();
		txtStdDiv.setBounds (16, 129, 190, 40);
		txtStdDiv.setColumns (10);
		txtStdDiv.setBorder (BorderFactory.createTitledBorder ("Class"));
		add (txtStdDiv);
		
		// Enter listener.
		KeyListener enterLister = new KeyAdapter () {
			@Override
			public void keyTyped (KeyEvent e) {
				super.keyTyped (e);
				if (e.getKeyChar () == KeyEvent.VK_ENTER) {
					if (checkForEmpty ()) {
						Action.execute (Command.valueOf (primaryButton.getActionCommand ())); // Primary button has two commands.
						txtName.requestFocusInWindow ();
					}
				}
			}
		};
		txtName.addKeyListener (enterLister);
		txtSurname.addKeyListener (enterLister);
		txtPost.addKeyListener (enterLister);
		txtStdDiv.addKeyListener (enterLister);
		
		// Add button.
		primaryButton = new JButton ();
		primaryButton.setBounds (16, 180, 90, 25);
		primaryButton.addActionListener (this);
		add (primaryButton);
		
		// Clear button.
		clearButton = new JButton (new ImageIcon (Main.class.getResource ("assets/cross-script.png")));
		clearButton.setToolTipText ("Middle click twice to clear entire DB.");
		clearButton.setActionCommand (Command.CLEAR.name ());
		clearButton.setBounds (116, 180, 90, 25);
		clearButton.addActionListener (this);
		clearButton.addMouseListener (new MouseAdapter () {
			@Override
			public void mouseClicked (MouseEvent e) {
				super.mouseClicked (e);
				if (e.getClickCount () == 2 && e.getButton () == MouseEvent.BUTTON2) {
					Action.execute (Command.CLEANSLATE);
					changeFormState (ADD);
					txtName.requestFocusInWindow ();
				}
			}
		});
		add (clearButton);
		
		{ // Default state. PLEASE DRAW YOUR ATTENTION HERE.
			changeFormState (ADD);			
		}
		
		// Logo for fun.
		try {
			ImageIcon imageIcon = new ImageIcon (Main.class.getResource ("assets/SchoolLogoAlt.png")); // load the image to a imageIcon
			Image newimg = imageIcon.getImage ().getScaledInstance (120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon (newimg);  // transform it back
			
			JLabel lblSchoolLogo = new JLabel (imageIcon);
			lblSchoolLogo.setBounds (16, 216, 190, 173);
			add (lblSchoolLogo);
			
		} catch (NullPointerException e) {
			System.err.println ("Logo not found. No issues.");
		}
		
	}
	
	/** Checks if text fields are empty. */
	private boolean checkForEmpty () {
		return !txtName.getText ().isEmpty () && !txtSurname.getText ().isEmpty () && !txtPost.getText ().isEmpty () && !txtStdDiv.getText ().isEmpty ();
	}
	
	// -----
	
	/** @return Name field reference. */
	public JTextField getNameField () {
		return txtName;
	}
	/** @return Surname field reference. */
	public JTextField getSurnameField () {
		return txtSurname;
	}
	/** @return Post field reference. */
	public JTextField getPostField () {
		return txtPost;
	}
	/** @return StdDiv field reference. */
	public JTextField getStdDivField () {
		return txtStdDiv;
	}
	
	/** Clears all fields. */
	public void clearFields () {
		getNameField ().setText ("");
		getSurnameField ().setText ("");
		getPostField ().setText ("");
		getStdDivField ().setText ("");
	}
	
	// -----
	
	/**
	 * Changes icons and action commands around.
	 * 
	 * @param state: ADD for adding an item, EDIT for editing an item.
	 */
	public void changeFormState (int state) {
		switch (state) {
			case EDIT:
				primaryButton.setActionCommand (Command.EDIT.name ());
				primaryButton.setText ("Edit");
				primaryButton.setIcon (new ImageIcon (Main.class.getResource ("assets/pencil-small.png")));
				clearButton.setText ("Cancel");

				int selectedRow = Action.getPos (Main.getInitFrame ().getContentTable ());
				txtName.setText 	(Main.getDB ().getCandidate (selectedRow).getFirstName ());
				txtSurname.setText 	(Main.getDB ().getCandidate (selectedRow).getLastName ());
				txtPost.setText 	(Main.getDB ().getCandidate (selectedRow).getPost ());
				txtStdDiv.setText 	(Main.getDB ().getCandidate (selectedRow).getStddiv ());
			break;

			case ADD:
			default:
				primaryButton.setActionCommand (Command.ADD.name ());
				primaryButton.setText ("Add");
				primaryButton.setIcon (new ImageIcon (Main.class.getResource ("assets/tick.png")));
				clearButton.setText ("Clear");
			break;
		}
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if (checkForEmpty () || Command.valueOf (e.getActionCommand ()) == Command.CLEAR)
			Action.execute (Command.valueOf (e.getActionCommand ()));
	}
}
