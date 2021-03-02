/*
 * MyHok.java
 *
 * Created on 10 december 2005, 14:27
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package berekenen;
import java.awt.*;
import java.awt.Graphics;


/**
 *
 * @author Dries
 */
public class MyHok extends Container {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2391839353181966242L;
	String str = new String();
    boolean streep;
    Font font;
    Dimension size;
        /** Creates a new instance of MyHok */
    public MyHok(String string2) {
        setSize(getPreferredSize());
        size = getSize();
        str = string2;
        font = new Font("Courier", Font.PLAIN, (int)(size.height * 0.8));
    }
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }
    public void paint (Graphics g) {
        if (streep) {
            int i = size.height/10;
            g.fillRect(1, 4*i, size.width,i);
        }
        else {
            g.setFont(font);
            g.drawString(str,size.width/2, size.height/2);
        }
            
    }
    public void leeg() {
        streep = false;
        str =  " ";
        repaint();
    }
    public void upd(String string3) {
        streep = false;
        str = string3;
        repaint();
    }
    public void streep() {
        streep = true;
        repaint();
    }
}
