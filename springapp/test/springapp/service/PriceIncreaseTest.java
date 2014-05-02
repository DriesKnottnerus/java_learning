/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package springapp.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dknottne
 */
public class PriceIncreaseTest {
    
    public PriceIncreaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    /**
     * Test of getPercentage method, of class PriceIncrease.
     */
    @Test
    public void testPercentage() {
        System.out.println("testPercentage");
        PriceIncrease instance = new PriceIncrease();
        int expResult = 23;
        instance.setPercentage(expResult);
        int result = instance.getPercentage();
        assertEquals(expResult, result);
        
    }
    
}
