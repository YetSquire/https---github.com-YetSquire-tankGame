package src;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Game implements Runnable{
	
	private JFrame frame;
	private JPanel holder;
	private JPanel panelEnd;
	private JPanel card1;
	private JPanel card2;
	private JLabel healthLabel;
	private JLabel scoreLabel;
	private JLabel highLabel;
	private JLabel lifeLabel;
	private static JLabel countLabel;
	private JLabel endScreen;
	private JLabel hold1;
	private JLabel hold2;
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
		panel.setLayout(new GridLayout(1, 5, Constants.panelWidth/12, 1));
		frame = new JFrame();
		c = frame.getContentPane();
		card = new CardLayout();
		c.setLayout(card);	
		card1 = new JPanel();
		card2 = new JPanel();
		card1.setLayout(new BorderLayout());	
		card2.setLayout(new BorderLayout());
		String health = "Health: " + Constants.tankHP;
		String score = "Score: " + Constants.score;
		String hiScore = "High Score: " + Constants.hiScore;
		String lives = "Lives: " + ((Tank) gp.actors.get(0)).getLives();
		healthLabel = new JLabel(health);
		scoreLabel = new JLabel(score);
		countLabel = new JLabel();
		highLabel = new JLabel(hiScore);
		lifeLabel = new JLabel(lives);
		panel.add(highLabel);
		panel.add(scoreLabel);
		panel.add(countLabel);
		panel.add(healthLabel);
		panel.add(lifeLabel);
		card1.add(panel, BorderLayout.NORTH);
		card1.add(gp, BorderLayout.CENTER);
		frame.add(card1);
        frame.pack();
        frame.setSize(Constants.panelWidth, Constants.panelWidth);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		endScreen = new JLabel("Game Over!", SwingConstants.CENTER);
		restart = new JButton("Play Again");
		restart.setPreferredSize(new Dimension(50, 100));
		restart.addMouseListener(new MouseAdapter() {
			public void mousePressed (MouseEvent e)
			{
				card.next(c);
				frame.repaint();
				exit = false;
				gp.reset(3);
			}
		});
		endScreen.setFont(new Font("Impact", Font.BOLD, 60));
		holder.setLayout(new BorderLayout());
		hold1 = new JLabel();
		hold2 = new JLabel("", SwingConstants.RIGHT);
		panelEnd = new JPanel();
		panelEnd.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelEnd.setLayout(new GridLayout(1, 2));
		panelEnd.add(hold1);
		panelEnd.add(hold2);
		holder.add(panelEnd, BorderLayout.NORTH);

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
			String hiScore = "High Score: " + Constants.hiScore;
			String lives = "Lives: " + ((Tank) gp.actors.get(0)).getLives();
			lifeLabel.setText(lives);
			highLabel.setText(hiScore);
			scoreLabel.setText("Score: " + Constants.score);
			if (gp.checkExit()) exit = true;;
		}
		gp.update(healthLabel);
		gp.repaint();

		if (exit)
		{
			try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			String hiScore = "High Score: " + Constants.hiScore;
			hold1.setText(hiScore);
			hold2.setText("Score: " + Constants.score);
			card.next(c);
			Constants.score = 0;
		}
	}

	public static void waitLife()
	{
		countLabel.setText("3");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countLabel.setText("2");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countLabel.setText("1");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countLabel.setText("");
	}
}

