package src;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Game implements Runnable{
	
	private JFrame frame;
	private JPanel panelEnd;
	private JPanel card1;
	private JPanel card2;
	private JPanel card3;
	private JLabel healthLabel;
	private JLabel scoreLabel;
	private JLabel highLabel;
	private JLabel rechargeLabel;
	private JLabel lifeLabel;
	private JLabel countLabel;
	private JLabel endScreen;
	private JLabel hold1;
	private JLabel hold2;
	private GamePanel gp;
	private JButton restart;
	public static boolean exit;
	private CardLayout card;
	private Container c;
	private Thread t;
	private boolean running;

	public Game() throws InterruptedException
	{
		running = false;
		exit = false;
		gp = new GamePanel(this);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5, Constants.panelWidth/12, 1));
		frame = new JFrame();
		c = frame.getContentPane();
		card = new CardLayout();
		c.setLayout(card);	
		card1 = new JPanel();
		card2 = new JPanel();
		card3 = new JPanel();
		card1.setLayout(new BorderLayout());	
		card2.setLayout(new BorderLayout());
		card3.setLayout(new BorderLayout());
		String health = "Health: " + Constants.tankHP;
		String score = "Score: " + Constants.score;
		String hiScore = "High Score: " + Constants.hiScore;
		String lives = "Lives: " + ((Tank) GamePanel.actors.get(0)).getLives();
		t = new Thread(new ReloadThread(rechargeLabel));
		healthLabel = new JLabel(health);
		scoreLabel = new JLabel(score);
		countLabel = new JLabel("", SwingConstants.CENTER);
		highLabel = new JLabel(hiScore);
		lifeLabel = new JLabel(lives);
		rechargeLabel = new JLabel();
		String recharge = "Reloading: ";
		int i = 150;
		String block = Character.toString ((char) i);
		recharge = recharge.concat(block);
		recharge = recharge.concat(block);
		recharge = recharge.concat(block);
		rechargeLabel.setText(recharge);
		panel.add(highLabel);
		panel.add(scoreLabel);
		countLabel.setFont(new Font("Calibri", Font.BOLD, 60));
		card2.add(countLabel);
		panel.add(rechargeLabel);
		panel.add(healthLabel);
		panel.add(lifeLabel);
		card1.add(panel, BorderLayout.NORTH);
		card1.add(gp, BorderLayout.CENTER);
		frame.add(card1);
		frame.add(card2);
		frame.add(card3);
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
		hold1 = new JLabel();
		hold2 = new JLabel("", SwingConstants.RIGHT);
		panelEnd = new JPanel();
		panelEnd.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelEnd.setLayout(new GridLayout(1, 2));
		panelEnd.add(hold1);
		panelEnd.add(hold2);
		card3.add(panelEnd, BorderLayout.NORTH);
		card3.add(endScreen, BorderLayout.CENTER);
		card3.add(restart, BorderLayout.SOUTH);

	}

	
	@Override
	public void run() {
		while (!exit)
		{
			if (!((Tank)(GamePanel.actors.get(0))).getReload() && !t.isAlive())
			{
				t = new Thread(new ReloadThread(rechargeLabel));
				t.start();
			}
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
			card.last(c);
			Constants.score = 0;
		}
	}

	public void waitLife()
	{
		healthLabel.setText("Health: 0");//no idea why actual update doesn't work
		gp.repaint();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		card.next(c);
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
		card.first(c);

	}
}

