package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import main.Constants;

public class PanelManager {

	private static JFrame frame;
	
	public static TitlePanel titlePanel;
	public static CharacterCreatePanel characterCreationPanel;
	public static IslandPanel islandPanel;
	public static RoutePanel routePanel;
	public static EventPanel eventPanel;
	public static LedgerStatsUpgradesPanel ledgerStatsUpgradePanel;
	public static GameOverPanel gameOverPanel;
	
	public static void startGUIGame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		
					initialize();
			
					titlePanel = new TitlePanel();
					characterCreationPanel = new CharacterCreatePanel();
					islandPanel = new IslandPanel();
					routePanel = new RoutePanel();
					eventPanel = new EventPanel();
					ledgerStatsUpgradePanel = new LedgerStatsUpgradesPanel();
					gameOverPanel = new GameOverPanel();
					
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
				routePanel.shipXPosition = -150;
				break;
			case "LedgerStatsUpgradesPanel":
				ledgerStatsUpgradePanel.updatePanel();
				frame.setContentPane(ledgerStatsUpgradePanel);
				break;
			case "EventPanel":
				frame.setContentPane(eventPanel);
				break;
			case "GameOverPanel":
				frame.setContentPane(gameOverPanel);
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
