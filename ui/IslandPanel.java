package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import exceptions.InsufficientGoldException;
import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Player;
import main.Route;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;

public class IslandPanel extends JPanel {
	
	private JLabel labelBackgroundImage;
	private JLabel labelIslandName;
	private JTextArea textAreaIslandDescription;
	private JButton buttonTravelRouteOne;
	private JButton buttonTravelRouteTwo;
	private JButton buttonTravelRouteThree;
	private JButton buttonTravelRouteFour;
	private JButton buttonTravelRouteFive;
	ArrayList<JButton> buttons;
	HashMap<Integer, Object[]> travelOptions;

	/**
	 * Create the panel.
	 */
	public IslandPanel() {

		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelBackgroundImage = new JLabel("");
		labelBackgroundImage.setBounds(0, 0, 1920, 1080);
		
		labelIslandName = new JLabel("");
		labelIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		labelIslandName.setForeground(Color.RED);
		labelIslandName.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelIslandName.setBounds(670, 40, 580, 50);
		add(labelIslandName);
		
		textAreaIslandDescription = new JTextArea("");
		textAreaIslandDescription.setWrapStyleWord(true);
		textAreaIslandDescription.setOpaque(true);
		textAreaIslandDescription.setLineWrap(true);
		textAreaIslandDescription.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaIslandDescription.setEditable(false);
		textAreaIslandDescription.setBounds(100, 95, 1720, 250);
		textAreaIslandDescription.setBackground(new Color(255,255,255,175));
		textAreaIslandDescription.setMargin(new Insets(15,15,15,15));
		add(textAreaIslandDescription);
		
		buttonTravelRouteOne = new JButton("");
		buttonTravelRouteOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPressedTravel(buttonTravelRouteOne);
			}
		});
		buttonTravelRouteOne.setBounds(1210, 715, 610, 50);
		add(buttonTravelRouteOne);
		
		buttonTravelRouteTwo = new JButton("");
		buttonTravelRouteTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPressedTravel(buttonTravelRouteTwo);
			}
		});
		buttonTravelRouteTwo.setBounds(1210, 775, 610, 50);
		add(buttonTravelRouteTwo);
		
		buttonTravelRouteThree = new JButton("");
		buttonTravelRouteThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPressedTravel(buttonTravelRouteThree);
			}
		});
		buttonTravelRouteThree.setBounds(1210, 835, 610, 50);
		add(buttonTravelRouteThree);
		
		buttonTravelRouteFour = new JButton("");
		buttonTravelRouteFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPressedTravel(buttonTravelRouteFour);
			}
		});
		buttonTravelRouteFour.setBounds(1210, 895, 610, 50);
		add(buttonTravelRouteFour);
		
		buttonTravelRouteFive = new JButton("");
		buttonTravelRouteFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonPressedTravel(buttonTravelRouteFive);
			}
		});
		buttonTravelRouteFive.setBounds(1210, 955, 610, 50);
		add(buttonTravelRouteFive);
		
		buttons = new ArrayList<JButton>();
		buttons.add(buttonTravelRouteOne);
		buttons.add(buttonTravelRouteTwo);
		buttons.add(buttonTravelRouteThree);
		buttons.add(buttonTravelRouteFour);
		buttons.add(buttonTravelRouteFive);
		
		
		add(labelBackgroundImage);
		
	}
	
	private void buttonPressedTravel(JButton button) {
		
		//index of the button in the button array, is the key of the route/island pair in the traveloptions hashmap
		
		Object[] routeIslandPair = travelOptions.get(buttons.indexOf(button));
		
		Route route = (Route) routeIslandPair[0];
		Island destination = (Island) routeIslandPair[1];
		
		int modifiedDuration = GameEnvironment.getModifiedTravelTime(route.getDistance());
		int totalCostToTravel = Player.getShip().totalCostToLeaveIsland(modifiedDuration);
		
		boolean eventOccurs = GameEnvironment.doesEventOccur(route);
		
		try {
			GameEnvironment.initiateTravel(destination, route);
		} catch (InsufficientGoldException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		GameEnvironment.arriveAtIsland(destination);
		PanelManager.setPanel("IslandPanel");
		
	}
	
	public void populateFieldsWithData() {
		
		setBackgroundByCurrentIsland();
		setNameByCurrentIsland();
		setDescriptionByCurrentIsland();
		populateRoutesByCurrentIsland();
	}
	
	private void populateRoutesByCurrentIsland() {
		
		travelOptions = GameEnvironment.getTravelOptions(0, GameEnvironment.getCurrentIsland());
		
		ArrayList<String> travelStrings = new ArrayList<String>();
		
		for (Object[] routeIslandPair : travelOptions.values()) {
			
			Route route = (Route) routeIslandPair[0];
			Island destinationIsland = (Island) routeIslandPair[1];
			
			String routeInfo = route.getRouteInfoString();
			
			String travelString = "<html>Travel to " + destinationIsland.getIslandName() + " via " + route.getName() + " " + routeInfo + "</html>";
			travelStrings.add(travelString);
		}
		
		for (int i = 0; i < travelOptions.size(); i++) {
			buttons.get(i).setText(travelStrings.get(i));
		}
		
		if (travelOptions.size() < buttons.size()) {
			buttonTravelRouteFive.setVisible(false);
		}
		else {
			buttonTravelRouteFive.setVisible(true);
		}
	}
	
	private void setNameByCurrentIsland() {
		
		String curIslandName = GameEnvironment.getCurrentIsland().getIslandName();
		labelIslandName.setText("<html>" + curIslandName + "</html>");
		
	}
	
	private void setDescriptionByCurrentIsland() {
		
		String curIslandDescription = GameEnvironment.getCurrentIsland().getIslandDescription();
		textAreaIslandDescription.setText(curIslandDescription);
	}
	
	private void setBackgroundByCurrentIsland() {
		
		String curIslandName = GameEnvironment.getCurrentIsland().getIslandName();
		String imagePath = "";
		
		switch (curIslandName) {
			case Constants.ISLAND_SALTFORGE:
				imagePath = "/ui/images/dullSaltForge.png";
				break;
			case Constants.ISLAND_SANDYFIELDS:
				imagePath = "/ui/images/dullSandyFields.png";
				break;
			case Constants.ISLAND_TUNIA:
				break;
			case Constants.ISLAND_SKULLHAVEN:
				break;
			case Constants.ISLAND_SEANOMADS:
				break;
			default:
				imagePath = "/ui/images/dullSandyFields.png";
		}
		
		labelBackgroundImage.setIcon(new ImageIcon(IslandPanel.class.getResource(imagePath)));
	}

}
