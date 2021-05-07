package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import main.Constants;

/**
 * creates the JFrame application window, and handles swapping JPanels in and out of the frame
 * @author Ryan Croucher rcr69
 *
 */
public class PanelManager {

	/**
	 * The application window
	 */
	public static JFrame frame;
	
	/**
	 * The initial panel of the game
	 */
	public static TitlePanel titlePanel;
	
	/**
	 * Panel for the player to create their character and game settings
	 */
	public static CharacterCreatePanel characterCreationPanel;
	
	/**
	 * Panel to handle all island activities including trading in the market
	 */
	public static IslandPanel islandPanel;
	
	/**
	 * Panel to handle travel time between islands
	 */
	public static RoutePanel routePanel;
	
	/**
	 * Panel to handle randomly generated events during travel
	 */
	public static EventPanel eventPanel;
	
	/**
	 * Panel to display ship properties, available upgrades and historical transactions
	 */
	public static LedgerStatsUpgradesPanel ledgerStatsUpgradePanel;
	
	/**
	 * Panel to show the end-game statistics
	 */
	public static GameOverPanel gameOverPanel;
	
	/**
	 * If GUIMode is true, this method is called to start the GUI version of the game
	 */
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
		
		frame = new JFrame("Sailors of the Saltforge");
		frame.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	/**
	 * Swaps a panel into the jframe, to make it the current displayed screen 
	 * @param panel the panel to place into the jframe
	 */
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
				routePanel.resetShipXPosition();
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


		refreshFrame();
			
	}
	
	/**
	 * Forces the jframe to refresh, allowing elements to properly update without overlap or glitches
	 */
	public static void refreshFrame() {
		
		frame.invalidate();
		frame.validate();
		frame.repaint();

	}

}
