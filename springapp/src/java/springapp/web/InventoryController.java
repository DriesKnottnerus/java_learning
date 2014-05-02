/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springapp.service.ProductManager;

/**
 *
 * @author dknottne
 */
public class InventoryController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());
    private ProductManager productManager;

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("now", now);
        myModel.put("products", this.productManager.getProducts());
        return new ModelAndView("hello", "model", myModel);
        // the viewResolver will prefix "hello" with "WEB-INF/jsp", and suffix with ".jsp", see springapp-servlet.xml
    }
    
    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    } 
}
