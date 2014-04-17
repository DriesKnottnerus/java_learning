/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baltesten;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class BalJPanel extends JPanel implements MouseListener,
        MouseMotionListener, ComponentListener, BalTester {

    private static final long serialVersionUID = 1L;

    Color balKleur;

    PointD startpos, snelheid, startSnelheid, begin, eind;
    private int ballsize;
    Dimension winSize;
    List<Bal> balls;
    List<Muur> muren;
    boolean botsing_gevonden;
    private boolean biljarten;
    Bal stootBal;
    private boolean muurOpzetten;

    //Constructor
    BalJPanel() {
        setOpaque(true);
        setBackground(Color.GREEN);
        //	setSize(600, 400);
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        winSize = getSize();
        
        startSnelheid = new PointD(4, 3);
        snelheid = new PointD(0, 0);
        ballsize = 20;

        balls = new ArrayList<>();
        muren = new ArrayList<>();
        balKleur = new Color(255, 0, 0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, winSize.width, winSize.height);

        for (Bal b : balls) {
            b.draw(g);
        }

        g.setColor(Color.BLACK);
        for (Muur m : muren) {
            m.draw(g);
        }
        if (muurOpzetten == false) {
            g.setColor(balKleur);
        }
        if (begin != null && eind != null) {
            int beginx = (int) Math.rint(begin.getX());
            int beginy = (int) Math.rint(begin.getY());
            int eindx = (int) Math.rint(eind.getX());
            int eindy = (int) Math.rint(eind.getY());
            g.drawLine(beginx, beginy, eindx, eindy);
        }
    }

    public void stop() {
        balls.clear();
        muren.clear();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (biljarten == false) {
            begin = new PointD(e.getX(), e.getY());
        }
        if (biljarten == true) {
            begin = null;
            for (Bal b : balls) {
                PointD p = new PointD(e.getX(), e.getY());
                if (p.distance(b.getPos()) < b.getSize()) {
                    b.setLastBounced(null);
                    stootBal = b;
                    begin = b.getPos();
                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (begin != null) {
            eind = new PointD(e.getX(), e.getY());
            if (biljarten == false) {
                if (muurOpzetten == true) {
                    zetMuurOp(begin, eind);
                } else {
                    startBal(begin, snelheid);
                }
            }
            if (biljarten == true) {
                stootBal.setSnelheid(snelheid);
            }
        }
        begin = null;
        eind = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (begin != null) {
            eind = new PointD(e.getX(), e.getY());
            snelheid = new PointD(
                    (eind.getX() - begin.getX()) / 10,
                    (eind.getY() - begin.getY()) / 10);
            repaint();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        winSize = getSize();
        for (Bal b : balls) {
            b.setRange(0, winSize.width - 1, 0, winSize.height - 1);
        }
    }

    private void startBal(PointD startpos, PointD snelheid) {
        Bal bal = new Bal(startpos, snelheid, ballsize, balKleur);
        balls.add(bal);
        bal.setRange(0, winSize.width - 1, 0, winSize.height - 1);
    }

    /**
     * @return the balls
     */
    @Override
    public List<Bal> getBalls() {
        return balls;
    }

    void moveBalls() {
        for (Bal b : balls) {
            b.move();
        }
        verwerk_muren();
        //		botsing_gevonden = true;
        //		while (botsing_gevonden) {
        //			botsing_gevonden = false;
        verwerk_botsingen();
        //		}

        if (balls.size() > 0) {
            repaint();
        }

    }

    /**
     * Botsen tussen ballen verwerken. Dat moet hier en niet in BatAWT, want met
     * een botsing zijn twee ballen betrokken, en de gevolgen moeten
     * tegelijkertijd voor beide ballen afgehandeld worden.
     */
    private void verwerk_botsingen() {
        for (Bal b1 : balls) {
            for (Bal b2 : balls) {
                if (balls.indexOf(b1) < balls.indexOf(b2)) {
                    if (b1.getLastBounced() != b2 || b2.getLastBounced() != b1) {
                        if (b1.getPos().distance(b2.getPos())
                                < 0.5d * (b1.getSize() + b2.getSize()) + 1) {
                            PointD pXY = getXY(b1.getPos(), b2.getPos());
                            PointD b1_s = getPointDtovXY(b1.getSnelheid(), pXY); //snelheid tov XY
                            PointD b2_s = getPointDtovXY(b2.getSnelheid(), pXY);
                            double ff = b2_s.getX(); //verwissel x-componenten
                            b2_s.setX(b1_s.getX());
                            b1_s.setX(ff);
                            //terugrekenen:
                            pXY = new PointD(pXY.getX(), -pXY.getY());
                            b1.setSnelheid(getPointDtovXY(b1_s, pXY));
                            b2.setSnelheid(getPointDtovXY(b2_s, pXY));
                            b1.setLastBounced(b2);
                            b2.setLastBounced(b1);
                            botsing_gevonden = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Het verwerken van botsingen van ballen tegen muren.
     */
    private void verwerk_muren() {
        for (Bal b : balls) {
            for (Muur m : muren) {
                if (m != b.getLastBounced()) {
                    PointD pXY = getXY(m.getNulPunt(), m.getEindPunt());
                    PointD bXY = getPointDtovXY(b.getPos(), pXY);
                    PointD nulXY = getPointDtovXY(m.getNulPunt(), pXY);
                    PointD eindXY = getPointDtovXY(m.getEindPunt(), pXY);
                    if (Math.abs(bXY.getY() - nulXY.getY()) < 0.5d * b.getSize()
                            && bXY.getX() > nulXY.getX() - 0.5d * b.getSize()
                            && bXY.getX() < eindXY.getX() + 0.5d * b.getSize()) {
                        //			System.out.println("/nbotsing met " + m.toString());
                        //			System.out.println(b.toString()); 
                        PointD b_s = getPointDtovXY(b.getSnelheid(), pXY);
                        b_s.setY(-b_s.getY());
                        //terugrekenen:
                        pXY = new PointD(pXY.getX(), -pXY.getY());
                        b.setSnelheid(getPointDtovXY(b_s, pXY));
                        b.setLastBounced(m);
                        //			System.out.println(b.toString()); 
                    }
                }
            }
        }
    }

    /**
     * Omrekenen van een punt naar de coordinaten aangegeven door (X,Y): de
     * nieuwe x-as ligt in de richting (X, Y).
     */
    PointD getPointDtovXY(PointD p, PointD pXY) {
        if (pXY.getY() == 0) {
            return new PointD(p);
        }
        if (pXY.getX() == 0) {
            if (pXY.getY() > 0) {
                return new PointD(p.getY(), -p.getX());
            } else {
                return new PointD(-p.getY(), p.getX());
            }
        }
        double wrtl = Math.sqrt(pXY.getX() * pXY.getX() + pXY.getY() * pXY.getY());
        return new PointD((p.getX() * pXY.getX() + p.getY() * pXY.getY())
                / wrtl, (p.getY() * pXY.getX() - p.getX() * pXY.getY()) / wrtl);
    }

    /**
     *
     * @param a
     * @param b
     * @return PointD dat de richting van a naar b representeert. X >= 0.
     */
    PointD getXY(PointD a, PointD b) {
        if (a.getX() == b.getX()) {
            return new PointD(0, 1);
        }
        if (a.getY() == b.getY()) {
            return new PointD(1, 0);
        }
        if (a.getX() > b.getX()) {
            return new PointD(a.getX() - b.getX(), a.getY() - b.getY());
        }
        return new PointD(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public void setBalKleur(Color balKleur) {
        this.balKleur = balKleur;
    }

    public void setBiljarten(boolean biljarten) {
        this.biljarten = biljarten;
    }

    public void setMuurOpzetten(boolean muurOpzetten) {
        this.muurOpzetten = muurOpzetten;

    }

    public int getBallsize() {
        return ballsize;
    }

    public void setBallsize(int ballsize) {
        this.ballsize = ballsize;
    }

    private void zetMuurOp(PointD begin, PointD eind) {
        Muur m = new Muur(begin, eind);
        /*
         PointD pXY = getXY(m.getNulPunt(), m.getEindPunt());
         PointD nulXY = getPointDtovXY(m.getNulPunt(),pXY); 
         PointD eindXY = getPointDtovXY(m.getEindPunt(),pXY); 
         PointD p = getPointDtovXY(new PointD(50,60), pXY);
         System.out.println("muur:\n" + m.toString());
         System.out.println("pXY = " + pXY.toString());
         System.out.println("nulpunt tov XY = " + nulXY.toString());
         System.out.println("eindpunt tov XY = " + eindXY.toString());
         System.out.println("(50,60) tov XY = " + p.toString());
         */
        muren.add(m);
        repaint();
    }
    
    public void zetWrijving(boolean wrijving) {
        for (Bal bal : balls) {
            bal.setWrijving(wrijving);
        }
    }
    
}
