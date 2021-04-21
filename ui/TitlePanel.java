package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constants;

public class TitlePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel labelRouteName = new JLabel("Sailors of the Saltforge");
		labelRouteName.setHorizontalAlignment(SwingConstants.CENTER);
		labelRouteName.setBounds(535, 12, 289, 66);
		add(labelRouteName);
		
		JButton buttonPlayGame = new JButton("Play");
		buttonPlayGame.setBounds(561, 216, 263, 25);
		add(buttonPlayGame);
		buttonPlayGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel(PanelManager.islandPanel);
			}
		});
		
		buttonPlayGame.setOpaque(false);
		buttonPlayGame.setBackground(new Color(200,200,0,0));
		buttonPlayGame.setFocusPainted(false);
		buttonPlayGame.setForeground(new Color(255,0,0,255));
		
	}

}
