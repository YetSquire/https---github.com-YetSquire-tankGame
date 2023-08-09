package src;

import java.awt.*;
import java.awt.geom.*;

public class Circle extends Enemy{
    public Circle(int l, int h, int x, int y, int s, double rA)
    {
        super(l, h, x, y, s, rA);
        friendly = false;
        p = new Area(new Ellipse2D.Double(x-length/2, y-length/2, length, length));
    }

    public void draw(Graphics g)
    {
        double hold = ((double)hp)/ogHP;
        int a = (int)(255-255*hold + 40);
        if (a > 255) a = 255;
        g.setColor(new Color(100, 100, 100, a));
        g.fillOval(x-length/2, y-length/2, length, length);
        p = new Area(new Ellipse2D.Double(x-length/2, y-length/2, length, length));
        
    }
}
