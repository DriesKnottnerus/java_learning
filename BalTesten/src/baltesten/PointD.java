package baltesten;

import java.awt.*;
import java.awt.geom.*;

public class PointD implements Comparable<PointD> {

    private static final long serialVersionUID = 1L;
    private final Point2D.Double p;

    public PointD(Point2D.Double p) {
        this.p = new Point2D.Double(p.getX(), p.getY());
    }

    public PointD(Point p) {
        this.p = new Point2D.Double((double) p.x, (double) p.y);
    }

    public PointD(PointD p) {
        this.p = new Point2D.Double(p.getX(), p.getY());
    }

    public PointD(double x, double y) {
        this.p = new Point2D.Double(x, y);
    }

    public PointD(int x, int y) {
        this.p = new Point2D.Double(x, y);
    }

    public double getX() {
        return p.getX();
    }

    public double getY() {
        return p.getY();
    }

    public void setLocation(double x, double y) {
        p.setLocation(x, y);
    }

    public double distance(double x, double y) {
        return p.distance(x, y);
    }

    public double distance(PointD p1) {
        return distance(p1.getX(), p1.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PointD)) 
            return false;
        PointD q = (PointD) o;
        return p.equals(q.p);
    }

    @Override
    public int hashCode() {
        return p.hashCode();
    }

    @Override
    public String toString() {
        return p.toString();
    }

    @Override
    public int compareTo(PointD p) {
        if (this.p.getX() < p.getX()) {
            return -1;
        }
        if (this.p.getX() > p.getX()) {
            return 1;
        }
        if (this.p.getY() < p.getY()) {
            return -1;
        }
        if (this.p.getY() > p.getY()) {
            return 1;
        }
        return 0;

    }

    public void setX(double x) {
        setLocation(x, getY());
    }

    public void setY(double y) {
        setLocation(getX(), y);
    }
}
