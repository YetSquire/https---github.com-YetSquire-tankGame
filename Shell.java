
import java.awt.Graphics;

public class Shell extends Actor{
	public Shell(double x, double y, double angle, int hp)
	{
		super(x, y, angle, hp);
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
		g.fillOval(x, y, Constants.shellSize, Constants.shellSize);
	}

	public double getRadius()
	{
		return Constants.shellSize;
	}
}
