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
import main.Island;
import main.Player;
import main.Route;
import java.awt.Font;

import javax.swing.JTextArea;
import java.awt.Insets;

public class RoutePanel extends JPanel {
	
	private static JLabel labelRouteName;
	private static JTextArea textAreaRouteDescription;
	private static JButton buttonNext;
	
	private static JLabel labelSomethingHappens;
	
	private static Island destination;
	private static boolean eventOccurs;
	private JLabel labelShipImage;
	
	public int shipXPosition = -150;

	/**
	 * Create the panel.
	 */
	public RoutePanel() {
		
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelRouteName = new JLabel("");
		labelRouteName.setForeground(Color.RED);
		labelRouteName.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelRouteName.setHorizontalAlignment(SwingConstants.CENTER);
		labelRouteName.setBounds(660, 30, 600, 50);
		add(labelRouteName);
		
		labelSomethingHappens = new JLabel("");
		labelSomethingHappens.setFont(new Font("Lato Black", Font.PLAIN, 20));
		labelSomethingHappens.setBounds(570, 662, 400, 25);
		add(labelSomethingHappens);
		
		textAreaRouteDescription = new JTextArea("");
		textAreaRouteDescription.setWrapStyleWord(true);
		textAreaRouteDescription.setOpaque(true);
		textAreaRouteDescription.setMargin(new Insets(15, 15, 15, 15));
		textAreaRouteDescription.setLineWrap(true);
		textAreaRouteDescription.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaRouteDescription.setEditable(false);
		textAreaRouteDescription.setBackground(new Color(255, 255, 255, 175));
		textAreaRouteDescription.setBounds(560, 95, 800, 600);
		//fixes selected text highlighting bug
		textAreaRouteDescription.getCaret().deinstall(textAreaRouteDescription);
		add(textAreaRouteDescription);
		
		buttonNext = new JButton("ARRIVE");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				GameEnvironment.arriveAtIsland(destination);
				
				if (eventOccurs) {
					PanelManager.setPanel("EventPanel");
				} else {
					PanelManager.setPanel("IslandPanel");
				}
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
		
		JLabel labelBackgroundFore = new JLabel("");
		labelBackgroundFore.setIcon(new ImageIcon(RoutePanel.class.getResource("/ui/images/basaltSpiresForeground.png")));
		labelBackgroundFore.setBounds(0, 0, 1920, 1080);
		add(labelBackgroundFore);
		
		labelShipImage = new JLabel("");
		labelShipImage.setIcon(new ImageIcon(RoutePanel.class.getResource("/ui/images/basaltSpiresShip.png")));
		labelShipImage.setBounds(-150, 305, 400, 300);
		add(labelShipImage);
		
		JLabel labelBackgroundRear = new JLabel("");
		labelBackgroundRear.setIcon(new ImageIcon(RoutePanel.class.getResource("/ui/images/basaltSpiresBackground.png")));
		labelBackgroundRear.setBounds(0, 0, 1920, 1080);
		add(labelBackgroundRear);

		moveShip();
	}
	
	
	public static void updateDetails(Route route, Island destinationIsland, boolean event) {
		
		int modifiedDuration = GameEnvironment.getModifiedTravelTime(route.getDistance());
		int totalCostToTravel = Player.getShip().totalCostToLeaveIsland(modifiedDuration);
		
		labelRouteName.setText(route.getName());
		textAreaRouteDescription.setText(route.getDescription() + "\n\n\n\n\n" + "You spend " + totalCostToTravel + " " + Constants.NAME_CURRENCY + " on crew hire, repairs and wages.");
		
		eventOccurs = event;
		destination = destinationIsland;
		
		if (eventOccurs) {
			labelSomethingHappens.setForeground(Color.RED);
			labelSomethingHappens.setText("Something happens on your journey.");
			
			buttonNext.setText("Next");
			
			EventPanel.updateDetails();
			
		} else {
			labelSomethingHappens.setForeground(Color.BLACK);
			labelSomethingHappens.setText("Your journey was uneventful.");
			
			buttonNext.setText("Arrive");
		}
		
	}
	
	private void moveShip() {
		
		javax.swing.Timer timer = new javax.swing.Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shipXPosition++;
				labelShipImage.setBounds(shipXPosition, labelShipImage.getBounds().y, labelShipImage.getBounds().width, labelShipImage.getBounds().height);
				repaint();
			}
		});
		
		timer.start();
		
	}
	
}
