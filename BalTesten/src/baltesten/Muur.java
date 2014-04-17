package baltesten;

import java.awt.Graphics;
import java.awt.geom.Line2D;

public class Muur implements Bounceable {
	private final Line2D.Double lijn;
	private  int size;
	
	public Muur(Line2D.Double lijn) {
		this.lijn = new Line2D.Double(lijn.getX1(), lijn.getY1(),
				lijn.getX2(), lijn.getY2());
	}
	public Muur(PointD begin, PointD eind) {
		this.lijn = new Line2D.Double(begin.getX(), begin.getY(),
				eind.getX()	, eind.getY());
	}
	public PointD getNulPunt() {
		PointD punt1 = new PointD(lijn.getX1(), lijn.getY1());
		PointD punt2 = new PointD(lijn.getX2(), lijn.getY2());
		if (punt1.compareTo(punt2) < 0)
			return punt1;
		if (punt1.compareTo(punt2) > 0)
			return punt2;
		return punt1; 
	}
	public PointD getEindPunt() {
		PointD punt1 = new PointD(lijn.getX1(), lijn.getY1());
		PointD punt2 = new PointD(lijn.getX2(), lijn.getY2());
		if (punt1.compareTo(punt2) < 0)
			return punt2;
		if (punt1.compareTo(punt2) > 0)
			return punt1;
		return punt2; 
	}
	@Override public boolean equals (Object o) {return lijn.equals(o);}  
	@Override public int hashCode() {return lijn.hashCode();}
	@Override public String toString() {
		return "muur van " + getNulPunt().toString() + " naar " +
		getEindPunt().toString();}
	public void draw(Graphics g) {
		int beginx = (int) Math.rint(lijn.getX1());
		int beginy = (int) Math.rint(lijn.getY1());
		int eindx = (int) Math.rint(lijn.getX2());
		int eindy = (int) Math.rint(lijn.getY2());
		g.drawLine(beginx, beginy, eindx, eindy);
	}
	public int getSize() {
		return size;
	}
}
