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

/**
 * The first panel of the game, displayed on startup
 * @author Ryan Croucher rcr69
 *
 */
public class TitlePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		//starts the game
		JButton buttonPlayGame = new JButton("PLAY");
		buttonPlayGame.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonPlayGame.setFont(new Font("Lato Black", Font.PLAIN, 60));
		buttonPlayGame.setBounds(810, 200, 300, 100);
		add(buttonPlayGame);
		buttonPlayGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel("CharacterCreatePanel");
			}
		});
		
		buttonPlayGame.setOpaque(false);
		buttonPlayGame.setBackground(new Color(200,200,0,0));
		buttonPlayGame.setFocusPainted(false);
		buttonPlayGame.setForeground(new Color(255,0,0,255));
		
		//authors
		JLabel labelCreatedBy = new JLabel("Created by Ryan Croucher and Steven Johnson");
		labelCreatedBy.setForeground(Color.RED);
		labelCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		labelCreatedBy.setFont(new Font("Lato Black", Font.PLAIN, 20));
		labelCreatedBy.setBounds(660, 120, 600, 100);
		add(labelCreatedBy);
		
		//background image
		JLabel labelBackgroundImage = new JLabel("");
		labelBackgroundImage.setIcon(new ImageIcon(TitlePanel.class.getResource("/ui/images/title.png")));
		labelBackgroundImage.setBounds(0, 0, 1920, 1080);
		add(labelBackgroundImage);

	}
}
