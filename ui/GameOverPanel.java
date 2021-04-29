package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import events.RandomEvent;
import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Player;
import main.Route;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextArea;
import java.awt.Insets;

public class GameOverPanel extends JPanel {
	
	private static JLabel labelGameOver;
	private static JTextArea textAreaStatistics;
	private static JButton buttonNext;
	
	private static RandomEvent event;

	/**
	 * Create the panel.
	 */
	public GameOverPanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelGameOver = new JLabel("GAME OVER");
		labelGameOver.setForeground(Color.RED);
		labelGameOver.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		labelGameOver.setBounds(660, 30, 600, 50);
		add(labelGameOver);
		
		textAreaStatistics = new JTextArea("");
		textAreaStatistics.setWrapStyleWord(true);
		textAreaStatistics.setOpaque(true);
		textAreaStatistics.setMargin(new Insets(15, 15, 15, 15));
		textAreaStatistics.setLineWrap(true);
		textAreaStatistics.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaStatistics.setEditable(false);
		textAreaStatistics.setBackground(new Color(255, 255, 255, 175));
		textAreaStatistics.setBounds(460, 95, 1000, 730);
		//fixes selected text highlighting bug
		textAreaStatistics.getCaret().deinstall(textAreaStatistics);
		add(textAreaStatistics);
		
		buttonNext = new JButton("NEXT");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel("IslandPanel");
			}
		});
		buttonNext.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonNext.setOpaque(false);
		buttonNext.setForeground(Color.RED);
		buttonNext.setFont(new Font("Lato Black", Font.PLAIN, 60));
		buttonNext.setFocusPainted(false);
		buttonNext.setBackground(new Color(200, 200, 0, 0));
		buttonNext.setBounds(1490, 915, 400, 100);
		add(buttonNext);
		
		JLabel labelBackground = new JLabel("");
		labelBackground.setIcon(new ImageIcon(GameOverPanel.class.getResource("/ui/images/endGame.png")));
		labelBackground.setBounds(0, 0, 1920, 1080);
		add(labelBackground);

	}
	

	
	public static void updateDetails(int endGameCode) {

		textAreaStatistics.setText(GameEnvironment.endgameStats(endGameCode));
		
	}
}
