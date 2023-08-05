package src;
import java.awt.*;
import java.awt.geom.Area;

public abstract class Actor {
    protected Area p;
    protected int x;
	protected int y;
	protected double angle;
	protected int speed;
	public boolean gone;
	protected double startTime;
	protected double endTime;
    public int hp;
    public int ogHP;
    protected boolean friendly;

    public Actor(int x, int y, double angle, int hp, int speed)
    {
        gone = false;
		this.x = x;
		this.y = y;
        this.hp = hp;
		this.angle = angle;
		this.speed = speed;
		startTime = System.currentTimeMillis();
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public boolean intersects(Area a)
    {
        Area q = new Area(p);
        q.intersect(a);
        if (q.isEmpty()) return false;
        else return true;
    }

    public Area getArea()
    {
        return p;
    }

    public boolean getFriendly()
    {
        return friendly;
    }

    public abstract int getOGHP();
}
