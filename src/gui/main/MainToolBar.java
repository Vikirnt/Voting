package gui.main;

import gui.core.Action;
import gui.core.Command;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import main.Main;

public class MainToolBar extends JToolBar {

	public MainToolBar () {
		
		setFloatable(false);
		setOrientation(JToolBar.HORIZONTAL);
		setVisible(true);
		setOpaque(true);
		setLayout(new BorderLayout (5, 5));
		
		// Double click info.
		
		JLabel lblDoubleClick = new JLabel("Double click to finalize vote.");
		lblDoubleClick.setEnabled(false);
		add(lblDoubleClick, BorderLayout.WEST);
		
		JButton btnVote = new JButton ();
		btnVote.setToolTipText("Choose wisely!");
		try {
			btnVote.setIcon (new ImageIcon (Main.class.getResource ("assets/tick.png")));
		}  catch (Exception e) {
			System.err.println("Could not find tick.png");
			btnVote.setText ("VOTE!");
		}
		btnVote.setActionCommand(Command.VOTE);
		btnVote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainFrame.getContentTable().getSelectedRow () != -1) {
					Action.execute(e.getActionCommand());
				}
			}
		});
		add (btnVote, BorderLayout.EAST);
		
	}
	
}
