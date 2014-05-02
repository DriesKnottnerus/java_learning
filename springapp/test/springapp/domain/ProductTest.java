/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package springapp.domain;

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
public class ProductTest {
    
    public ProductTest() {
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
     * Test of setDescription and getDescription method, of class Product.
     */
    @Test
    public void testDescription() {
        System.out.println("getDescription");
        Product instance = new Product();
        String expResult = "productdescription";
        instance.setDescription("productdescription");
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    

    /**
     * Test of setPrice and getPrice method, of class Product.
     */
    @Test
    public void testPrice() {
        System.out.println("getPrice");
        Product instance = new Product();
        Double expResult = 8.556d;
        instance.setPrice(8.556d);
        Double result = instance.getPrice();
        assertEquals(expResult, result);
    }

    

    /**
     * Test of toString method, of class Product.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Product instance = new Product();
        instance.setDescription("productdescription");
        instance.setPrice(8.556d);
        String expResult = "Description: productdescription;Price: 8.556";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }
    
}
