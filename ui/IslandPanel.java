package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class IslandPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public IslandPanel() {

		setBounds(0, 0, 1280, 720);
		setLayout(null);
		
		JLabel labelRouteName = new JLabel("The Saltforge");
		labelRouteName.setHorizontalAlignment(SwingConstants.CENTER);
		labelRouteName.setBounds(535, 12, 289, 66);
		add(labelRouteName);
		
		JButton buttonToIsland = new JButton("Travel via the Tranquil Expanse");
		buttonToIsland.setBounds(561, 216, 263, 25);
		add(buttonToIsland);
		buttonToIsland.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel(PanelManager.routePanel);
			}
		});
		
		buttonToIsland.setOpaque(false);
		buttonToIsland.setBackground(new Color(200,200,0,0));
		buttonToIsland.setFocusPainted(false);
		buttonToIsland.setForeground(new Color(255,0,0,255));
		
		
		JLabel labelRouteDescription = new JLabel("The salt forge is where the dwarves live.");
		labelRouteDescription.setBounds(588, 139, 298, 15);
		add(labelRouteDescription);
		
	}

}
