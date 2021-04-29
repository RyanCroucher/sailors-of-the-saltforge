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

public class EventPanel extends JPanel {
	
	private static JLabel labelEventName;
	private static JTextArea textAreaEventDescription;
	private static JButton buttonArrive;

	private static JLabel labelResultEffect;
	
	private static JButton buttonOptionOne, buttonOptionTwo, buttonOptionThree;
	
	private static RandomEvent event;

	/**
	 * Create the panel.
	 */
	public EventPanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelEventName = new JLabel("");
		labelEventName.setForeground(Color.RED);
		labelEventName.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelEventName.setHorizontalAlignment(SwingConstants.CENTER);
		labelEventName.setBounds(660, 30, 600, 50);
		add(labelEventName);
		
		labelResultEffect = new JLabel("");
		labelResultEffect.setForeground(Color.RED);
		labelResultEffect.setFont(new Font("Lato Black", Font.PLAIN, 20));
		labelResultEffect.setBounds(570, 760, 700, 25);
		add(labelResultEffect);
		
		textAreaEventDescription = new JTextArea("");
		textAreaEventDescription.setWrapStyleWord(true);
		textAreaEventDescription.setOpaque(true);
		textAreaEventDescription.setMargin(new Insets(15, 15, 15, 15));
		textAreaEventDescription.setLineWrap(true);
		textAreaEventDescription.setFont(new Font("Lato Black", Font.PLAIN, 18));
		textAreaEventDescription.setEditable(false);
		textAreaEventDescription.setBackground(new Color(255, 255, 255, 175));
		textAreaEventDescription.setBounds(560, 95, 800, 700);
		//fixes selected text highlighting bug
		textAreaEventDescription.getCaret().deinstall(textAreaEventDescription);
		add(textAreaEventDescription);
		
		buttonArrive = new JButton("ARRIVE");
		buttonArrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel("IslandPanel");
			}
		});
		buttonArrive.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonArrive.setOpaque(false);
		buttonArrive.setForeground(Color.RED);
		buttonArrive.setFont(new Font("Lato Black", Font.PLAIN, 60));
		buttonArrive.setFocusPainted(false);
		buttonArrive.setBackground(new Color(200, 200, 0, 0));
		buttonArrive.setBounds(1490, 915, 400, 100);
		add(buttonArrive);
		
		buttonOptionOne = new JButton("");
		buttonOptionOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickedOption(buttonOptionOne);
			}
		});
		buttonOptionOne.setBounds(560, 825, 800, 50);
		add(buttonOptionOne);
		
		buttonOptionTwo = new JButton("");
		buttonOptionTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickedOption(buttonOptionTwo);
			}
		});
		buttonOptionTwo.setBounds(560, 905, 800, 50);
		add(buttonOptionTwo);
		
		buttonOptionThree = new JButton("");
		buttonOptionThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickedOption(buttonOptionThree);
			}
		});
		buttonOptionThree.setBounds(560, 985, 800, 50);
		add(buttonOptionThree);
		
		JLabel labelBackground = new JLabel("");
		labelBackground.setIcon(null);
		labelBackground.setBounds(0, 0, 1920, 1080);
		add(labelBackground);

	}
	
	private static void clickedOption(JButton button) {
		
		hideOptionButtons();
		
		if (button == buttonOptionOne)
			event.chooseOption(1);
		else if (button == buttonOptionTwo)
			event.chooseOption(2);
		else if (button == buttonOptionThree)
			event.chooseOption(3);
		
		
		textAreaEventDescription.setText(event.getEffect());
		event.doEffect();
		
		buttonArrive.setVisible(true);
	}
	
	private static void hideButtonsAndEffectLabel() {
		
		buttonArrive.setVisible(false);
		
		hideOptionButtons();
		
		labelResultEffect.setText("");
	}
	
	private static void hideOptionButtons() {
		buttonOptionOne.setVisible(false);
		buttonOptionTwo.setVisible(false);
		buttonOptionThree.setVisible(false);
	}
	
	public static void updateDetails() {
		
		//generate a random event
		event = GameEnvironment.rollRandomEvent();
		
		labelEventName.setText(event.getName());
		textAreaEventDescription.setText(event.getDescription());
		
		ArrayList<String> options = event.getOptions();
		JButton[] buttons = new JButton[] {buttonOptionOne, buttonOptionTwo, buttonOptionThree};
		hideButtonsAndEffectLabel();
		
		if (options.size() == 0) {
			labelResultEffect.setText(event.getEffect());
			event.doEffect();
			buttonArrive.setVisible(true);
		}
		
		//we need to pick an option
		else {
			
			for(int i = 0; i < options.size(); i++) {
				buttons[i].setVisible(true);
				buttons[i].setText(options.get(i));
			}
			
		}
	}
}
