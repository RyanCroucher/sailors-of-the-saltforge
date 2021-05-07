package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Constants;
import main.GameEnvironment;

import java.awt.Font;

import javax.swing.JTextArea;
import java.awt.Insets;

public class GameOverPanel extends JPanel {
	
	private static JLabel labelGameOver;
	private static JTextArea textAreaStatistics;
	private static JButton buttonExit;

	/**
	 * Create the panel.
	 */
	public GameOverPanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		constructTexts();
		
		constructExitButton();
		
		setBackgroundImage();

	}
	
	/**
	 * Build all labels and text areas
	 */
	private void constructTexts() {
		
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
		
	}
	
	/**
	 * Creates the button to exit the game
	 */
	private void constructExitButton() {
		
		buttonExit = new JButton("EXIT GAME");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		buttonExit.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonExit.setOpaque(false);
		buttonExit.setForeground(Color.RED);
		buttonExit.setFont(new Font("Lato Black", Font.PLAIN, 60));
		buttonExit.setFocusPainted(false);
		buttonExit.setBackground(new Color(200, 200, 0, 0));
		buttonExit.setBounds(1490, 915, 400, 100);
		add(buttonExit);
		
	}
	
	/**
	 * Sets the panel's background picture
	 */
	private void setBackgroundImage() {
		
		JLabel labelBackground = new JLabel("");
		labelBackground.setIcon(new ImageIcon(GameOverPanel.class.getResource("/ui/images/endGame.png")));
		labelBackground.setBounds(0, 0, 1920, 1080);
		add(labelBackground);
		
	}
	
	/**
	 * Sets the end-game description to match the reason the game ended
	 * @param endGameCode the type of game over that occurred
	 */
	public static void updateDetails(int endGameCode) {
		textAreaStatistics.setText(GameEnvironment.endgameStats(endGameCode));
	}
}
