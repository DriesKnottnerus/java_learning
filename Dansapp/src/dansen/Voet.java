/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dansen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author dknottne
 */
public class Voet {

    private PointD richting;
    private PointD positie;

    private Sex sex;
    private SoortVoet soortVoet;
    DansImageProvider dansImageProvider;
    private boolean visible;
    
    /** 
     * the numbers of counts in the beat before the next step, default is 1.
     * It can be 0.5 for a quick. (turning-lock in waltz).
     */ 
    private float nextStepDelay; 
    

    // Constructor
    public Voet(DansImageProvider dansImageProvider) {
        this.dansImageProvider = dansImageProvider;
        this.nextStepDelay = 1.0f;
    }

    /**
     * Teken de voet, beginnend op <code>positie</code>, in de richting van
     * <code>richting</code>, maar met vaste <code>lengte</code>.
     *
     * @param g
     */
    public void draw(Graphics g) {
        if (visible && positie != null && richting != null) {
            int beginx = (int) Math.rint(positie.getX());
            int beginy = (int) Math.rint(positie.getY());

            Image img;
            if (this.sex == Sex.HEER) {
                if (this.soortVoet == SoortVoet.LINKS) {
                    img = dansImageProvider.getHeerLinks();
                } else {
                    img = dansImageProvider.getHeerRechts();
                }
            } else {
                if (this.soortVoet == SoortVoet.LINKS) {
                    img = dansImageProvider.getDameLinks();
                } else {
                    img = dansImageProvider.getDameRechts();
                }
            }
            // create the transform, note that the transformations happen
            // in reversed order (so check them backwards)
            AffineTransform at = new AffineTransform();
            // 4. translate it to the startpoint
            at.translate(beginx, beginy);

            // 3. do the actual rotation
            at.rotate(-1 * richting.getY(), richting.getX());

            // 2. just a scale because this image is big
            at.scale(0.5, 0.5);

            // 1. translate the object so that you rotate it around the 
            //    center (easier :))
            at.translate(-img.getWidth(null) / 2, -img.getHeight(null) / 2);

            // draw the image
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(img, at, null);

        }
    }

    public PointD getRichting() {
        return richting;
    }

    public void setRichting(PointD richting) {
        this.richting = richting;
    }

    public PointD getPositie() {
        return positie;
    }

    public void setPositie(PointD positie) {
        this.positie = positie;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public SoortVoet getSoortVoet() {
        return soortVoet;
    }

    public void setSoortVoet(SoortVoet soortVoet) {
        this.soortVoet = soortVoet;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getNextStepDelay() {
        return nextStepDelay;
    }

    public void setNextStepDelay(float nextStepDelay) {
        this.nextStepDelay = nextStepDelay;
    }

    
    
    @Override
    public String toString() {
        return "Voet{" + "richting=" + richting + ", positie=" + positie + ", sex=" + sex + ", soortVoet=" + soortVoet + '}';
    }
    
    
}
