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
import javax.swing.SwingConstants;

public class IslandScreen {

	//private JFrame frame;
	private JPanel panel;



	/**
	 * Create the application.
	 */
	public IslandScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frame = new JFrame();
		//frame.setBounds(100, 100, 1280, 720);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);
		
		JLabel lblBobsYourUncle = new JLabel("bob's your uncle");
		lblBobsYourUncle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBobsYourUncle.setBounds(434, 140, 350, 121);
		panel.add(lblBobsYourUncle);
		//frame.getContentPane().add(panel);
		
		JButton btnGoToScreen = new JButton("go to screen1");
		btnGoToScreen.setBounds(398, 215, 150, 25);
		panel.add(btnGoToScreen);
		
		JLabel lblIslandScreen = new JLabel("Island screen");
		lblIslandScreen.setBounds(721, 10, 95, 15);
		panel.add(lblIslandScreen);
		btnGoToScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StartScreen.setPanel("startscreen");
			}
		});
		
		btnGoToScreen.setOpaque(true);
		
		btnGoToScreen.setBackground(new Color(0,200,0,20));
		btnGoToScreen.setOpaque(false);
		btnGoToScreen.repaint();
		btnGoToScreen.revalidate();
		btnGoToScreen.setForeground(new Color(0,0,0,20));
	}
	
	public JPanel getPanel() {
		return panel;
		//frame.setVisible(true);
	}
	
	public void hideFrame() {
		//frame.setVisible(false);
	}

}
