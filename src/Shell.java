package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Shell extends Actor{
	public Shell(int x, int y, double angle, int hp, int speed)
	{
		super(x, y, angle, hp, speed);
		Ellipse2D.Double ci = new Ellipse2D.Double(x, y, Constants.shellSize, Constants.shellSize);
		p = new Area(ci);
	}
	
	public void update()
	{
		if (hp <= 0) gone = true;
		if (x >= Constants.panelWidth)
			gone = true;
		if (x <= 0)
			gone = true;
		if (y >= Constants.panelHeight)
			gone = true;
		if (y <= 0)
			gone = true;
		endTime = System.currentTimeMillis();
		if (!gone && endTime-startTime > 50)
		{
			x -= speed*Math.cos(angle);
			y -= speed*Math.sin(angle);
			startTime = System.currentTimeMillis();
		}
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.black);
		Ellipse2D.Double ci = new Ellipse2D.Double(x, y, Constants.shellSize, Constants.shellSize);
		p = new Area(ci);
		g.fillOval(x, y, Constants.shellSize, Constants.shellSize);
	}

	public double getRadius()
	{
		return Constants.shellSize;
	}

	public int getOGHP()
	{
		return hp;
	}
}
