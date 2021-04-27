package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Ledger;
import main.Player;
import main.Transaction;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import exceptions.InsufficientGoldException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class LedgerStatsUpgradesPanel extends JPanel {
	
	private JTextArea textAreaShipInfo;
	private JTextArea textAreaLedger;
	private JTextArea textAreaUpgrade;
	private JLabel labelAlreadyHaveUpgrade;
	private JButton buttonBuyUpgrade;

	/**
	 * Create the panel.
	 */
	public LedgerStatsUpgradesPanel() {
			
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelAlreadyHaveUpgrade = new JLabel("You already have this upgrade.");
		labelAlreadyHaveUpgrade.setFont(new Font("Lato Black", Font.PLAIN, 16));
		labelAlreadyHaveUpgrade.setBounds(729, 864, 250, 25);
		add(labelAlreadyHaveUpgrade);
		
		buttonBuyUpgrade = new JButton("BUY");
		buttonBuyUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String upgrade = "";
				Island curIsland = GameEnvironment.getCurrentIsland();
				int upgradeCost = Constants.UPGRADE_COST;
				
				switch (curIsland.getIslandName()) {
					case (Constants.ISLAND_SALTFORGE):
						upgrade = Constants.UPGRADE_CANNONS;
						break;
					case (Constants.ISLAND_TUNIA):
						upgrade = Constants.UPGRADE_HULL;
						break;
					case (Constants.ISLAND_SKULLHAVEN):
						upgrade = Constants.UPGRADE_FLAG;
						break;
					case (Constants.ISLAND_SANDYFIELDS):
						upgrade = Constants.UPGRADE_CONTRACT;
						break;
					case (Constants.ISLAND_SEANOMADS):
						upgrade = Constants.UPGRADE_SAILS;
						break;
					default:
						throw new IllegalArgumentException("Can't buy upgrades at this island");
			}
				try {
					GameEnvironment.buyIslandUpgrade(upgrade, upgradeCost);
				} catch (InsufficientGoldException e) {
					//can't afford to buy the upgrade
				}
				
				updateUpgradeInfo();
				PanelManager.refreshFrame();
			}
		});
		buttonBuyUpgrade.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonBuyUpgrade.setOpaque(false);
		buttonBuyUpgrade.setForeground(Color.RED);
		buttonBuyUpgrade.setFont(new Font("Lato Black", Font.PLAIN, 48));
		buttonBuyUpgrade.setFocusPainted(false);
		buttonBuyUpgrade.setBackground(new Color(200, 200, 0, 0));
		buttonBuyUpgrade.setBounds(990, 800, 200, 80);
		add(buttonBuyUpgrade);
		
		//the greeting text on the left
		textAreaShipInfo = new JTextArea("");
		textAreaShipInfo.setWrapStyleWord(true);
		textAreaShipInfo.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaShipInfo.setLineWrap(true);
		textAreaShipInfo.setEditable(false);
		textAreaShipInfo.setBounds(100, 100, 500, 800);
		textAreaShipInfo.setBackground(new Color(255,255,255,175));
		textAreaShipInfo.setMargin(new Insets(15,15,15,15));
		//fixes selected text highlighting bug
		textAreaShipInfo.getCaret().deinstall(textAreaShipInfo);
		add(textAreaShipInfo);
		textAreaShipInfo.setOpaque(true);
		
		textAreaUpgrade = new JTextArea("");
		textAreaUpgrade.setWrapStyleWord(true);
		textAreaUpgrade.setOpaque(true);
		textAreaUpgrade.setMargin(new Insets(15, 15, 15, 15));
		textAreaUpgrade.setLineWrap(true);
		textAreaUpgrade.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaUpgrade.setEditable(false);
		textAreaUpgrade.setBackground(new Color(255, 255, 255, 175));
		textAreaUpgrade.setBounds(710, 100, 500, 800);
		//fixes selected text highlighting bug
		textAreaUpgrade.getCaret().deinstall(textAreaUpgrade);
		add(textAreaUpgrade);
		
		
		textAreaLedger = new JTextArea("");
		textAreaLedger.setWrapStyleWord(true);
		textAreaLedger.setOpaque(true);
		textAreaLedger.setMargin(new Insets(15, 15, 15, 15));
		textAreaLedger.setLineWrap(true);
		textAreaLedger.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaLedger.setEditable(false);
		textAreaLedger.setBackground(new Color(255, 255, 255, 175));
		textAreaLedger.setBounds(1320, 100, 500, 800);
		//fixes selected text highlighting bug
		textAreaLedger.getCaret().deinstall(textAreaLedger);
		
		//add scrollbar to text area
		JScrollPane scrollPaneLedger = new JScrollPane(textAreaLedger);
		scrollPaneLedger.setSize(500, 800);
		scrollPaneLedger.setLocation(1320, 100);

		scrollPaneLedger.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
		        PanelManager.refreshFrame();
		    }
			
		});
		
		//scrollPaneLedger.setBounds(1820, 100, 10, 800);
		scrollPaneLedger.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//textAreaLedger.add(scrollPaneLedger);
		//add(textAreaLedger);
		add(scrollPaneLedger);

		
		JButton buttonBackToMarket = new JButton("BACK TO MARKET");
		buttonBackToMarket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel("IslandPanel");
			}
		});
		buttonBackToMarket.setVerticalAlignment(SwingConstants.BOTTOM);
		buttonBackToMarket.setOpaque(false);
		buttonBackToMarket.setForeground(Color.RED);
		buttonBackToMarket.setFont(new Font("Lato Black", Font.PLAIN, 48));
		buttonBackToMarket.setFocusPainted(false);
		buttonBackToMarket.setBackground(new Color(200, 200, 0, 0));
		buttonBackToMarket.setBounds(1320, 926, 500, 80);
		add(buttonBackToMarket);
		
	}
	
	public void updatePanel() {
		
		updateShipInfo();
		updateLedger();
		updateUpgradeInfo();
		
	}
	
	private void updateShipInfo() {
		textAreaShipInfo.setText(GameEnvironment.getShipDetailsString());
	}
	
	private void updateUpgradeInfo() {
		
		String upgradeString = Constants.CRAGNUS_UPGRADE + "\n\n";
		
		String upgrade = "";
		
		Island curIsland = GameEnvironment.getCurrentIsland();
		int upgradeCost = Constants.UPGRADE_COST;
		
		switch (curIsland.getIslandName()) {
			case (Constants.ISLAND_SALTFORGE):
				upgrade = Constants.UPGRADE_CANNONS;
				upgradeString += "The dwarven smiths offer to upgrade your cannons for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".";
				break;
			case (Constants.ISLAND_TUNIA):
				upgrade = Constants.UPGRADE_HULL;
				upgradeString += "The Tunian shipyard can reinforce your ship's hull for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".";
				break;
			case (Constants.ISLAND_SKULLHAVEN):
				upgrade = Constants.UPGRADE_FLAG;
				upgradeString += "In a dark alley, a shady figure whispers that he can help you to evade attacks from pirates, and offers to sell you a genuine pirate flag for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".";
				break;
			case (Constants.ISLAND_SANDYFIELDS):
				upgrade = Constants.UPGRADE_CONTRACT;
				upgradeString += "The Council of Peasants declares that they will sell all goods to you at a reduced price, if you make an upfront investment for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".";
				break;
			case (Constants.ISLAND_SEANOMADS):
				upgrade = Constants.UPGRADE_SAILS;
				upgradeString += "An old woman with leathery hands offers you a painstakingly crafted main sail for " + upgradeCost + " " + Constants.NAME_CURRENCY + ".";
				break;
			default:
				throw new IllegalArgumentException("Can't buy upgrades at this island");
		}
		
		textAreaUpgrade.setText(upgradeString);
		
		if (Player.getShip().getUpgrades().contains(upgrade)) {
			labelAlreadyHaveUpgrade.setVisible(true);
			buttonBuyUpgrade.setVisible(false);
		}
		else {
			labelAlreadyHaveUpgrade.setVisible(false);
			buttonBuyUpgrade.setVisible(true);
		}
		
	}
	
	private void updateLedger() {
		
		String ledgerString = Constants.OLARD_LEDGER + "\n";
		ledgerString += "\nAll previous transactions:";
		
		ArrayList<Transaction> transactions;
		
		try {
			transactions = Ledger.getTransactions();
			
			for (Transaction transaction : transactions) {
				ledgerString += "\n\n" + transaction.toString();
			}
			
		} catch (NullPointerException e) {
			ledgerString += "\n" + "There are no transactions yet.";
		}
		
		textAreaLedger.setText(ledgerString);
		
	}
}
