package gui.core;

import gui.main.Main;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchField extends JTextField {
	
	public SearchField (JTable ref) {
		// Properties.
		super ();
		setToolTipText ("Search");
		
		// Search icon.
		setLayout (new BorderLayout (2, 2));
		
		ImageIcon searchIcon = new ImageIcon (Main.class.getResource ("assets/search.png")); // load the image to a imageIcon
		Image newimg = searchIcon.getImage ().getScaledInstance (15, 15,  java.awt.Image.SCALE_SMOOTH); // scale the image the smooth way  
		searchIcon = new ImageIcon (newimg);  // transform it back
		add (new JLabel (searchIcon), BorderLayout.EAST);
	}
	
}
