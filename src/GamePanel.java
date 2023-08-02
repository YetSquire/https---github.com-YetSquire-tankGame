package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	protected static ArrayList<Actor> actors;
	protected static Tank tank;
	private boolean addShell;
	private boolean exit;
	private Game parent;
	protected static boolean altered;

	public void update(JLabel jlabel) {

		jlabel.setText("            Health " + actors.get(0).hp);
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
			int x1 = (((Tank)actors.get(0)).x - Constants.barrel);
			int y1 = (actors.get(0).getY());
			Point center = new Point(((Tank)actors.get(0)).x, ((Tank)actors.get(0)).y);
			Point one = ((Tank)actors.get(0)).rotate(new Point(x1, y1), center);
			actors.add(new Shell(one.x, one.y, actors.get(0).angle, Constants.shellHP, Constants.shellSpeed));
			addShell = false;
		}
		altered = false;
		if (actors.size() < 10)
		{
			double rA;
			int x;
			int y;
			int r = (int) ((Math.random() * 100) + 25);
			int speed = (int)(r/20) + 4;
			int hp = r*3;
			double f = Math.random();
			double g = Math.random();
			if (f > 0.5)
			{
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
			}
			else
			{
				if (g < 0.25) {
				x = (int) (Math.random() * Constants.panelWidth);
				y = 10;
			} else if (g < 0.5) {
				y = (int) (Math.random() * Constants.panelHeight);
				x = 10;
			} else if (g < 0.75) {
				x = (int) (Math.random() * Constants.panelWidth);
				y = Constants.panelHeight - 10;
			} else {
				y = (int) (Math.random() * Constants.panelHeight);
				x = Constants.panelWidth - 10;
			}
			rA = Math.atan2((y - actors.get(0).getY()), x - actors.get(0).getX());
			
			}
			altered = true;
			double hold = Math.random();
			if (hold < 0.33)
			{
				actors.add(new Triangle(r, hp, x, y, speed, rA));
			}
			else if (hold > 0.67)
			{
				actors.add(new Square(r, hp, x, y, speed, rA));
			}
			else
			{
				actors.add(new Square(r, hp, x, y, speed, rA));
			}
			altered = false;
		}
		//System.out.println(actors.get(0).getHP());
		for (Actor s: actors)
			if (s.getClass().getSimpleName().equals("Shell") || s.getClass().getSimpleName().equals("Tank"))
				for (Actor a: actors)
					if (!(a.getClass().getSimpleName().equals("Shell") || a.getClass().getSimpleName().equals("Tank")) && s.intersects(a.getArea())) 
						 {
							altered = true;
							a.hp -= 50;
							s.hp -= Math.abs(a.hp/4);
							if (s.hp < 0) s.hp = 0;
							altered = false;
							if (s.getClass().getSimpleName().equals("Shell") && a.hp < 0) Constants.score += a.getOGHP();

						}
		if (actors.get(0).hp <= 0)
		{
			if (((Tank)actors.get(0)).getLives() <= 1) exit = true;
			else 
			{
				parent.waitLife();
				reset(((Tank)actors.get(0)).getLives() -1);
				repaint();
			}
			if (Constants.hiScore < Constants.score) Constants.hiScore = Constants.score;
		}
	}

	public GamePanel(Game parent) {
		this.parent = parent;
		addShell = false;
		altered = false;
		exit = false;
		actors = new ArrayList<Actor>();
		tank = new Tank(Constants.panelWidth/2, Constants.panelHeight/2, Math.toRadians(45), Constants.tankHP);
		actors.add(tank);
		setSize(Constants.panelHeight, Constants.panelWidth);
		setBindings();
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

	public void reset(int lives)
	{
		actors = new ArrayList<Actor>();
		tank = new Tank(Constants.panelWidth/2, Constants.panelHeight/2, Math.toRadians(45), Constants.tankHP);
		tank.setLives(lives);
		actors.add(tank);
		addShell = false;
		altered = false;
		exit = false;
	}

	public void setBindings()
	{
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UP");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "UP");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "DOWN");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "LEFT");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "RIGHT");
		//this.getInputMap().put(KeyStroke.getKeyStroke("Q"), "SHOOT");
		this.getActionMap().put("UP", new MoveAction("UP", 1));
		this.getActionMap().put("DOWN", new MoveAction("DOWN", 1));
		this.getActionMap().put("LEFT", new MoveAction("LEFT", 1));
		this.getActionMap().put("RIGHT", new MoveAction("RIGHT", 1));
		this.getActionMap().put("ROTATE", new MoveAction("ROTATE", 1));
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
		
	}
}
