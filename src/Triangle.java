package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;


public class Triangle extends Enemy{

    public Triangle(int l, int h, int x, int y, int s, double rA)
    {
        super(l, h, x, y, s, rA);
        friendly = false;
        Point a = new Point(x, y - length);
        Point b = new Point((int)(x-(length*Math.sqrt(3)/2)), (int)(y+(length*1/2)));
        Point c = new Point((int)(x+(length*Math.sqrt(3)/2)), (int)(y+(length*1/2)));
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY())};
        Polygon thisP = new Polygon(xArr, yArr, 3);
        p = new Area(thisP);
    }

    public void draw(Graphics g)
    {
        double hold = ((double)hp)/ogHP;
        int tint = (int)(255-255*hold + 40);
        if (tint > 255) tint = 255;
        g.setColor(new Color(100, 100, 100, tint));
        Point a = new Point(x, y - length);
        Point b = new Point((int)(x-(length*Math.sqrt(3)/2)), (int)(y+(length*1/2)));
        Point c = new Point((int)(x+(length*Math.sqrt(3)/2)), (int)(y+(length*1/2)));
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY())};
        Polygon thisP = new Polygon(xArr, yArr, 3);
        p = new Area(thisP);
        g.fillPolygon(thisP);
    }
}
