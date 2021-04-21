package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Constants;

public class PanelManager {

	private static JFrame frame;
	
	public static JPanel islandPanel;
	public static JPanel routePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		
					initialize();
			
					islandPanel = new IslandPanel();
					routePanel = new RoutePanel();
					
					setPanel(islandPanel);

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
	
	public static void setPanel(JPanel panel) {
		
		frame.setContentPane(panel);
		frame.revalidate();
			
	}

}
