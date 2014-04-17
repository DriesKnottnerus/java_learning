/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dansen;

/**
 *
 * @author dknottne
 */
public enum Sex {
    HEER(50), DAME(40);
    private final int voetLengte;
    
    //Constructor
    Sex(int voetLengte) {
        this.voetLengte = voetLengte;
    }

    public int getVoetLengte() {
        return voetLengte;
    }
    
}
