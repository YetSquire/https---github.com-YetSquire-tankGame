package src;
import java.awt.Graphics;
import java.awt.geom.Area;

public abstract class Enemy extends Actor{
    protected int ogHP;
    protected int length;
    protected double endTime;
    public Enemy(int l, int h, int x, int y, int s, double rA)
    {
        super(x, y, rA, h, s);
        ogHP = h;
        length = l;
        speed = s;
    }

    public void update()
    {
        if (hp < 0) 
        {
            gone = true;
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

    public abstract void draw(Graphics g);

    public int getLength()
    {
        return length;
    }

    public int getOGHP()
    {
        return ogHP;
    }

}
