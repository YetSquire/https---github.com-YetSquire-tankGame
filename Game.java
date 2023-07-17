

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game implements Runnable{
	
	private JFrame frame;
	private JLabel jlabel;
	private GamePanel gp;
	
	public Game()
	{
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
		while (true)
		{
			gp.update(jlabel);
			gp.repaint();
		}
	}
}

