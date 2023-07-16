
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	protected static ArrayList<Actor> actors = new ArrayList<>();
	protected static Tank tank;
	private boolean addShell = false;
	private boolean altered = false;

	public void update() {
		altered = true;
		tank.update();
		actors.removeIf(s -> s.gone == true);
		for (Actor s: actors)
		{
			s.update();
		}
		altered = false;
		if (addShell)
		{
			altered = true;
			int x1 = (int) (tank.getCX() - 100);
			int y1 = (int) (tank.getY() + Constants.tankHeight / 2 - 5);
			Point center = new Point((int) tank.getCX(), (int) tank.getCY());
			Point one = tank.rotate(new Point(x1, y1), center);
			actors.add(new Shell(one.x, one.y, tank.getAngle(), Constants.shellHP));
			addShell = false;
		}
		altered = false;
		if (Math.random() < 0.00001)
		{
			double rA;
			int x;
			int y;
			int r = (int) (Math.random() * 25) + 100;
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
		for (Actor s: actors)
			if (s.getClass().getSimpleName().equals("Shell"))
				for (Actor a: actors)
					if (a.getClass().getSimpleName().equals("Enemy"))
						 if (s.intersects(a)) 
						 {
							s.gone = true;
							altered = true;
							a.hp -= 50;
							altered = false;
						}
	}

	public GamePanel(Game game) {
		tank = new Tank();
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
		tank.draw(g1);
		}
		Constants.panelHeight = this.getHeight();
		Constants.panelWidth = this.getWidth();
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
		double xt = GamePanel.tank.getX();
		double yt = GamePanel.tank.getY();
		if (direction.equals("UP") && !GamePanel.tank.noUp) {
			GamePanel.tank.setY(yt - Constants.tankSpeed);
			if (GamePanel.tank.getHor())
				GamePanel.tank.horz();
		}
		if (direction.equals("DOWN") && !GamePanel.tank.noDown) {
			GamePanel.tank.setY(yt + Constants.tankSpeed);
			if (GamePanel.tank.getHor())
				GamePanel.tank.horz();
		}
		if (direction.equals("LEFT") && !GamePanel.tank.noLeft) {
			GamePanel.tank.setX(xt - Constants.tankSpeed);
			if (!GamePanel.tank.getHor())
				GamePanel.tank.horz();
		}
		if (direction.equals("RIGHT") && !GamePanel.tank.noRight) {
			GamePanel.tank.setX(xt + Constants.tankSpeed);
			if (!GamePanel.tank.getHor())
				GamePanel.tank.horz();
		}
		if (direction.equals("ROTATE"))
			GamePanel.tank.setAngle(GamePanel.tank.getAngle() + 0.1);
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
