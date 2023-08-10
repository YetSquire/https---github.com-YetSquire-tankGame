package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Shield extends Actor{
    int length;
    int width;
    boolean corner;
    Polygon holder;
    Polygon holdCorner;

    public Shield(int x, int y, double angle, int hp, int length, int width)
    {
        super(x, y, angle, hp, -1);
        this.width = width;
        this.length = length;
        ogHP = hp;
        if (Math.random() > 0.5) corner = false;
        else corner = true;
        friendly = true;

        if (corner) isCorner();
        else notCorner();
    }

    public Point rotate(Point p, Point c) {
		int x1 = p.x;
		int y1 = p.y;
		int cx = c.x;
		int cy = c.y;
		int dx = x1 - cx;
		int dy = y1 - cy;
		x1 = cx + (int) (dx * Math.cos(angle) - dy * Math.sin(angle));
		y1 = cy + (int) (dx * Math.sin(angle) + dy * Math.cos(angle));
		return new Point(x1, y1);

	}

    private void isCorner()
    {
        Point center = new Point(x + width/2, y + length/2);
        Point a = new Point(x + width, y);
        Point b = new Point(x + width, y + length);
        Point c = new Point(x, y + length);
        Point d = new Point(x, y);
        a = rotate(a, center);
        b = rotate(b, center);
        c = rotate(c, center);
        d = rotate(d, center);
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX()), (int)(d.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY()), (int)(d.getY())};

        holder = new Polygon(xArr, yArr, 4);
        x = x + width;

        a = new Point(x + length, y);
        b = new Point(x + length, y + width);
        c = new Point(x, y + width);
        d = new Point(x, y);
        a = rotate(a, center);
        b = rotate(b, center);
        c = rotate(c, center);
        d = rotate(d, center);
        int[] x2Arr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX()), (int)(d.getX())};
        int[] y2Arr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY()), (int)(d.getY())};

        holdCorner = new Polygon(x2Arr, y2Arr, 4);
        p = new Area(holder);
        p.add(new Area(holdCorner));
    }

    private void notCorner()
    {
        Point center = new Point(x + width/2, y + length/2);
        Point a = new Point(x + width, y);
        Point b = new Point(x + width, y + length);
        Point c = new Point(x, y + length);
        Point d = new Point(x, y);

        a = rotate(a, center);
        b = rotate(b, center);
        c = rotate(c, center);
        d = rotate(d, center);
        int[] xArr = {(int)(a.getX()), (int)(b.getX()), (int)(c.getX()), (int)(d.getX())};
        int[] yArr = {(int)(a.getY()), (int)(b.getY()), (int)(c.getY()), (int)(d.getY())};

        holder = new Polygon(xArr, yArr, 4);
        holdCorner = holder;
        p = new Area(holder);
    }

    public void update() {
        if (hp <= 0) gone = true;
    }

    public void draw(Graphics g) {
        double hold = ((double)hp)/ogHP;
        int a = (int)(255*hold);
        if (a > 255) a = 255;
        g.setColor(new Color(200, 0, 0, a));
        if (!corner) g.fillPolygon(holder);
        else
        {
            g.fillPolygon(holdCorner);
            g.fillPolygon(holder);
        }
    }

    public int getOGHP() {
        return ogHP;
    }

    
}
