package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	protected static ArrayList<Actor> actors = new ArrayList<>();
	protected static Tank tank;
	private boolean addShell = false;
	private boolean exit = false;
	protected static boolean altered = false;

	public void update(JLabel jlabel) {

		jlabel.setText("Health " + actors.get(0).hp);
		altered = true;
		actors.removeIf(s -> s.gone == true);
		for (Actor s: actors)
		{
			s.update();
		}
		altered = false;
		if (addShell)
		{
			altered = true;
			int x1 = (int) (((Tank)actors.get(0)).getCX() - 100);
			int y1 = (int) (actors.get(0).getY() + Constants.tankHeight / 2 - 5);
			Point center = new Point((int)((Tank)actors.get(0)).getCX(), (int) ((Tank)actors.get(0)).getCY());
			Point one = ((Tank)actors.get(0)).rotate(new Point(x1, y1), center);
			actors.add(new Shell(one.x, one.y, actors.get(0).angle, Constants.shellHP));
			addShell = false;
		}
		altered = false;
		if (Math.random() < 0.00001 && actors.size() < 10)
		{
			double rA;
			int x;
			int y;
			int r = (int) ((Math.random() * 100) + 25);
			int speed = (int) (25 / (Math.random() * r)) + 4;
			double g = Math.random();
			if (g < 0.25) {
				x = (int) (Math.random() * Constants.panelWidth);
				y = 10;
				if (x > Constants.panelWidth / 2)
					rA = Math.random() * (-Math.PI / 2);
				else
					rA = Math.random() * (Math.PI / 2) + Math.PI;
			} else if (g < 0.5) {
				y = (int) (Math.random() * Constants.panelHeight);
				x = 10;
				if (y < Constants.panelHeight / 2)
					rA = Math.random() * (Math.PI / 2) + Math.PI;
				else
					rA = Math.random() * (-Math.PI / 2) + Math.PI;
			} else if (g < 0.75) {
				x = (int) (Math.random() * Constants.panelWidth);
				y = Constants.panelHeight - 10;
				if (x < Constants.panelWidth / 2)
					rA = Math.random() * (-Math.PI / 4) + Math.PI / 2;
				else
					rA = Math.random() * (Math.PI / 4);
			} else {
				y = (int) (Math.random() * Constants.panelHeight);
				x = Constants.panelWidth - 10;
				if (y < Constants.panelHeight / 2)
					rA = Math.random() * (-Math.PI / 4);
				else
					rA = Math.random() * (Math.PI / 4);
			}
			altered = true;
			actors.add(new Enemy(r, Constants.enemyHP, x, y, speed, rA));
			altered = false;
		}
		//System.out.println(actors.get(0).getHP());
		for (Actor s: actors)
			if (s.getClass().getSimpleName().equals("Shell") || s.getClass().getSimpleName().equals("Tank"))
				for (Actor a: actors)
					if (a.getClass().getSimpleName().equals("Enemy"))
						 if (s.intersects(a)) 
						 {
							altered = true;
							a.hp -= 50;
							s.hp -= 10;
							altered = false;

						}
		if (actors.get(0).hp <= 0) exit = true;
	}

	public GamePanel(Game game) {
		tank = new Tank(Constants.panelWidth/2, Constants.panelHeight/2, Math.toRadians(45), Constants.tankHP);
		actors.add(tank);
		setSize(Constants.panelHeight, Constants.panelWidth);
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "UP");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
		this.getInputMap().put(KeyStroke.getKeyStroke("W"), "UP");
		this.getInputMap().put(KeyStroke.getKeyStroke("S"), "DOWN");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"), "LEFT");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"), "RIGHT");
		//this.getInputMap().put(KeyStroke.getKeyStroke("Q"), "SHOOT");
		this.getActionMap().put("UP", new MoveAction("UP", 1));
		this.getActionMap().put("DOWN", new MoveAction("DOWN", 1));
		this.getActionMap().put("LEFT", new MoveAction("LEFT", 1));
		this.getActionMap().put("RIGHT", new MoveAction("RIGHT", 1));
		this.getActionMap().put("ROTATE", new MoveAction("ROTATE", 1));
		//this.getActionMap().put("SHOOT", new MoveAction("SHOOT", 1));
		setFocusable(true);
		setVisible(true);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				addShell = true;
			}
		});

	}

	@Override
	public void paintComponent(Graphics g1) {
		if (!altered)
		{
			super.paintComponent(g1);
		for (Actor s : actors) {
			s.draw(g1);
		}
		}
		Constants.panelHeight = this.getHeight();
		Constants.panelWidth = this.getWidth();
	}

	public boolean checkExit()
	{
		return exit;
	}

}

@SuppressWarnings("serial")
class MoveAction extends AbstractAction {
	private String direction;
	private int player;

	MoveAction(String direction, int player) {

		this.direction = direction;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int xt = GamePanel.actors.get(0).getX();
		int yt = GamePanel.actors.get(0).getY();
		GamePanel.altered = true;
		if (direction.equals("UP") && !((Tank)GamePanel.actors.get(0)).noUp) {
			GamePanel.actors.get(0).y = (yt - Constants.tankSpeed);
			if (((Tank)GamePanel.actors.get(0)).getHor())
				((Tank)GamePanel.actors.get(0)).horz();
		}
		if (direction.equals("DOWN") && !((Tank)GamePanel.actors.get(0)).noDown) {
			GamePanel.actors.get(0).setY(yt + Constants.tankSpeed);
			if (((Tank)GamePanel.actors.get(0)).getHor())
				((Tank)GamePanel.actors.get(0)).horz();
		}
		if (direction.equals("LEFT") && !((Tank)GamePanel.actors.get(0)).noLeft) {
			GamePanel.actors.get(0).setX(xt - Constants.tankSpeed);
			if (!((Tank)GamePanel.actors.get(0)).getHor())
				((Tank)GamePanel.actors.get(0)).horz();
		}
		if (direction.equals("RIGHT") && !((Tank)GamePanel.actors.get(0)).noRight) {
			GamePanel.actors.get(0).setX(xt + Constants.tankSpeed);
			if (!((Tank)GamePanel.actors.get(0)).getHor())
				((Tank)GamePanel.actors.get(0)).horz();
		}
		if (direction.equals("ROTATE"))
			GamePanel.actors.get(0).angle = (GamePanel.actors.get(0).angle + 0.1);

		GamePanel.altered = false;
		// if (direction.equals("SHOOT")) {
		// 	double rA;
		// 	int x;
		// 	int y;
		// 	int r = (int) (Math.random() * 25) + 100;
		// 	int speed = (int) (25 / (Math.random() * r)) + 4;
		// 	double g = Math.random();
		// 	if (g < 0.25) {
		// 		x = (int) (Math.random() * Constants.panelWidth);
		// 		y = 10;
		// 		if (x > Constants.panelWidth / 2)
		// 			rA = Math.random() * (-Math.PI / 2);
		// 		else
		// 			rA = Math.random() * (Math.PI / 2) + Math.PI;
		// 	} else if (g < 0.5) {
		// 		y = (int) (Math.random() * Constants.panelHeight);
		// 		x = 10;
		// 		if (y < Constants.panelHeight / 2)
		// 			rA = Math.random() * (Math.PI / 2) + Math.PI;
		// 		else
		// 			rA = Math.random() * (-Math.PI / 2) + Math.PI;
		// 	} else if (g < 0.75) {
		// 		x = (int) (Math.random() * Constants.panelWidth);
		// 		y = Constants.panelHeight - 10;
		// 		if (x < Constants.panelWidth / 2)
		// 			rA = Math.random() * (-Math.PI / 4) + Math.PI / 2;
		// 		else
		// 			rA = Math.random() * (Math.PI / 4);
		// 	} else {
		// 		y = (int) (Math.random() * Constants.panelHeight);
		// 		x = Constants.panelWidth - 10;
		// 		if (y < Constants.panelHeight / 2)
		// 			rA = Math.random() * (-Math.PI / 4);
		// 		else
		// 			rA = Math.random() * (Math.PI / 4);
		// 	}
		// 	GamePanel.actors.add(new Enemy(r, 10, x, y, speed, rA));
		// }
	}
}
