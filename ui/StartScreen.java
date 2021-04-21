package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class StartScreen {

	private static JFrame frame;
	private JPanel panel;
	
	private static StartScreen screen1;
	private static IslandScreen screen2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					screen1 = new StartScreen();
					screen2 = new IslandScreen();
					screen1.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		
		JButton btnGoToScreen = new JButton("go to screen2");
		btnGoToScreen.setBounds(453, 216, 150, 25);
		panel.add(btnGoToScreen);
		
		
		JLabel lblScreen = new JLabel("screen 1");
		lblScreen.setBounds(721, 10, 95, 15);
		panel.add(lblScreen);
		btnGoToScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.toString());
				setPanel("islandscreen");
			}
		});
		
		btnGoToScreen.setOpaque(false);
		
		btnGoToScreen.setBackground(new Color(200,200,0,0));
		//btnGoToScreen.setBorder(null);
		btnGoToScreen.setFocusPainted(false);
//		/btnGoToScreen.setOpaque(false);
		//btnGoToScreen.repaint();
		//btnGoToScreen.revalidate();
		btnGoToScreen.setForeground(new Color(255,0,0,255));
		//btnGoToScreen.setOpaque(false);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(StartScreen.class.getResource("/ui/Starman.jpg")));
		label.setBounds(306, 129, 403, 243);
		panel.add(label);
		
	}
	
	public void showFrame() {
		frame.setVisible(true);
	}
	
	public void hideFrame() {
		frame.setVisible(false);
	}
	
	public JPanel getPanel() {
		return panel;
		//frame.setVisible(true);
	}
	
	public static void setPanel(String screen) {
	
		if (screen == "startscreen") {
			frame.setContentPane(screen1.getPanel());
			frame.revalidate();
			//screen1.showFrame();
			//screen2.hideFrame();
		}
		else if (screen == "islandscreen") {
			frame.setContentPane(screen2.getPanel());
			frame.revalidate();
			//repaint();
			//screen2.showFrame();
			//screen1.hideFrame();
		}
			
		
	}

}
