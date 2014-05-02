/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import springapp.domain.Product;

/**
 *
 * @author dknottne
 */
public class SimpleProductManagerTest {

    private SimpleProductManager productManager;
    private List<Product> products;
    private static final int PRODUCT_COUNT = 2;
    private static final Double CHAIR_PRICE = new Double(20.50);
    private static final String CHAIR_DESCRIPTION = "Chair";
    private static final String TABLE_DESCRIPTION = "Table";
    private static final Double TABLE_PRICE = new Double(150.10);
    private static int POSITIVE_PRICE_INCREASE = 10;    

    public SimpleProductManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        productManager = new SimpleProductManager();
        products = new ArrayList<>();
        // stub up a list of products
        Product product = new Product();
        product.setDescription("Chair");
        product.setPrice(CHAIR_PRICE);
        products.add(product);
        product = new Product();
        product.setDescription("Table");
        product.setPrice(TABLE_PRICE);
        products.add(product);
        productManager.setProducts(products);
    }

    @After
    public void tearDown() {
        productManager = null;
        products = null;
    }

    @Test
    public void testGetProductsWithNoProducts() {
        productManager = new SimpleProductManager();
        assertNull(productManager.getProducts());
    }
    
    /**
     *
     */
    @Test
    public void testGetProducts() {
        List<Product> productsLocal = productManager.getProducts();
        assertNotNull(productsLocal);        
        assertEquals(PRODUCT_COUNT, productManager.getProducts().size());    
        Product product = productsLocal.get(0);
        assertEquals(CHAIR_DESCRIPTION, product.getDescription());
        assertEquals(CHAIR_PRICE, product.getPrice());        
        product = productsLocal.get(1);
        assertEquals(TABLE_DESCRIPTION, product.getDescription());
        assertEquals(TABLE_PRICE, product.getPrice());      
    } 
    
    @Test
    public void testIncreasePriceWithNullListOfProducts() {
        try {
            productManager = new SimpleProductManager();
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(NullPointerException ex) {
            fail("Products list is null.");
        }
    }   
    
    @Test
    public void testIncreasePriceWithEmptyListOfProducts() {
        try {
            productManager = new SimpleProductManager();
            productManager.setProducts(new ArrayList<Product>());
            productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        }
        catch(Exception ex) {
            fail("Products list is empty.");
        }           
    }  
    
    @Test
    public void testIncreasePriceWithPositivePercentage() {
        productManager.increasePrice(POSITIVE_PRICE_INCREASE);
        Double expectedChairPriceWithIncrease = 22.55;
        Double expectedTablePriceWithIncrease = 165.11;        
        List<Product> productsLocal = productManager.getProducts();      
        Product product = productsLocal.get(0);
        assertEquals(expectedChairPriceWithIncrease, product.getPrice());        
        product = productsLocal.get(1);      
        assertEquals(expectedTablePriceWithIncrease, product.getPrice());       
    }        

}
