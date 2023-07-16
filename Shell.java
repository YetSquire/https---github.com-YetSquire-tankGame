
import java.awt.Graphics;

public class Shell extends Actor{
	public Shell(double x, double y, double angle)
	{
		super(x, y, angle);
	}
	
	public void update()
	{
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
			//System.out.println(x);
			x -= speed*Math.cos(angle);
			y -= speed*Math.sin(angle);
			startTime = System.currentTimeMillis();
		}
		
	}
	
	public void draw(Graphics g)
	{
		g.fillOval(x, y, Constants.shellSize, Constants.shellSize);
	}

	public int getRadius()
	{
		return Constants.shellSize;
	}
}
