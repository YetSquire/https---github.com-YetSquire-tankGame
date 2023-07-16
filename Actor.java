import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class Actor {
    protected int x;
	protected int y;
	protected double angle;
	protected double speed;
	public boolean gone;
	protected double startTime;
	protected double endTime;
    protected int hp;

    public Actor(double x, double y, double angle, int hp)
    {
        gone = false;
		this.x = (int)x;
		this.y = (int)y;
        hp = this.hp;
		this.angle = angle;
		speed = Constants.shellSpeed;
		startTime = System.currentTimeMillis();
    }

    public void update()
    {

    }

    public void draw(Graphics g)
    {

    }

    public boolean intersects(Actor a)
    {
        Ellipse2D.Double th = new Ellipse2D.Double(x, y, Constants.shellSize, Constants.shellSize);
        Rectangle a2 = new Rectangle((int)a.getX(), (int)a.getY(), (int)a.getRadius(), (int)a.getRadius());
        if (th.intersects(a2)){
            return true;
        }
        
        return false;
    }

    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public abstract double getRadius();
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
}
