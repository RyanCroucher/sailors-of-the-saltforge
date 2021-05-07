package ui;

import javax.swing.JPanel;

import main.Constants;
import main.Constants.ShipModel;
import main.GameEnvironment;
import main.Player;
import main.Ship;

import javax.swing.JLabel;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;

/**
 * GUI Panel to handle character creation and initiating the game
 * @author Ryan Croucher rcr69
 *
 */
public class CharacterCreatePanel extends JPanel {
	
	//to enter a character name
	private JTextField textFieldChooseName;
	
	//to display errors with entered data during character creation
	private JLabel labelErrorMessage;
	
	//slider bar to choose game duration
	private JSlider sliderGameDuration;

	/**
	 * Create the panel.
	 */
	public CharacterCreatePanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		constructStaticTexts();
		
		//text field to type a name
		textFieldChooseName = new JTextField();
		textFieldChooseName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldChooseName.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textFieldChooseName.setBounds(1340, 240, 250, 40);
		add(textFieldChooseName);
		textFieldChooseName.setColumns(10);
		
		constructDurationPromptAndSlider();
		
		constructButtonAndRadioButtons();
		
		//label to display error messages
		labelErrorMessage = new JLabel("");
		labelErrorMessage.setForeground(Color.RED);
		labelErrorMessage.setBackground(Color.WHITE);
		labelErrorMessage.setFont(new Font("Lato Black", Font.PLAIN, 16));
		labelErrorMessage.setBounds(1493, 866, 400, 35);
		add(labelErrorMessage);
		
		setBackgroundImage();
	}
	
	/**
	 * Builds all of the unchanging labels and text fields
	 */
	private void constructStaticTexts() {
		
		//the greeting text on the left
		JTextArea textAreaPrimer = new JTextArea(Constants.PRIMER);
		textAreaPrimer.setWrapStyleWord(true);
		textAreaPrimer.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaPrimer.setLineWrap(true);
		textAreaPrimer.setEditable(false);
		textAreaPrimer.setBounds(100, 200, 500, 300);
		textAreaPrimer.setBackground(new Color(255,255,255,175));
		textAreaPrimer.setMargin(new Insets(15,15,15,15));
		//fixes selected text highlighting bug
		textAreaPrimer.getCaret().deinstall(textAreaPrimer);
		add(textAreaPrimer);
		textAreaPrimer.setOpaque(true);
		
		//The title at the top middle
		JLabel labelPanelTitle = new JLabel("Create your character");
		labelPanelTitle.setForeground(Color.RED);
		labelPanelTitle.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelPanelTitle.setBounds(720, 50, 480, 75);
		add(labelPanelTitle);
		
		//the prompt to enter a name
		JLabel labelChooseName = new JLabel("What is your name, proud dwarf merchant?");
		labelChooseName.setFont(new Font("Lato Black", Font.PLAIN, 20));
		labelChooseName.setBounds(1340, 200, 500, 30);
		add(labelChooseName);
		
		JTextArea textAreaGrantShip = new JTextArea();
		textAreaGrantShip.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaGrantShip.setLineWrap(true);
		textAreaGrantShip.setWrapStyleWord(true);
		textAreaGrantShip.setBounds(1340, 430, 500, 50);
		add(textAreaGrantShip);
		textAreaGrantShip.setText("Old Saltbeard will grant you " + Constants.PLAYER_START_GOLD + " " + Constants.NAME_CURRENCY + " and one ship of your choosing:");
		textAreaGrantShip.setOpaque(false);
		
	}
	
	/**
	 * Builds the slider bar and label to allow setting game duration
	 */
	private void constructDurationPromptAndSlider() {
		
		//The prompt to choose a duration
		JLabel labelChooseDuration = new JLabel("I will need 20 days to save the Saltforge.");
		labelChooseDuration.setFont(new Font("Lato Black", Font.PLAIN, 20));
		labelChooseDuration.setBounds(1340, 320, 500, 25);
		add(labelChooseDuration);
		
		sliderGameDuration = new JSlider();
		sliderGameDuration.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				labelChooseDuration.setText("I will need " + sliderGameDuration.getValue() + " days to save the Saltforge.");
			}
		});
		sliderGameDuration.setFont(new Font("Lato Black", Font.PLAIN, 16));
		sliderGameDuration.setSnapToTicks(true);
		sliderGameDuration.setPaintLabels(true);
		sliderGameDuration.setValue(20);
		sliderGameDuration.setMinorTickSpacing(1);
		sliderGameDuration.setMajorTickSpacing(10);
		sliderGameDuration.setMinimum(20);
		sliderGameDuration.setMaximum(50);
		sliderGameDuration.setBounds(1340, 350, 480, 50);
		add(sliderGameDuration);
		sliderGameDuration.setOpaque(false);
		
	}
	
	/**
	 * Builds radio buttons to allow selecting a ship, and also the button to start the game
	 */
	private void constructButtonAndRadioButtons() {
		
		//groups radio buttons together (so you select one at a time)
		ButtonGroup chooseShipButtonGroup = new ButtonGroup();
		
		JRadioButton radioButtonChooseMerchantman = new JRadioButton("");
		radioButtonChooseMerchantman.setVerticalAlignment(SwingConstants.TOP);
		radioButtonChooseMerchantman.setBounds(1335, 480, 505, 35);
		add(radioButtonChooseMerchantman);
		chooseShipButtonGroup.add(radioButtonChooseMerchantman);
		
		//html tag allows text wrapping
		radioButtonChooseMerchantman.setText("<html>" + Constants.MERCHANTMAN_INFO_STRING + "</html>");
		radioButtonChooseMerchantman.setOpaque(false);
		
		JRadioButton radioButtonChooseCutter = new JRadioButton("");
		radioButtonChooseCutter.setVerticalAlignment(SwingConstants.TOP);
		radioButtonChooseCutter.setBounds(1335, 520, 505, 35);
		add(radioButtonChooseCutter);
		chooseShipButtonGroup.add(radioButtonChooseCutter);
		
		radioButtonChooseCutter.setText("<html>" + Constants.CUTTER_INFO_STRING + "</html>");
		radioButtonChooseCutter.setOpaque(false);
		
		JRadioButton radioButtonChooseSloop = new JRadioButton("");
		radioButtonChooseSloop.setVerticalAlignment(SwingConstants.TOP);
		radioButtonChooseSloop.setBounds(1335, 560, 505, 35);
		add(radioButtonChooseSloop);
		chooseShipButtonGroup.add(radioButtonChooseSloop);
		
		radioButtonChooseSloop.setText("<html>" + Constants.SLOOP_INFO_STRING + "</html>");
		radioButtonChooseSloop.setOpaque(false);
		
		JRadioButton radioButtonChooseBarge = new JRadioButton("");
		radioButtonChooseBarge.setVerticalAlignment(SwingConstants.TOP);
		radioButtonChooseBarge.setBounds(1335, 600, 505, 35);
		add(radioButtonChooseBarge);
		chooseShipButtonGroup.add(radioButtonChooseBarge);
		
		radioButtonChooseBarge.setText("<html>" + Constants.BARGE_INFO_STRING + "</html>");
		radioButtonChooseBarge.setOpaque(false);
		
		JButton buttonSetSail = new JButton("SET SAIL");
		buttonSetSail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ShipModel selectedShip = null;
				for (Enumeration<AbstractButton> radioButtons = chooseShipButtonGroup.getElements(); radioButtons.hasMoreElements();) {
					AbstractButton button = radioButtons.nextElement();
					
					if (button.isSelected()) {
						String buttonText = button.getText();
						switch (buttonText) {
							case "<html>" + Constants.MERCHANTMAN_INFO_STRING + "</html>":
								selectedShip = ShipModel.MERCHANTMAN;
								break;
							case "<html>" + Constants.CUTTER_INFO_STRING + "</html>":
								selectedShip = ShipModel.CUTTER;
								break;
							case "<html>" + Constants.SLOOP_INFO_STRING + "</html>":
								selectedShip = ShipModel.SLOOP;
								break;
							case "<html>" + Constants.BARGE_INFO_STRING + "</html>":
								selectedShip = ShipModel.BARGE;
								break;
						}
						break;

					}
					
				}
				validateChoicesAndStartGame(textFieldChooseName.getText(), sliderGameDuration.getValue(), selectedShip);
			}
		});
		
		buttonSetSail.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonSetSail.setOpaque(false);
		buttonSetSail.setForeground(Color.RED);
		buttonSetSail.setFont(new Font("Lato Black", Font.PLAIN, 60));
		buttonSetSail.setFocusPainted(false);
		buttonSetSail.setBackground(new Color(200, 200, 0, 0));
		buttonSetSail.setBounds(1490, 915, 400, 100);
		add(buttonSetSail);
		
	}
	
	/**
	 * Sets an image to be the background of the panel
	 */
	private void setBackgroundImage() {
		
		JLabel labelBackgroundImage = new JLabel("");
		labelBackgroundImage.setIcon(new ImageIcon(CharacterCreatePanel.class.getResource("/ui/images/characterCreate.png")));
		labelBackgroundImage.setBounds(0, 0, 1920, 1080);
		add(labelBackgroundImage);
		
	}
	
	/**
	 * Checks that the selected options are all valid choices before starting the game
	 * @param name the typed character's name
	 * @param gameDuration the selected duration of the game
	 * @param ship the selected ship
	 */
	private void validateChoicesAndStartGame(String name, int gameDuration, ShipModel ship) {
		
		try {
			
			if (GameEnvironment.isValidName(name)) {
				
				Player.setName(name);
				GameEnvironment.setGameDuration(gameDuration);
				Player.setShip(new Ship(ship));
				
				PanelManager.setPanel("IslandPanel");
				
			}
			
		} catch (IllegalArgumentException e) {
			
			labelErrorMessage.setText("<html>" + e.getMessage() + "</html>");
			
		}
		
	}
}
