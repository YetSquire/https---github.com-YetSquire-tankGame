package src;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
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
	private JPanel card1;
	private JPanel card2;
	private JLabel healthLabel;
	private JLabel scoreLabel;
	private JLabel endScreen;
	private GamePanel gp;
	private JButton restart;
	public static boolean exit;
	private CardLayout card;
	private Container c;

	public Game() throws InterruptedException
	{
		exit = false;
		gp = new GamePanel();
		holder = new JPanel();
		JPanel panel = new JPanel();
		frame = new JFrame();
		c = frame.getContentPane();
		card = new CardLayout();
		c.setLayout(card);	
		card1 = new JPanel();
		card2 = new JPanel();
		card1.setLayout(new BorderLayout());	
		card2.setLayout(new BorderLayout());
		String health = "            Health: " + Constants.tankHP;
		String score = "Score: " + Constants.score + "            ";
		healthLabel = new JLabel(health, SwingConstants.LEFT);
		scoreLabel = new JLabel(score, SwingConstants.RIGHT);
		panel.add(scoreLabel);
		panel.add(healthLabel);
		card1.add(panel, BorderLayout.NORTH);
		card1.add(gp, BorderLayout.CENTER);
		frame.add(card1);
        frame.pack();
        frame.setSize(Constants.panelWidth, Constants.panelWidth);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		endScreen = new JLabel("Game Over!", SwingConstants.CENTER);
		restart = new JButton("Play Again");
		restart.setPreferredSize(new Dimension(50, 100));
		restart.addMouseListener(new MouseAdapter() {
			public void mousePressed (MouseEvent e)
			{
				card.next(c);
				frame.repaint();
				exit = false;
				gp.reset();
			}
		});
		endScreen.setFont(new Font("Impact", Font.BOLD, 60));
		holder.setLayout(new BorderLayout());
		holder.add(endScreen, BorderLayout.CENTER);
		holder.add(restart, BorderLayout.SOUTH);
		frame.add(holder);
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

			card.next(c);

		}
	}

	
}

