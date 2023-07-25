package src;


import java.awt.BorderLayout;
import java.awt.Font;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game implements Runnable{
	
	private JFrame frame;
	private JLabel healthLabel;
	private JLabel scoreLabel;
	private GamePanel gp;
	private boolean exit;
	
	public Game()
	{
		exit = false;
		gp = new GamePanel(this);
		JPanel panel = new JPanel();
		frame = new JFrame();
		frame.setLayout(new BorderLayout());	
		String health = "            Health: " + Constants.tankHP;
		String score = "Score: " + Constants.score + "            ";
		healthLabel = new JLabel(health, SwingConstants.LEFT);
		scoreLabel = new JLabel(score, SwingConstants.RIGHT);
		panel.add(scoreLabel);
		panel.add(healthLabel);
		frame.add(panel, BorderLayout.NORTH);
        frame.add(gp);
        frame.pack();
        frame.setSize(Constants.panelWidth, Constants.panelWidth);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread drawy = new Thread(this);
		drawy.start();
	}

	
	@Override
	public void run() {
		while (!exit)
		{
			gp.repaint();
			gp.update(healthLabel);
			scoreLabel.setText("Score: " + Constants.score + "            ");
			if (gp.checkExit()) exit = true;;
		}
		gp.update(healthLabel);
		gp.repaint();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (exit)
		{
			gp.setVisible(false);
			JLabel endScreen = new JLabel("Game Over!", SwingConstants.CENTER);
			endScreen.setFont(new Font("Impact", Font.BOLD, 60));
			frame.add(endScreen);
			frame.pack();
        	frame.setSize(Constants.panelWidth, Constants.panelWidth);
        	frame.setVisible(true);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}

