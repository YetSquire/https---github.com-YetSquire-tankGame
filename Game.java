

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game implements Runnable{
	
	private JFrame frame;
	private JLabel jlabel;
	private GamePanel gp;
	private boolean exit;
	
	public Game()
	{
		exit = false;
		gp = new GamePanel(this);
		frame = new JFrame();	
		String health = "Health " + Constants.tankHP;
		jlabel = new JLabel(health);
		gp.add(jlabel);
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
			gp.update(jlabel);
			if (gp.checkExit()) exit = true;;
		}
		gp.update(jlabel);
		gp.repaint();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (exit)
		{
			gp.setVisible(false);
			JLabel endScreen = new JLabel("Game Over!");
			frame.add(endScreen);
			frame.pack();
        	frame.setSize(Constants.panelWidth, Constants.panelWidth);
        	frame.setVisible(true);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}

