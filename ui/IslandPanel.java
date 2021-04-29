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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exceptions.InsufficientCargoCapacityException;
import exceptions.InsufficientGoldException;
import exceptions.InsufficientItemQuantityException;
import main.Constants;
import main.GameEnvironment;
import main.Island;
import main.Item;
import main.Player;
import main.Route;
import main.Store;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

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
	
	
	private JLabel labelWealth;
	private JLabel labelDay;
	
	private JLabel labelImports;
	private JLabel labelExports;
	private JLabel labelCargo;
	
	private JLabel labelItemStockQuantityAlcohol;
	private JTextArea textAreaItemNameAlcohol;
	private JTextField textFieldAlcoholQuantity;
	private JButton buttonBuyAlcohol;
	private JButton buttonSellAlcohol;
	private JLabel labelAlcoholPrice;
	
	private JLabel labelItemStockQuantityRawMaterials;
	private JTextArea textAreaItemNameRawMaterials;
	private JLabel labelRawMaterialsPrice;
	private JButton buttonRawMaterialsIncreaseQuantity;
	private JButton buttonRawMaterialsDecreaseQuantity;
	private JTextField textFieldRawMaterialsQuantity;
	private JTextArea textAreaItemDescriptionRawMaterials;
	private JButton buttonBuyRawMaterials;
	private JButton buttonSellRawMaterials;
	
	
	private JTextArea textAreaItemNameFood;
	private JLabel labelItemImageFood;
	private JLabel labelFoodPrice;
	private JLabel labelItemStockQuantityFood;
	private JButton buttonFoodIncreaseQuantity;
	private JTextField textFieldFoodQuantity;
	private JButton buttonFoodDecreaseQuantity;
	private JButton buttonBuyFood;
	private JButton buttonSellFood;
	private JTextArea textAreaItemDescriptionFood;
	
	
	private JTextArea textAreaItemNameLuxuryGoods;
	private JLabel labelLuxuryGoodsPrice;
	private JLabel labelItemStockQuantityLuxuryGoods;
	private JLabel labelItemImageLuxuryGoods;
	private JButton buttonLuxuryGoodsIncreaseQuantity;
	private JTextField textFieldLuxuryGoodsQuantity;
	private JButton buttonBuyLuxuryGoods;
	private JButton buttonSellLuxuryGoods;
	private JButton buttonLuxuryGoodsDecreaseQuantity;
	private JTextArea textAreaItemDescriptionLuxuryGoods;
	
	
	private JTextArea textAreaTransactionResult;
	private JButton buttonLedgerAndInfo;

	/**
	 * Create the panel.
	 */
	public IslandPanel() {

		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setLayout(null);
		
		labelIslandName = new JLabel("");
		labelIslandName.setHorizontalAlignment(SwingConstants.CENTER);
		labelIslandName.setForeground(Color.RED);
		labelIslandName.setFont(new Font("Lato Black", Font.PLAIN, 48));
		labelIslandName.setBounds(670, 30, 580, 50);
		add(labelIslandName);
		
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
		
		labelDay = new JLabel("It is day 29 of 50.");
		labelDay.setForeground(Color.RED);
		labelDay.setFont(new Font("Lato Black", Font.PLAIN, 18));
		labelDay.setBounds(370, 356, 150, 25);
		add(labelDay);
		
		labelWealth = new JLabel("You have 1000 Gold Crowns.");
		labelWealth.setForeground(new Color(0, 0, 0));
		labelWealth.setFont(new Font("Lato Black", Font.PLAIN, 18));
		labelWealth.setBounds(110, 356, 250, 25);
		add(labelWealth);
		
		textAreaIslandDescription = new JTextArea("");
		textAreaIslandDescription.setWrapStyleWord(true);
		textAreaIslandDescription.setOpaque(true);
		textAreaIslandDescription.setLineWrap(true);
		textAreaIslandDescription.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaIslandDescription.setEditable(false);
		textAreaIslandDescription.setBounds(100, 95, 1720, 290);
		textAreaIslandDescription.setBackground(new Color(255,255,255,175));
		textAreaIslandDescription.setMargin(new Insets(15,15,15,15));
		//fixes selected text highlighting bug
		textAreaIslandDescription.getCaret().deinstall(textAreaIslandDescription);
		add(textAreaIslandDescription);
		
		labelImports = new JLabel("");
		labelImports.setForeground(Color.RED);
		labelImports.setFont(new Font("Lato Black", Font.PLAIN, 18));
		labelImports.setBounds(532, 466, 300, 25);
		add(labelImports);
		
		labelExports = new JLabel("");
		labelExports.setForeground(Color.RED);
		labelExports.setFont(new Font("Lato Black", Font.PLAIN, 18));
		labelExports.setBounds(525, 490, 360, 25);
		add(labelExports);
		
		labelCargo = new JLabel("");
		labelCargo.setFont(new Font("Lato Black", Font.PLAIN, 18));
		labelCargo.setBounds(245, 516, 650, 25);
		add(labelCargo);
		
		JTextArea textAreaImportsExports = new JTextArea("You quickly discover the main imports and exports of this market.\nMain Imports (will buy/sell at a higher price):\nMain Exports (will buy/sell at a lower  price):\nYour cargo is:");
		textAreaImportsExports.setWrapStyleWord(true);
		textAreaImportsExports.setOpaque(true);
		textAreaImportsExports.setMargin(new Insets(15, 15, 15, 15));
		textAreaImportsExports.setLineWrap(true);
		textAreaImportsExports.setFont(new Font("Lato Black", Font.PLAIN, 20));
		textAreaImportsExports.setEditable(false);
		textAreaImportsExports.setBackground(new Color(255, 255, 255, 175));
		textAreaImportsExports.setBounds(100, 425, 800, 125);
		//fixes selected text highlighting bug
		textAreaImportsExports.getCaret().deinstall(textAreaImportsExports);
		add(textAreaImportsExports);
		
		JButton buttonAlcoholIncreaseQuantity = new JButton("+");
		buttonAlcoholIncreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldAlcoholQuantity.getText();
				String newText;
				
				try {
					newText = (Math.min(100, Integer.parseInt(curText) + 1)) + "";
					
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldAlcoholQuantity.setText(newText);
				
			}
		});
		
		labelAlcoholPrice = new JLabel("0");
		labelAlcoholPrice.setHorizontalAlignment(SwingConstants.LEFT);
		labelAlcoholPrice.setBackground(new Color(255, 255, 255));
		labelAlcoholPrice.setForeground(new Color(0, 0, 0));
		labelAlcoholPrice.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelAlcoholPrice.setBounds(103, 605, 80, 30);
		add(labelAlcoholPrice);
		buttonAlcoholIncreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonAlcoholIncreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonAlcoholIncreaseQuantity.setBounds(230, 600, 45, 25);
		add(buttonAlcoholIncreaseQuantity);
		
		textFieldAlcoholQuantity = new JTextField();
		textFieldAlcoholQuantity.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void removeUpdate(DocumentEvent e) {
				if (!textFieldAlcoholQuantity.getText().isEmpty())
					updatePrice();
			}
			
			public void insertUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void updatePrice() {
				try {
					int quantity = Integer.parseInt(textFieldAlcoholQuantity.getText());
					Item alcohol = GameEnvironment.getItems().get(1);
					Store store = GameEnvironment.getCurrentIsland().getIslandStore();
					int updatedPrice = Math.max(1, quantity) * store.getItemPrice(alcohol);
					labelAlcoholPrice.setText(updatedPrice + "¢");
				} catch (NumberFormatException | NullPointerException exception) {
					System.err.println(exception.getMessage());
				}
			}
			
		});
		textFieldAlcoholQuantity.setText("0");
		textFieldAlcoholQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAlcoholQuantity.setFont(new Font("Lato Black", Font.PLAIN, 18));
		textFieldAlcoholQuantity.setBounds(230, 638, 45, 45);
		add(textFieldAlcoholQuantity);
		textFieldAlcoholQuantity.setColumns(10);
		
		JButton buttonAlcoholDecreaseQuantity = new JButton("-");
		buttonAlcoholDecreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldAlcoholQuantity.getText();
				String newText;
				
				try {
					newText = (Math.max(0, Integer.parseInt(curText) - 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldAlcoholQuantity.setText(newText);
				
			}
		});
		buttonAlcoholDecreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonAlcoholDecreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonAlcoholDecreaseQuantity.setBounds(230, 695, 45, 25);
		add(buttonAlcoholDecreaseQuantity);
		
		labelItemStockQuantityAlcohol = new JLabel("");
		labelItemStockQuantityAlcohol.setHorizontalAlignment(SwingConstants.RIGHT);
		labelItemStockQuantityAlcohol.setForeground(Color.RED);
		labelItemStockQuantityAlcohol.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelItemStockQuantityAlcohol.setBounds(165, 605, 50, 30);
		add(labelItemStockQuantityAlcohol);
		
		JLabel labelItemImageAlcohol = new JLabel("");
		labelItemImageAlcohol.setHorizontalAlignment(SwingConstants.TRAILING);
		labelItemImageAlcohol.setIcon(new ImageIcon(IslandPanel.class.getResource("/ui/images/alcoholIcon.png")));
		labelItemImageAlcohol.setBackground(Color.BLACK);
		labelItemImageAlcohol.setBounds(100, 600, 120, 120);
		add(labelItemImageAlcohol);
		
		buttonBuyAlcohol = new JButton("Buy");
		buttonBuyAlcohol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyItem(GameEnvironment.getItems().get(1));
			}
		});
		buttonBuyAlcohol.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonBuyAlcohol.setBounds(290, 600, 70, 50);
		add(buttonBuyAlcohol);
		
		buttonSellAlcohol = new JButton("Sell");
		buttonSellAlcohol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sellItem(GameEnvironment.getItems().get(1));
			}
		});
		buttonSellAlcohol.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonSellAlcohol.setBounds(290, 670, 70, 50);
		add(buttonSellAlcohol);
		
		textAreaItemNameAlcohol = new JTextArea("        Alcohol");
		textAreaItemNameAlcohol.setWrapStyleWord(true);
		textAreaItemNameAlcohol.setLineWrap(true);
		textAreaItemNameAlcohol.setOpaque(true);
		textAreaItemNameAlcohol.setMargin(new Insets(5, 0, 0, 0));
		textAreaItemNameAlcohol.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaItemNameAlcohol.setEditable(false);
		textAreaItemNameAlcohol.setBackground(new Color(255, 255, 255, 175));
		textAreaItemNameAlcohol.setBounds(100, 570, 120, 25);
		//fixes selected text highlighting bug
		textAreaItemNameAlcohol.getCaret().deinstall(textAreaItemNameAlcohol);
		add(textAreaItemNameAlcohol);
		
		JTextArea textAreaItemDescriptionAlcohol = new JTextArea(GameEnvironment.getItems().get(1).getDescription());
		textAreaItemDescriptionAlcohol.setWrapStyleWord(true);
		textAreaItemDescriptionAlcohol.setOpaque(true);
		textAreaItemDescriptionAlcohol.setMargin(new Insets(7, 5, 0, 0));
		textAreaItemDescriptionAlcohol.setLineWrap(true);
		textAreaItemDescriptionAlcohol.setFont(new Font("Lato Black", Font.PLAIN, 13));
		textAreaItemDescriptionAlcohol.setEditable(false);
		textAreaItemDescriptionAlcohol.setBackground(new Color(255, 255, 255, 175));
		textAreaItemDescriptionAlcohol.setBounds(100, 725, 260, 25);
		//fixes selected text highlighting bug
		textAreaItemDescriptionAlcohol.getCaret().deinstall(textAreaItemDescriptionAlcohol);
		add(textAreaItemDescriptionAlcohol);
		
		labelItemStockQuantityRawMaterials = new JLabel("");
		labelItemStockQuantityRawMaterials.setHorizontalAlignment(SwingConstants.RIGHT);
		labelItemStockQuantityRawMaterials.setForeground(Color.RED);
		labelItemStockQuantityRawMaterials.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelItemStockQuantityRawMaterials.setBounds(165, 805, 50, 30);
		add(labelItemStockQuantityRawMaterials);
		
		labelRawMaterialsPrice = new JLabel("0");
		labelRawMaterialsPrice.setHorizontalAlignment(SwingConstants.LEFT);
		labelRawMaterialsPrice.setForeground(new Color(0, 0, 0));
		labelRawMaterialsPrice.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelRawMaterialsPrice.setBounds(103, 805, 80, 30);
		add(labelRawMaterialsPrice);
		
		JLabel labelItemImageRawMaterials = new JLabel("");
		labelItemImageRawMaterials.setHorizontalAlignment(SwingConstants.TRAILING);
		labelItemImageRawMaterials.setIcon(new ImageIcon(IslandPanel.class.getResource("/ui/images/rawMaterialIcon.png")));
		labelItemImageRawMaterials.setBackground(Color.BLACK);
		labelItemImageRawMaterials.setBounds(100, 800, 120, 120);
		add(labelItemImageRawMaterials);
		
		textAreaItemNameRawMaterials = new JTextArea("  Raw Materials");
		textAreaItemNameRawMaterials.setWrapStyleWord(true);
		textAreaItemNameRawMaterials.setOpaque(true);
		textAreaItemNameRawMaterials.setMargin(new Insets(5, 0, 0, 0));
		textAreaItemNameRawMaterials.setLineWrap(true);
		textAreaItemNameRawMaterials.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaItemNameRawMaterials.setEditable(false);
		textAreaItemNameRawMaterials.setBackground(new Color(255, 255, 255, 175));
		textAreaItemNameRawMaterials.setBounds(100, 770, 120, 25);
		//fixes selected text highlighting bug
		textAreaItemNameRawMaterials.getCaret().deinstall(textAreaItemNameRawMaterials);
		add(textAreaItemNameRawMaterials);
		
		buttonRawMaterialsIncreaseQuantity = new JButton("+");
		buttonRawMaterialsIncreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldRawMaterialsQuantity.getText();
				String newText;
				
				try {
					newText = (Math.min(100, Integer.parseInt(curText) + 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldRawMaterialsQuantity.setText(newText);
				
			}
		});
		buttonRawMaterialsIncreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonRawMaterialsIncreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonRawMaterialsIncreaseQuantity.setBounds(230, 800, 45, 25);
		add(buttonRawMaterialsIncreaseQuantity);
		
		buttonRawMaterialsDecreaseQuantity = new JButton("-");
		buttonRawMaterialsDecreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldRawMaterialsQuantity.getText();
				String newText;
				
				try {
					newText = (Math.max(0, Integer.parseInt(curText) - 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldRawMaterialsQuantity.setText(newText);
				
			}
		});
		buttonRawMaterialsDecreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonRawMaterialsDecreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonRawMaterialsDecreaseQuantity.setBounds(230, 895, 45, 25);
		add(buttonRawMaterialsDecreaseQuantity);
		
		textFieldRawMaterialsQuantity = new JTextField();
		textFieldRawMaterialsQuantity.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void removeUpdate(DocumentEvent e) {
				if (!textFieldRawMaterialsQuantity.getText().isEmpty())
					updatePrice();
			}
			
			public void insertUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void updatePrice() {
				try {
					int quantity = Integer.parseInt(textFieldRawMaterialsQuantity.getText());
					Item rawMaterials = GameEnvironment.getItems().get(2);
					Store store = GameEnvironment.getCurrentIsland().getIslandStore();
					int updatedPrice = Math.max(1, quantity) * store.getItemPrice(rawMaterials);
					labelRawMaterialsPrice.setText(updatedPrice + "¢");
				} catch (NumberFormatException | NullPointerException exception) {
					System.err.println(exception.getMessage());
				}
			}
			
		});
		textFieldRawMaterialsQuantity.setText("0");
		textFieldRawMaterialsQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRawMaterialsQuantity.setFont(new Font("Lato Black", Font.PLAIN, 18));
		textFieldRawMaterialsQuantity.setColumns(10);
		textFieldRawMaterialsQuantity.setBounds(230, 838, 45, 45);
		add(textFieldRawMaterialsQuantity);
		
		textAreaItemDescriptionRawMaterials = new JTextArea(GameEnvironment.getItems().get(2).getDescription());
		textAreaItemDescriptionRawMaterials.setWrapStyleWord(true);
		textAreaItemDescriptionRawMaterials.setOpaque(true);
		textAreaItemDescriptionRawMaterials.setMargin(new Insets(7, 5, 0, 0));
		textAreaItemDescriptionRawMaterials.setLineWrap(true);
		textAreaItemDescriptionRawMaterials.setFont(new Font("Lato Black", Font.PLAIN, 13));
		textAreaItemDescriptionRawMaterials.setEditable(false);
		textAreaItemDescriptionRawMaterials.setBackground(new Color(255, 255, 255, 175));
		textAreaItemDescriptionRawMaterials.setBounds(100, 925, 260, 25);
		//fixes selected text highlighting bug
		textAreaItemDescriptionRawMaterials.getCaret().deinstall(textAreaItemDescriptionRawMaterials);
		add(textAreaItemDescriptionRawMaterials);
		
		buttonBuyRawMaterials = new JButton("Buy");
		buttonBuyRawMaterials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyItem(GameEnvironment.getItems().get(2));
			}
		});
		buttonBuyRawMaterials.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonBuyRawMaterials.setBounds(290, 800, 70, 50);
		add(buttonBuyRawMaterials);
		
		buttonSellRawMaterials = new JButton("Sell");
		buttonSellRawMaterials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sellItem(GameEnvironment.getItems().get(2));
			}
		});
		buttonSellRawMaterials.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonSellRawMaterials.setBounds(290, 870, 70, 50);
		add(buttonSellRawMaterials);
		
		textAreaItemNameFood = new JTextArea("          Food");
		textAreaItemNameFood.setWrapStyleWord(true);
		textAreaItemNameFood.setOpaque(true);
		textAreaItemNameFood.setMargin(new Insets(5, 0, 0, 0));
		textAreaItemNameFood.setLineWrap(true);
		textAreaItemNameFood.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaItemNameFood.setEditable(false);
		textAreaItemNameFood.setBackground(new Color(255, 255, 255, 175));
		textAreaItemNameFood.setBounds(395, 570, 120, 25);
		//fixes selected text highlighting bug
		textAreaItemNameFood.getCaret().deinstall(textAreaItemNameFood);
		add(textAreaItemNameFood);
		
		labelFoodPrice = new JLabel("0");
		labelFoodPrice.setHorizontalAlignment(SwingConstants.LEFT);
		labelFoodPrice.setForeground(new Color(0, 0, 0));
		labelFoodPrice.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelFoodPrice.setBounds(398, 605, 80, 30);
		add(labelFoodPrice);
		
		labelItemStockQuantityFood = new JLabel("");
		labelItemStockQuantityFood.setHorizontalAlignment(SwingConstants.RIGHT);
		labelItemStockQuantityFood.setForeground(Color.RED);
		labelItemStockQuantityFood.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelItemStockQuantityFood.setBounds(460, 605, 50, 30);
		add(labelItemStockQuantityFood);
		
		buttonFoodIncreaseQuantity = new JButton("+");
		buttonFoodIncreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldFoodQuantity.getText();
				String newText;
				
				try {
					newText = (Math.min(100, Integer.parseInt(curText) + 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldFoodQuantity.setText(newText);
				
			}
		});
		
		labelItemImageFood = new JLabel("");
		labelItemImageFood.setIcon(new ImageIcon(IslandPanel.class.getResource("/ui/images/foodIcon.png")));
		labelItemImageFood.setHorizontalAlignment(SwingConstants.TRAILING);
		labelItemImageFood.setBackground(Color.BLACK);
		labelItemImageFood.setBounds(395, 600, 120, 120);
		add(labelItemImageFood);
		buttonFoodIncreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonFoodIncreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonFoodIncreaseQuantity.setBounds(525, 600, 45, 25);
		add(buttonFoodIncreaseQuantity);
		
		textFieldFoodQuantity = new JTextField();
		textFieldFoodQuantity.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void removeUpdate(DocumentEvent e) {
				if (!textFieldFoodQuantity.getText().isEmpty())
					updatePrice();
			}
			
			public void insertUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void updatePrice() {
				try {
					int quantity = Integer.parseInt(textFieldFoodQuantity.getText());
					Item food = GameEnvironment.getItems().get(3);
					Store store = GameEnvironment.getCurrentIsland().getIslandStore();
					int updatedPrice = Math.max(1, quantity) * store.getItemPrice(food);
					labelFoodPrice.setText(updatedPrice + "¢");
				} catch (NumberFormatException | NullPointerException exception) {
					System.err.println(exception.getMessage());
				}
			}
			
		});
		textFieldFoodQuantity.setText("0");
		textFieldFoodQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFoodQuantity.setFont(new Font("Lato Black", Font.PLAIN, 18));
		textFieldFoodQuantity.setColumns(10);
		textFieldFoodQuantity.setBounds(525, 638, 45, 45);
		add(textFieldFoodQuantity);
		
		buttonFoodDecreaseQuantity = new JButton("-");
		buttonFoodDecreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldFoodQuantity.getText();
				String newText;
				
				try {
					newText = (Math.max(0, Integer.parseInt(curText) - 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldFoodQuantity.setText(newText);
				
			}
		});
		buttonFoodDecreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonFoodDecreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonFoodDecreaseQuantity.setBounds(525, 695, 45, 25);
		add(buttonFoodDecreaseQuantity);
		
		buttonBuyFood = new JButton("Buy");
		buttonBuyFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyItem(GameEnvironment.getItems().get(3));
			}
		});
		buttonBuyFood.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonBuyFood.setBounds(585, 600, 70, 50);
		add(buttonBuyFood);
		
		buttonSellFood = new JButton("Sell");
		buttonSellFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sellItem(GameEnvironment.getItems().get(3));
			}
		});
		buttonSellFood.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonSellFood.setBounds(585, 670, 70, 50);
		add(buttonSellFood);
		
		textAreaItemDescriptionFood = new JTextArea(GameEnvironment.getItems().get(3).getDescription());
		textAreaItemDescriptionFood.setWrapStyleWord(true);
		textAreaItemDescriptionFood.setOpaque(true);
		textAreaItemDescriptionFood.setMargin(new Insets(7, 5, 0, 0));
		textAreaItemDescriptionFood.setLineWrap(true);
		textAreaItemDescriptionFood.setFont(new Font("Lato Black", Font.PLAIN, 13));
		textAreaItemDescriptionFood.setEditable(false);
		textAreaItemDescriptionFood.setBackground(new Color(255, 255, 255, 175));
		textAreaItemDescriptionFood.setBounds(395, 725, 260, 25);
		//fixes selected text highlighting bug
		textAreaItemDescriptionFood.getCaret().deinstall(textAreaItemDescriptionFood);
		add(textAreaItemDescriptionFood);
		
		textAreaItemNameLuxuryGoods = new JTextArea("  Luxury Goods");
		textAreaItemNameLuxuryGoods.setWrapStyleWord(true);
		textAreaItemNameLuxuryGoods.setOpaque(true);
		textAreaItemNameLuxuryGoods.setMargin(new Insets(5, 0, 0, 0));
		textAreaItemNameLuxuryGoods.setLineWrap(true);
		textAreaItemNameLuxuryGoods.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaItemNameLuxuryGoods.setEditable(false);
		textAreaItemNameLuxuryGoods.setBackground(new Color(255, 255, 255, 175));
		textAreaItemNameLuxuryGoods.setBounds(395, 770, 120, 25);
		//fixes selected text highlighting bug
		textAreaItemNameLuxuryGoods.getCaret().deinstall(textAreaItemNameLuxuryGoods);
		add(textAreaItemNameLuxuryGoods);
		
		labelLuxuryGoodsPrice = new JLabel("0");
		labelLuxuryGoodsPrice.setHorizontalAlignment(SwingConstants.LEFT);
		labelLuxuryGoodsPrice.setForeground(new Color(0, 0, 0));
		labelLuxuryGoodsPrice.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelLuxuryGoodsPrice.setBounds(398, 805, 80, 30);
		add(labelLuxuryGoodsPrice);
		
		labelItemStockQuantityLuxuryGoods = new JLabel("");
		labelItemStockQuantityLuxuryGoods.setHorizontalAlignment(SwingConstants.RIGHT);
		labelItemStockQuantityLuxuryGoods.setForeground(Color.RED);
		labelItemStockQuantityLuxuryGoods.setFont(new Font("Lato Black", Font.PLAIN, 26));
		labelItemStockQuantityLuxuryGoods.setBounds(460, 805, 50, 30);
		add(labelItemStockQuantityLuxuryGoods);
		
		labelItemImageLuxuryGoods = new JLabel("");
		labelItemImageLuxuryGoods.setIcon(new ImageIcon(IslandPanel.class.getResource("/ui/images/luxuryIcon.png")));
		labelItemImageLuxuryGoods.setHorizontalAlignment(SwingConstants.TRAILING);
		labelItemImageLuxuryGoods.setBackground(Color.BLACK);
		labelItemImageLuxuryGoods.setBounds(395, 800, 120, 120);
		add(labelItemImageLuxuryGoods);
		
		buttonLuxuryGoodsIncreaseQuantity = new JButton("+");
		buttonLuxuryGoodsIncreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldLuxuryGoodsQuantity.getText();
				String newText;
				
				try {
					newText = (Math.min(100, Integer.parseInt(curText) + 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldLuxuryGoodsQuantity.setText(newText);
				
			}
		});
		buttonLuxuryGoodsIncreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonLuxuryGoodsIncreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonLuxuryGoodsIncreaseQuantity.setBounds(525, 800, 45, 25);
		add(buttonLuxuryGoodsIncreaseQuantity);
		
		textFieldLuxuryGoodsQuantity = new JTextField();
		textFieldLuxuryGoodsQuantity.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void removeUpdate(DocumentEvent e) {
				if (!textFieldLuxuryGoodsQuantity.getText().isEmpty())
					updatePrice();
			}
			
			public void insertUpdate(DocumentEvent e) {
				updatePrice();
			}
			
			public void updatePrice() {
				try {
					int quantity = Integer.parseInt(textFieldLuxuryGoodsQuantity.getText());
					Item luxuryGoods = GameEnvironment.getItems().get(0);
					Store store = GameEnvironment.getCurrentIsland().getIslandStore();
					int updatedPrice = Math.max(1, quantity) * store.getItemPrice(luxuryGoods);
					labelLuxuryGoodsPrice.setText(updatedPrice + "¢");
				} catch (NumberFormatException | NullPointerException exception) {
					System.err.println(exception.getMessage());
				}
			}
			
		});
		textFieldLuxuryGoodsQuantity.setText("0");
		textFieldLuxuryGoodsQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLuxuryGoodsQuantity.setFont(new Font("Lato Black", Font.PLAIN, 18));
		textFieldLuxuryGoodsQuantity.setColumns(10);
		textFieldLuxuryGoodsQuantity.setBounds(525, 838, 45, 45);
		add(textFieldLuxuryGoodsQuantity);
		
		buttonBuyLuxuryGoods = new JButton("Buy");
		buttonBuyLuxuryGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyItem(GameEnvironment.getItems().get(0));
			}
		});
		buttonBuyLuxuryGoods.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonBuyLuxuryGoods.setBounds(585, 800, 70, 50);
		add(buttonBuyLuxuryGoods);
		
		buttonSellLuxuryGoods = new JButton("Sell");
		buttonSellLuxuryGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sellItem(GameEnvironment.getItems().get(0));
			}
		});
		buttonSellLuxuryGoods.setFont(new Font("Lato Black", Font.PLAIN, 16));
		buttonSellLuxuryGoods.setBounds(585, 870, 70, 50);
		add(buttonSellLuxuryGoods);
		
		buttonLuxuryGoodsDecreaseQuantity = new JButton("-");
		buttonLuxuryGoodsDecreaseQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String curText = textFieldLuxuryGoodsQuantity.getText();
				String newText;
				
				try {
					newText = (Math.max(0, Integer.parseInt(curText) - 1)) + "";
				} catch (NumberFormatException e) {
					//default back to 0
					newText = "0";
				}
				
				textFieldLuxuryGoodsQuantity.setText(newText);
				
			}
		});
		buttonLuxuryGoodsDecreaseQuantity.setVerticalAlignment(SwingConstants.TOP);
		buttonLuxuryGoodsDecreaseQuantity.setFont(new Font("Lato Black", Font.BOLD, 18));
		buttonLuxuryGoodsDecreaseQuantity.setBounds(525, 895, 45, 25);
		add(buttonLuxuryGoodsDecreaseQuantity);
		
		textAreaItemDescriptionLuxuryGoods = new JTextArea(GameEnvironment.getItems().get(0).getDescription());
		textAreaItemDescriptionLuxuryGoods.setWrapStyleWord(true);
		textAreaItemDescriptionLuxuryGoods.setOpaque(true);
		textAreaItemDescriptionLuxuryGoods.setMargin(new Insets(7, 5, 0, 0));
		textAreaItemDescriptionLuxuryGoods.setLineWrap(true);
		textAreaItemDescriptionLuxuryGoods.setFont(new Font("Lato Black", Font.PLAIN, 13));
		textAreaItemDescriptionLuxuryGoods.setEditable(false);
		textAreaItemDescriptionLuxuryGoods.setBackground(new Color(255, 255, 255, 175));
		textAreaItemDescriptionLuxuryGoods.setBounds(395, 925, 260, 25);
		//fixes selected text highlighting bug
		textAreaItemDescriptionLuxuryGoods.getCaret().deinstall(textAreaItemDescriptionLuxuryGoods);
		add(textAreaItemDescriptionLuxuryGoods);
		
		textAreaTransactionResult = new JTextArea("");
		textAreaTransactionResult.setWrapStyleWord(true);
		textAreaTransactionResult.setOpaque(true);
		textAreaTransactionResult.setMargin(new Insets(5, 15, 15, 15));
		textAreaTransactionResult.setLineWrap(true);
		textAreaTransactionResult.setFont(new Font("Lato Black", Font.PLAIN, 16));
		textAreaTransactionResult.setEditable(false);
		textAreaTransactionResult.setBackground(new Color(255, 255, 255, 175));
		textAreaTransactionResult.setBounds(100, 965, 800, 50);
		//fixes selected text highlighting bug
		textAreaTransactionResult.getCaret().deinstall(textAreaTransactionResult);
		add(textAreaTransactionResult);
		
		buttonLedgerAndInfo = new JButton("View ledger, ship details and island upgrades");
		buttonLedgerAndInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelManager.setPanel("LedgerStatsUpgradesPanel");
			}
		});
		buttonLedgerAndInfo.setBounds(1210, 500, 610, 50);
		add(buttonLedgerAndInfo);
		
		labelBackgroundImage = new JLabel("");
		labelBackgroundImage.setBounds(0, 0, 1920, 1080);
		
		
		add(labelBackgroundImage);
		
	}
	
	private void buttonPressedTravel(JButton button) {
		
		//index of the button in the button array, is the key of the route/island pair in the traveloptions hashmap
		
		Object[] routeIslandPair = travelOptions.get(buttons.indexOf(button));
		
		Route route = (Route) routeIslandPair[0];
		Island destination = (Island) routeIslandPair[1];
		
		boolean eventOccurs = GameEnvironment.doesEventOccur(route);
		
		RoutePanel.updateDetails(route, destination, eventOccurs);
		
		try {
			GameEnvironment.initiateTravel(destination, route);
		} catch (InsufficientGoldException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		GameEnvironment.arriveAtIsland(destination);
		PanelManager.setPanel("RoutePanel");
		
	}
	
	private void buyItem(Item item) {

		Store store = GameEnvironment.getCurrentIsland().getIslandStore();
		
		String quantityText = "";
		
		if (item.getName() == "Luxury Goods")
			quantityText = textFieldLuxuryGoodsQuantity.getText();
		else if (item.getName() == "Alcohol")
			quantityText = textFieldAlcoholQuantity.getText();
		else if (item.getName() == "Raw Materials")
			quantityText = textFieldRawMaterialsQuantity.getText();
		else if (item.getName() == "Food")
			quantityText = textFieldFoodQuantity.getText();
		
		try {
			
			int quantity = Integer.parseInt(quantityText);
			GameEnvironment.buyItem(item, store, quantity);
			resetItemTextFields();
			
			String successMessage = "You purchase " + quantity + " " + item.getName() + " for " + (store.getItemPrice(item) * quantity) + " " + Constants.NAME_CURRENCY;
			successMessage += "\nBeside you, Olard scribbles a note in the ledger, grumbling about the expense.";
			
			textAreaTransactionResult.setForeground(Color.BLACK);
			textAreaTransactionResult.setText(successMessage);
			
		} catch (IllegalArgumentException | InsufficientGoldException | InsufficientItemQuantityException | InsufficientCargoCapacityException e) {
			
			textAreaTransactionResult.setForeground(Color.RED);
			textAreaTransactionResult.setText(e.getMessage());
			
		}

		populateMarketData();

	}
	
	private void sellItem(Item item) {
		
		Store store = GameEnvironment.getCurrentIsland().getIslandStore();
		
		String quantityText = "";
		if (item.getName() == "Luxury Goods")
			quantityText = textFieldLuxuryGoodsQuantity.getText();
		else if (item.getName() == "Alcohol")
			quantityText = textFieldAlcoholQuantity.getText();
		else if (item.getName() == "Raw Materials")
			quantityText = textFieldRawMaterialsQuantity.getText();
		else if (item.getName() == "Food")
			quantityText = textFieldFoodQuantity.getText();
		
		try {
			
			int quantity = Integer.parseInt(quantityText);
			GameEnvironment.sellItem(item, store, quantity);
			resetItemTextFields();
			
			String successMessage = "You sell " + quantity + " " + item.getName() + " for " + (store.getItemPrice(item) * quantity) + " " + Constants.NAME_CURRENCY;
			successMessage += "\nOlard grabs the bag of coin as soon as you make the sale, stashing it somewhere under his armour.";
			
			textAreaTransactionResult.setForeground(Color.BLACK);
			textAreaTransactionResult.setText(successMessage);
			
		} catch (IllegalArgumentException | InsufficientItemQuantityException e) {
			
			textAreaTransactionResult.setForeground(Color.RED);
			textAreaTransactionResult.setText(e.getMessage());
			
		}
		
		populateMarketData();
		
	}
	
	private void resetItemTextFields() {
		
		textFieldLuxuryGoodsQuantity.setText("0");
		textFieldAlcoholQuantity.setText("0");
		textFieldRawMaterialsQuantity.setText("0");
		textFieldFoodQuantity.setText("0");
		
	}
	
	public void populateFieldsWithData() {
		
		setBackgroundByCurrentIsland();
		setNameLabelByCurrentIsland();
		setDescriptionLabelByCurrentIsland();
		populateRoutesByCurrentIsland();
		setCurrentDayLabel();
		setIslandImportsExportsLabels();
		populateMarketData();
		
	}
	
	private void populateMarketData() {
		
		setCargoLabel();
		setItemStockQuantityLabels();
		setItemPriceLabels();
		setCurrentMoneyLabel();
		
		//to update the transaction messagebox without overlapping text
		PanelManager.refreshFrame();
		
	}
	
	private void setItemPriceLabels() {
		
		Store store = GameEnvironment.getCurrentIsland().getIslandStore();
		
		Item luxuryGoods = GameEnvironment.getItems().get(0);
		Item alcohol = GameEnvironment.getItems().get(1);
		Item rawMaterials = GameEnvironment.getItems().get(2);
		Item food = GameEnvironment.getItems().get(3);
		
		labelLuxuryGoodsPrice.setText(store.getItemPrice(luxuryGoods) + "¢");
		labelAlcoholPrice.setText(store.getItemPrice(alcohol) + "¢");
		labelRawMaterialsPrice.setText(store.getItemPrice(rawMaterials) + "¢");
		labelFoodPrice.setText(store.getItemPrice(food) + "¢");
	}
	
	private void setItemStockQuantityLabels() {
		
		Store store = GameEnvironment.getCurrentIsland().getIslandStore();
		
		Item luxuryGoods = GameEnvironment.getItems().get(0);
		Item alcohol = GameEnvironment.getItems().get(1);
		Item rawMaterials = GameEnvironment.getItems().get(2);
		Item food = GameEnvironment.getItems().get(3);
		
		labelItemStockQuantityLuxuryGoods.setText(store.getItemQuantity(luxuryGoods) + "");
		labelItemStockQuantityAlcohol.setText(store.getItemQuantity(alcohol) + "");
		labelItemStockQuantityRawMaterials.setText(store.getItemQuantity(rawMaterials) + "");
		labelItemStockQuantityFood.setText(store.getItemQuantity(food) + "");
		
	}
	
	private void setCargoLabel() {
		
		labelCargo.setText(Player.getShip().getCargo() + "/" + Player.getShip().getCargoCapacity() + " capacity " + Player.getShip().getInventoryString());
		
	}
	
	private void setIslandImportsExportsLabels() {
		
		labelImports.setText(GameEnvironment.getImportsString(GameEnvironment.getCurrentIsland().getIslandStore()));
		labelExports.setText(GameEnvironment.getExportsString(GameEnvironment.getCurrentIsland().getIslandStore()));
		
	}
	
	private void setCurrentDayLabel() {
		labelDay.setText("It is day " + (GameEnvironment.getHoursSinceStart() / 24) + " of " + GameEnvironment.getGameDuration() + ".");
	}
	
	private void setCurrentMoneyLabel() {
		labelWealth.setText("You have " + Player.getGold() + " " + Constants.NAME_CURRENCY + ".");
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
	
	private void setNameLabelByCurrentIsland() {
		
		String curIslandName = GameEnvironment.getCurrentIsland().getIslandName();
		labelIslandName.setText("<html>" + curIslandName + "</html>");
		
	}
	
	private void setDescriptionLabelByCurrentIsland() {
		
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
				imagePath = "/ui/images/dullTunia.png";
				break;
			case Constants.ISLAND_SKULLHAVEN:
				imagePath = "/ui/images/dullSkullHaven.png";
				break;
			case Constants.ISLAND_SEANOMADS:
				imagePath = "/ui/images/dullSeaNomads.png";
				break;
			default:
				imagePath = "/ui/images/dullSandyFields.png";
		}
		
		labelBackgroundImage.setIcon(new ImageIcon(IslandPanel.class.getResource(imagePath)));
	}
}
