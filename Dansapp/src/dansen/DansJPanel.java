/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dansen;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dknottne
 */
public class DansJPanel extends javax.swing.JPanel {
    
    private List<Voet> voeten;
    private PointD draggingStart;
    private PointD draggingEnd;
    private int currentVoet;

    public DansJPanel() {
        this.voeten = new ArrayList<>();
        this.currentVoet = 0;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // do your painting here...
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
        
        g.setColor(Color.black);
        for (Voet voet : voeten) {
            voet.draw(g);
        }
        if (draggingStart != null && draggingEnd != null) {
            int beginx = (int) Math.rint(draggingStart.getX());
            int beginy = (int) Math.rint(draggingStart.getY());
            int eindx = (int) Math.rint(draggingEnd.getX());
            int eindy = (int) Math.rint(draggingEnd.getY());
            g.drawLine(beginx, beginy, eindx, eindy);
        }
    }

    public List<Voet> getVoeten() {
        return voeten;
    }

    public void setVoeten(List<Voet> voeten) {
        this.voeten = voeten;
    }   

    public PointD getDraggingStart() {
        return draggingStart;
    }

    public void setDraggingStart(PointD draggingStart) {
        this.draggingStart = draggingStart;
    }

    public PointD getDraggingEnd() {
        return draggingEnd;
    }

    public void setDraggingEnd(PointD draggingEnd) {
        this.draggingEnd = draggingEnd;
    }
    
    public void addVoetToList(Voet voet) {
        voeten.add(voet);
    }
    
    /**
     * make all feet visible
     */
    public void setAllVisible() {
        for (Voet voet : voeten) {
            voet.setVisible(true);
        }
    }
    
    /**
     * make all feet invisible
     */
    public void setAllInvisible() {
        for (Voet voet : voeten) {
            voet.setVisible(false);
        }
    }
    
    /**
     * loops over the list of feet, sets the next one visible.
     */
    public void goToNextFoot() {
        voeten.get(currentVoet).setVisible(false);
        currentVoet++;
        if (currentVoet >= voeten.size()) {
            currentVoet = 0;
        }
        voeten.get(currentVoet).setVisible(true);
    }
    
    /** 
     * Before animation starts, the position must be on the last foot; 
     * the first foot shown will then be the first foot.
     */
    public void positionOnLastFoot() {
        currentVoet = voeten.size() - 1;
    }
    
    /**
     * 
     * @return a reference to the current Voet
     */
    public Voet getCurrentVoet() {
        return voeten.get(currentVoet);
    }
}
