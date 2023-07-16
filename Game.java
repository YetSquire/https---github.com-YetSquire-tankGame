

import javax.swing.JFrame;

public class Game implements Runnable{
	
	private JFrame frame;
	private GamePanel gp;
	
	public Game()
	{
		gp = new GamePanel(this);
		frame = new JFrame();		
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
			gp.update();
			gp.repaint();
			
		}
	}
}

