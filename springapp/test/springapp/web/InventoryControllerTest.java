/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package springapp.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.servlet.ModelAndView;
import springapp.service.SimpleProductManager;

/**
 *
 * @author dknottne
 */
public class InventoryControllerTest {
    
    public InventoryControllerTest() {
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
     * Test of handleRequest method, of class InventoryController.
     * @throws java.lang.Exception
     */
    @Test
    public void testHandleRequest() throws Exception {
        System.out.println("handleRequest");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        InventoryController instance = new InventoryController();
        instance.setProductManager(new SimpleProductManager());
        String expResult = "hello";
        ModelAndView modelAndView;
        modelAndView = instance.handleRequest(request, response);
        
        assertEquals(expResult, modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        Map modelMap = (Map) modelAndView.getModel().get("model");
        String nowValue = (String) modelMap.get("now");
        assertNotNull(nowValue);
    }
    
}
