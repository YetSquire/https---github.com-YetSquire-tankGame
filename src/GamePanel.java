package src;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.*;

public class GamePanel extends JPanel {
	protected ArrayList<Actor> actors;
	protected Tank tank;

	//change
	private static int expX;
	private static int expY;
	public static int enemyNum;
	private boolean addBomb;
	private boolean addShell;
	private static boolean explosion; 
	private boolean exit;
	private Game parent;
	protected boolean altered;
	private Ellipse2D.Double radar;
	private boolean growing;

	public void update(JLabel jlabel) {

		jlabel.setText("            Health " + actors.get(0).hp);
		altered = true;
		if (growing) radarGrowth();
		for (Actor s: actors)
		{
			s.update();
			if (s.gone && !s.getFriendly()) enemyNum--;
		}
		actors.removeIf(s -> s.gone == true);

		if (addShell)
		{
			int x1 = (((Tank)actors.get(0)).x - Constants.barrel);
			int y1 = (actors.get(0).getY());
			Point center = new Point(((Tank)actors.get(0)).x, ((Tank)actors.get(0)).y);
			Point one = ((Tank)actors.get(0)).rotate(new Point(x1, y1), center);
			actors.add(new Shell(one.x, one.y, actors.get(0).angle, Constants.shellHP, Constants.shellSpeed));
			addShell = false;
		}
		if (addBomb)
		{
			int x1 = (((Tank)actors.get(0)).x - Constants.barrel);
			int y1 = (actors.get(0).getY());
			Point center = new Point(((Tank)actors.get(0)).x, ((Tank)actors.get(0)).y);
			Point one = ((Tank)actors.get(0)).rotate(new Point(x1, y1), center);
			actors.add(new Bomb(one.x, one.y, actors.get(0).angle, Constants.shellHP, Constants.bombSize));
			addBomb = false;
			((Tank)actors.get(0)).setReload(false);
		}

		if (explosion)
		{
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			actors.add(new Shell(expX, expY, Math.random()*Math.PI*2, 100, Constants.shellSpeed));
			explosion = false;
		}
		altered = false;
		if (enemyNum < 15)
		{
			double rA;
			int x;
			int y;
			int r = (int) ((Math.random() * 100) + 25);
			int speed = (int)(r/20) + 4;
			int hp = r*3;
			double f = Math.random();
			double g = Math.random();
			if (f > 0.75)
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
			double hold = Math.random();
			altered = true;
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
			enemyNum++;
			altered = false;
		}

		for (Actor s: actors)
			if (s.getFriendly())
				for (Actor a: actors)
					if (!a.getFriendly() && s.intersects(a.getArea()))
						 {
							altered = true;
							a.hp -= 50;
							s.hp -= a.hp/4;
							if (s.getClass().getSimpleName().equals("Tank")) a.hp -= 1000;
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
		enemyNum = 0;
		radar = new Ellipse2D.Double(0, 0, 0, 0);
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
		setBackground(new Color(0, 100, 50));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton()==MouseEvent.BUTTON1)
				{
					addShell = true;
				}
				else if (me.getButton()==MouseEvent.BUTTON3)
				{
					if (((Tank)actors.get(0)).getReload()) addBomb = true;
				}
			}
		});

	}

	public void callRadar()
	{
		radar.setFrame(actors.get(0).getX(), actors.get(0).getY(), radar.getWidth(), radar.getHeight());
		growing = true;
	}

	@Override
	public void paintComponent(Graphics g1) {
		if (!altered)
		{
		super.paintComponent(g1);
		g1.setColor(Color.white);
		Graphics2D g2 = (Graphics2D) g1;
		g2.setStroke(new BasicStroke(100));
		if (growing) g2.drawOval((int)radar.getX(), (int)radar.getY(), (int)radar.getWidth(), (int)radar.getHeight());
		g2.setStroke(new BasicStroke(3));
		g1.setColor(Color.red);
		for (Actor s : actors) {
			s.draw(g1);
		}
		}
		Constants.panelHeight = this.getHeight();
		Constants.panelWidth = this.getWidth();
	}

	public static void explosion(int x, int y)
	{
		explosion = true;
		expX = x;
		expY = y;
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
		addBomb = false;
		enemyNum = 0;
		altered = false;
		exit = false;
		radar.setFrame(0, 0, 0, 0);
		growing = false;
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
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "RADAR");
		this.getActionMap().put("UP", new MoveAction("UP", 1, this));
		this.getActionMap().put("DOWN", new MoveAction("DOWN", 1, this));
		this.getActionMap().put("LEFT", new MoveAction("LEFT", 1, this));
		this.getActionMap().put("RIGHT", new MoveAction("RIGHT", 1, this));
		this.getActionMap().put("ROTATE", new MoveAction("ROTATE", 1, this));
		this.getActionMap().put("RADAR", new MoveAction("RADAR", 1, this));
	}


	public void radarGrowth()
	{
		radar.setFrame(radar.getX()-5, radar.getY()-5, radar.getWidth()+10, radar.getHeight()+10);
		if (radar.getWidth() > Constants.panelWidth + 100 && radar.getWidth() > Constants.panelHeight + 100)
		{
			radar.setFrame(0, 0, 0, 0);
			growing = false;
		}
	}


}

@SuppressWarnings("serial")
class MoveAction extends AbstractAction {
	private String direction;
	private int player;
	private GamePanel gp;

	MoveAction(String direction, int player, GamePanel gp) {

		this.direction = direction;
		this.player = player;
		this.gp = gp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int xt = gp.actors.get(0).getX();
		int yt = gp.actors.get(0).getY();
		gp.altered = true;
		if (direction.equals("UP") && !((Tank)gp.actors.get(0)).noUp) {
			gp.actors.get(0).y = (yt - Constants.tankSpeed);
			if (((Tank)gp.actors.get(0)).getHor())
				((Tank)gp.actors.get(0)).horz();
		}
		if (direction.equals("DOWN") && !((Tank)gp.actors.get(0)).noDown) {
			gp.actors.get(0).setY(yt + Constants.tankSpeed);
			if (((Tank)gp.actors.get(0)).getHor())
				((Tank)gp.actors.get(0)).horz();
		}
		if (direction.equals("LEFT") && !((Tank)gp.actors.get(0)).noLeft) {
			gp.actors.get(0).setX(xt - Constants.tankSpeed);
			if (!((Tank)gp.actors.get(0)).getHor())
				((Tank)gp.actors.get(0)).horz();
		}
		if (direction.equals("RIGHT") && !((Tank)gp.actors.get(0)).noRight) {
			gp.actors.get(0).setX(xt + Constants.tankSpeed);
			if (!((Tank)gp.actors.get(0)).getHor())
				((Tank)gp.actors.get(0)).horz();
		}
		if (direction.equals("ROTATE"))
			gp.actors.get(0).angle = (gp.actors.get(0).angle + 0.1);
		if (direction.equals("RADAR"))
			gp.callRadar();

		gp.altered = false;
		
	}
}
