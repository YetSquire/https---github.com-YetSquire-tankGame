package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.*;

public class Bomb extends Shell{
    
    public Bomb(int x, int y, double angle, int hp, int speed)
	{
		super(x, y, angle, hp, speed);
        friendly = true;
		Ellipse2D.Double ci = new Ellipse2D.Double(x, y, Constants.bombSize, Constants.bombSize);
		p = new Area(ci);
	}

    public void update()
    {
        if (hp <= 0)
        {
            gone = true;
            GamePanel.explosion(x, y);
        }
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
        Ellipse2D.Double ci = new Ellipse2D.Double(x, y, Constants.bombSize, Constants.bombSize);
        p = new Area(ci);
        g.fillOval(x, y, Constants.bombSize, Constants.bombSize);
    }
}
