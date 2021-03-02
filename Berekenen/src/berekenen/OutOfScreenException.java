/*
 * OutOfScreenException.java
 *
 * Created on 10 december 2005, 14:15
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package berekenen;

/**
 *
 * @author Dries
 */
public class OutOfScreenException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5074574801821491421L;

	/** Creates a new instance of OutOfScreenException */
    public OutOfScreenException(String str) {
        super(str);
    }  
}
