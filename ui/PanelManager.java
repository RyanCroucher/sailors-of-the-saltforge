package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Constants;
import main.GameEnvironment;

public class PanelManager {

	private static JFrame frame;
	
	public static TitlePanel titlePanel;
	public static CharacterCreatePanel characterCreationPanel;
	public static IslandPanel islandPanel;
	public static RoutePanel routePanel;
	public static LedgerStatsUpgradesPanel ledgerStatsUpgradePanel;
	
	public static void startGUIGame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		
					initialize();
					
					//arrive at the saltforge
					//GameEnvironment.arriveAtIsland(GameEnvironment.getIslands()[0]);
			
					titlePanel = new TitlePanel();
					characterCreationPanel = new CharacterCreatePanel();
					islandPanel = new IslandPanel();
					routePanel = new RoutePanel();
					ledgerStatsUpgradePanel = new LedgerStatsUpgradesPanel();
					
					setPanel("TitlePanel");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		
		frame = new JFrame();
		frame.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.getContentPane().setLayout(null);
		
	}
	
	public static void setPanel(String panel) {
		
		switch(panel) {
			case "IslandPanel":
				islandPanel.populateFieldsWithData();
				frame.setContentPane(islandPanel);
				break;
			case "TitlePanel":
				frame.setContentPane(titlePanel);
				break;
			case "CharacterCreatePanel":
				frame.setContentPane(characterCreationPanel);
				break;
			case "RoutePanel":
				frame.setContentPane(routePanel);
				break;
			case "LedgerStatsUpgradesPanel":
				ledgerStatsUpgradePanel.updatePanel();
				frame.setContentPane(ledgerStatsUpgradePanel);
				break;
			default:
				System.err.println("No such panel");
				break;
		}


		frame.revalidate();
			
	}
	
	public static void refreshFrame() {
		
		frame.invalidate();
		frame.validate();
		frame.repaint();

	}

}
