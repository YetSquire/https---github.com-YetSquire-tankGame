package src;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game implements Runnable{
	
	private JFrame frame;
	private JPanel holder;
	private JLabel healthLabel;
	private JLabel scoreLabel;
	private GamePanel gp;
	private JButton restart;
	private boolean exit;
	private Thread drawy;

	public Game()
	{
		exit = false;
		gp = new GamePanel();
		holder = new JPanel();
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
		drawy = new Thread(this);
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
			//new game is working, however the holder panel is not disappearing
			gp.setVisible(false);
			holder.setLayout(new BorderLayout());
			JLabel endScreen = new JLabel("Game Over!", SwingConstants.CENTER);
			restart = new JButton("Play Again");
			restart.setPreferredSize(new Dimension(50, 100));
			restart.addMouseListener(new MouseAdapter() {
				public void mousePressed (MouseEvent e)
				{
					holder.setVisible(false);
					gp.setVisible(true);
					exit = false;
					gp.reset();
					System.out.println("yes");
					while (!exit)
					{
						System.out.println("yes");
						gp.repaint();
			gp.update(healthLabel);
			scoreLabel.setText("Score: " + Constants.score + "            ");
			if (gp.checkExit()) exit = true;
			System.out.println(GamePanel.actors.get(0).hp);
					}
				}
			});
			endScreen.setFont(new Font("Impact", Font.BOLD, 60));
			holder.add(endScreen, BorderLayout.CENTER);
			holder.add(restart, BorderLayout.SOUTH);
			frame.add(holder, BorderLayout.CENTER);
			frame.pack();
        	frame.setSize(Constants.panelWidth, Constants.panelWidth);
        	frame.setVisible(true);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}

