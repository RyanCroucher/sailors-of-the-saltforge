package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constants;

public class IslandPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public IslandPanel() {

		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel labelIslandName = new JLabel("The Saltforge");
		labelIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		labelIslandName.setBounds(535, 12, 289, 66);
		add(labelIslandName);
		
		JButton buttonToRoute = new JButton("Travel via the Tranquil Expanse");
		buttonToRoute.setBounds(561, 216, 263, 25);
		add(buttonToRoute);
		buttonToRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel(PanelManager.routePanel);
			}
		});
		
		buttonToRoute.setOpaque(false);
		buttonToRoute.setBackground(new Color(200,200,0,0));
		buttonToRoute.setFocusPainted(false);
		buttonToRoute.setForeground(new Color(255,0,0,255));
		
		
		JLabel labelIslandDescription = new JLabel("The salt forge is where the dwarves live.");
		labelIslandDescription.setBounds(588, 139, 298, 15);
		add(labelIslandDescription);
		
	}

}
