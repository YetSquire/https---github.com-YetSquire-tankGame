import java.awt.Graphics;

public class Enemy extends Actor{
    private int radius;
    private int speed;
    private double endTime;
    public Enemy(int r, int h, int x, int y, int s, double rA)
    {
        super(x, y, rA, h);
        radius = r;
        speed = s;
    }

    public void update()
    {
        if (hp < 0) gone = true;
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
        g.fillRect(x, y, radius, radius);
    }

    public double getRadius()
    {
        return radius;
    }
}
