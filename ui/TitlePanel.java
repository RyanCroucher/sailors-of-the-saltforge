package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constants;
import javax.swing.ImageIcon;
import java.awt.Font;

public class TitlePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		JButton buttonPlayGame = new JButton("Play");
		buttonPlayGame.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonPlayGame.setBounds(910, 216, 100, 50);
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
		
		JLabel labelBackgroundImage = new JLabel("");
		labelBackgroundImage.setIcon(new ImageIcon(TitlePanel.class.getResource("/ui/images/title.png")));
		labelBackgroundImage.setBounds(0, 0, 1920, 1080);
		add(labelBackgroundImage);

	}
}
