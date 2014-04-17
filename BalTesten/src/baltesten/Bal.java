package baltesten;

import java.awt.*;

/**
 * De bal kent de grootte van het veld, en stuitert op de rand. 
 *
 * @author Dries
 * 
*/
public class Bal implements Bounceable {

    private PointD pos, snelheid;

    private int size;
    private int xrangemin, xrangemax, yrangemin, yrangemax;
    private boolean wrijving;

    private Bounceable lastBounced;

    Color balKleur;

    public Bal(PointD ps, PointD sn, int s, Color balKleur) {
        pos = new PointD(ps.getX(), ps.getY());
        snelheid = new PointD(sn.getX(), sn.getY());
        size = s;

        lastBounced = null;

        this.balKleur = balKleur;
    }
    
    /**
     * @return the pos
     */
    public PointD getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(PointD pos) {
        this.pos = pos;
    }

    /**
     * @return the lastBounced
     */
    public Bounceable getLastBounced() {
        return lastBounced;
    }

    /**
     * @param lastBounced the lastBounced to set
     */
    public void setLastBounced(Bounceable lastBounced) {
        this.lastBounced = lastBounced;
    }

    /**
     * @return the snelheid
     */
    public PointD getSnelheid() {
        return snelheid;
    }

    /**
     * @param snelheid the snelheid to set
     */
    public void setSnelheid(PointD snelheid) {
        this.snelheid = snelheid;
    }

    /**
     * De bal laten bewegen, aanpassen van positie (pos) en snelheid
     *
     */
    public void move() {
        /**
         * "zwaartekracht" laten werken:
         */
              //	snelheid.setY(snelheid.getY() + 1.0d);

        /**
         * "wrijving" toepassen
         */
        if (wrijving) {
            snelheid.setX(snelheid.getX() * 0.99d);
            if (Math.abs(snelheid.getX()) < 0.2) {
                snelheid.setX(0.0d);
            }
            snelheid.setY(snelheid.getY() * 0.99d);
            if (Math.abs(snelheid.getY()) < 0.2) {
                snelheid.setY(0.0d);
            }
        }
        
        pos.setLocation(pos.getX() + snelheid.getX(), pos.getY() + snelheid.getY());
        /**
         * stuiteren op de rand:
         */
        if (pos.getX() < xrangemin) {
            lastBounced = null;
            pos.setX(xrangemin);
            snelheid.setX(-snelheid.getX());
        }
        if (pos.getX() > xrangemax) {
            lastBounced = null;
            pos.setX(xrangemax);
            snelheid.setX(-snelheid.getX());
        }
        if (pos.getY() < yrangemin) {
            lastBounced = null;
            pos.setY(yrangemin);
            snelheid.setY(-snelheid.getY());

        }
        if (pos.getY() > yrangemax) {
            lastBounced = null;
            pos.setY(yrangemax);
            snelheid.setY(-snelheid.getY());
        }

    }

    public void setRange(int mnx, int mxx, int mny, int mxy) {
        xrangemin = mnx + size / 2;
        xrangemax = mxx - size / 2;
        yrangemin = mny + size / 2;
        yrangemax = mxy - size / 2;
    }

    public void draw(Graphics g) {
        int posx = (int) Math.rint(pos.getX());
        int posy = (int) Math.rint(pos.getY());
        g.setColor(balKleur);
        g.fillOval(posx - size / 2, posy - size / 2, size, size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isWrijving() {
        return wrijving;
    }

    public void setWrijving(boolean wrijving) {
        this.wrijving = wrijving;
    }
    
    
    @Override
    public String toString() {
        return "bal op " + pos.toString() + "\n"
                + "size: " + size + ", snelheid: " + snelheid.toString();
    }
}
