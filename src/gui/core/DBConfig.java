package gui.core;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import main.Main;
import core.DBFile;

public class DBConfig extends JDialog implements ActionListener {
	
	private static String NONE = "none";
	
	private JFileChooser mainChooser;
	private JTextField txtPath;
	private String loc = NONE;
	
	public DBConfig () {
		super (Main.getMainFrame(), "Select a DB.");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setSize(350, 100);
		setResizable(false);
		
		// Content pane.
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		// Folder
		
		JLabel lblFolder = new JLabel("Folder:");
		lblFolder.setBounds(10, 11, 46, 14);
		mainPanel.add(lblFolder);
		
		// TestField for path display.
		
		txtPath = new JTextField();
		txtPath.setEditable(false);
		txtPath.setBounds(66, 8, 258, 20);
		txtPath.setColumns(10);
		try {
			txtPath.setText("(╯°□°)╯︵ ┻━┻");
		} catch (Exception e) {
			txtPath.setText("No folder selected.");
		}
		mainPanel.add(txtPath);
		
		// Buttons
		
		JButton loadButton = new JButton("Load");
		loadButton.setBounds(140, 39, 75, 23);
		loadButton.setActionCommand(Command.LOAD);
		loadButton.addActionListener(this);
		loadButton.setIcon(new ImageIcon (Main.class.getResource("assets/tick.png")));
		mainPanel.add(loadButton);
		
		JButton rechooseButton = new JButton("Rechoose");
		rechooseButton.setBounds(225, 39, 99, 23);
		rechooseButton.setActionCommand(Command.RECHOOSE);
		rechooseButton.addActionListener(this);
		rechooseButton.setIcon(new ImageIcon (Main.class.getResource("assets/folder-horizontal.png")));
		mainPanel.add(rechooseButton);
		
		mainChooser = new JFileChooser(System.getenv("APPDATA"));
		mainChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		mainChooser.setAcceptAllFileFilterUsed(false);
		mainChooser.setDialogTitle("Choose a directory. No need for creating an empty one.");
		
		choose();
	}
	
	private void choose () {
		if (mainChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			System.out.println("getCurrentDirectory(): " +  mainChooser.getCurrentDirectory());
			loc = mainChooser.getCurrentDirectory ().getPath ().replace("\\", "/");
			txtPath.setText(loc);
		} else {
			System.out.println("No Selection!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case Command.LOAD:
				if (!loc.equalsIgnoreCase(NONE)) {
					Main.setDB(new DBFile (loc));
					System.out.println(loc + "/imp/cont.dat");
					dispose ();
				}
			break;
			case Command.RECHOOSE:
				choose();
			break;
		}
	}
	
	public static void main (String [] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new DBConfig().setVisible(true);
	}
}
