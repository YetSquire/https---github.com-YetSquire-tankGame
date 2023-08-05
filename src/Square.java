package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Square extends Enemy {
    public Square(int l, int h, int x, int y, int s, double rA)
    {
        super(l, h, x, y, s, rA);
        friendly = false;
        Point a = new Point(x - length/2, y - length/2);
        Point b = new Point(x - length/2, y + length/2);
        Point c = new Point(x + length/2, y + length/2);
        Point d = new Point(x + length/2, y - length/2);
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX()), (int)(d.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY()), (int)(d.getY())};

        Polygon thisP = new Polygon(xArr, yArr, 4);
        p = new Area(thisP);
    }

    public void draw(Graphics g)
    {
        double hold = ((double)hp)/ogHP;
        int tint = (int)(255-255*hold + 40);
        if (tint > 255) tint = 255;
        g.setColor(new Color(100, 100, 100, tint));

        Point a = new Point(x - length/2, y - length/2);
        Point b = new Point(x - length/2, y + length/2);
        Point c = new Point(x + length/2, y + length/2);
        Point d = new Point(x + length/2, y - length/2);
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX()), (int)(d.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY()), (int)(d.getY())};

        Polygon thisP = new Polygon(xArr, yArr, 4);
        p = new Area(thisP);
        g.fillPolygon(thisP);
    }
}
