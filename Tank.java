import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

public class Tank {
	// going to make tanks, turn off static and pass tank object to moveaction
	private double x;
	private double y;
	private double cenX;
	private double cenY;
	private int CircleR;
	private boolean horizontal;
	private double rotationAngle;
	public boolean noLeft;
	public boolean noRight;
	public boolean noUp;
	public boolean noDown;
	private int hp;

    public Tank()
    {
        x = 10;
        y = 10;
        CircleR = 30;
        horizontal = true;
        rotationAngle = Math.toRadians(45);
		hp = 400;
    }
    public void horz()
	{
		horizontal = !horizontal;
		int holder = Constants.tankHeight;
		Constants.tankHeight = Constants.tankWidth;
		Constants.tankWidth = holder;
		if (horizontal)
		{
			x-=Constants.tankWidth/4;
			y+=Constants.tankHeight/4;
		}
		else
		{
			x+=Constants.tankWidth/4;
			y-=Constants.tankHeight/4;
		}
	}

    public void update()
    {
        cenX = x + Constants.tankWidth/2;
		cenY = y + Constants.tankHeight/2;
		Point loc = MouseInfo.getPointerInfo().getLocation();
		int endx = (int)cenX;
		int endy = (int)cenY;
		noUp = false;
		noDown = false;
		noLeft = false;
		noRight = false;
		rotationAngle = Math.atan2(endy-loc.y, endx-loc.x) -0.05;
		if (cenX >= Constants.panelWidth)
		{
			noRight = true;
			x = Constants.panelWidth - Constants.tankWidth/2 -1;
		}
		if (cenX <= 0)
		{
			noLeft = true;
			x = 1 - Constants.tankWidth/2;
		}
		if (cenY >= Constants.panelHeight)
		{
			noDown = true;
			y = Constants.panelHeight - Constants.tankHeight/2 - 1;
		}
		if (cenY <= 0)
		{
			noUp = true;
			y = 1 - Constants.tankHeight/2;
		}
    }

    public void draw(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.black);
		g.drawRect((int) x, (int) y, Constants.tankWidth, Constants.tankHeight);
		g.fillOval((int) x + Constants.tankWidth / 2 - CircleR / 2, (int) y + Constants.tankHeight / 2 - CircleR / 2, CircleR,
				CircleR);

		int cx = (int)cenX;
		int cy = (int)cenY;
		int x1 = (int) cx - 100;
		int y1 = (int) y + Constants.tankHeight / 2 - 5;
		Point center = new Point(cx, cy);
		Point one = rotate(new Point(x1, y1 + 5), center);
		Point two = rotate(new Point(x1+1, y1 - 5), center);
		Point three = rotate(new Point(cx, cy - 5), center);
		Point four = rotate(new Point(cx, cy + 5), center);
		g.drawOval(cx, cy, 2, 2);
		int[] xarr = { one.x, two.x, three.x, four.x };
		int[] yarr = { one.y, two.y, three.y, four.y };
		g.fillPolygon(xarr, yarr, 4);
    }

    public Point rotate(Point p, Point c) {
		int x1 = p.x;
		int y1 = p.y;
		int cx = c.x;
		int cy = c.y;
		int dx = x1 - cx;
		int dy = y1 - cy;
		x1 = cx + (int) (dx * Math.cos(rotationAngle) - dy * Math.sin(rotationAngle));
		y1 = cy + (int) (dx * Math.sin(rotationAngle) + dy * Math.cos(rotationAngle));
		return new Point(x1, y1);

	}

    public boolean getHor()
    {
        return horizontal;
    }

    public void setHor(boolean b)
    {
        horizontal = b;
    }

    public double getCX()
    {
        return cenX;
    }
    public double getCY()
    {
        return cenY;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public void setX(double x)
    {
        this.x = x;
    }
    public void setY(double y)
    {
        this.y = y;
    }
    public double getAngle()
    {
        return rotationAngle;
    }
    public void setAngle(double Angle)
    {
        this.rotationAngle = Angle;
    }
	public int getHP()
    {
        return hp;
    }
    public void setHP(int h)
    {
        this.hp = h;
    }
}
