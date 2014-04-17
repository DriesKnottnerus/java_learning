/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dansen;

import java.awt.Image;

/**
 * Interface to obtain the four images of the left and right feet of the gentleman and the lady.
 * @author dknottne
 */
public interface DansImageProvider {

    public Image getHeerLinks();

    public Image getHeerRechts();

    public Image getDameLinks();

    public Image getDameRechts();
}
