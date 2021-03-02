/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 *
 * @author dknottne
 */
@WebServlet(name = "AutoCompleteServlet", urlPatterns = {"/autocomplete"})
public class AutoCompleteServlet extends HttpServlet {
    static private final String CRLF = "\r\n";
    private ServletContext context;
    // TO DO: lees Excel en maak VertaalRegel's 

    private final List<VertaalRegel> vertaalregels;

    public AutoCompleteServlet() {
        this.vertaalregels = new ArrayList<>();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
        try {
            readExcel(vertaalregels);
        } catch (IOException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AutoCompleteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AutoCompleteServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");
        StringBuilder sb = new StringBuilder();

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean linesAdded = false;
        if (action.equals("complete")) {
            // check if user sent empty string
            if (targetId != null && !targetId.equals("") && 1 < targetId.length()) {
                for (VertaalRegel regel : vertaalregels) {
                    if ( // targetId occurs within dutch line
                            regel.getDutchLine().toLowerCase().contains(targetId)) {
                        sb.append("<vertaalregel>");
                        sb.append("<id>").append(regel.getId()).append("</id>");
                        sb.append("<dutchLine>").append(regel.getDutchLine()).append("</dutchLine>");
                        sb.append("<englishLine>").append(regel.getEnglishLine()).append("</englishLine>");
                        sb.append("</vertaalregel>");
                        linesAdded = true;
                    }
                }
            }

            if (linesAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<vertaalregels>" + sb.toString() + "</vertaalregels>");
            //    logXML("<vertaalregels>" + sb.toString() + "</vertaalregels>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Reads the Excelsheet and makes a VertaalRegel for every line, then 
     * adds that VertaalRegel to the HashMap.
     * @param vertaalregels
     * @throws IOException 
     */
    private void readExcel(List<VertaalRegel> vertaalregels) throws IOException, URISyntaxException {
       // File inputWorkbook = new File("C:\\temp\\VertaaltabelAchmea.xls"); werkt, maar niet mooi
        
       // File inputWorkbook = new File("resources\\VertaaltabelAchmea.xls");   werkt niet
       // File inputWorkbook = new File("VertaaltabelAchmea.xls");   werkt niet 
       // File inputWorkbook = new File("/resources/VertaaltabelAchmea.xls"); werkt niet
       // File inputWorkbook = new File("/WEB-INF/classes/resources/VertaaltabelAchmea.xls"); werkt niet
       // File inputWorkbook = new File("WEB-INF/classes/resources/VertaaltabelAchmea.xls"); werkt niet
       URL  url = getClass().getResource("/resources/VertaaltabelAchmea.xls");
        System.out.println("URL = " + url);
        System.out.println("URI = " + url.toURI());
        
       File inputWorkbook = new File(url.toURI());
        
        Workbook w;

        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet;

            for (int k = 0; k < w.getNumberOfSheets(); k++) {

                sheet = w.getSheet(k);

                //   	Cell headercell = sheet.getCell(j, 0);
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell1 = sheet.getCell(0, i);
                    Cell cell2 = sheet.getCell(1, i);
                    String dutchLine = cell1.getContents();
                    if ("".equals(dutchLine)) dutchLine = "-";
                    String englishLine = cell2.getContents();
                    if ("".equals(englishLine)) englishLine = "-";
                    VertaalRegel regel = new VertaalRegel();
                    regel.setId(String.valueOf(i));
                    regel.setDutchLine(dutchLine);
                    regel.setEnglishLine(englishLine);
                    vertaalregels.add(regel);
                    String tekst = dutchLine + " - " + englishLine;
                    Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.INFO, tekst);
                }
            }
            w.close();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }
    
    private void logXML(String xml) {
        String[] lines = xml.split("<");

        for (String xmlline : lines)
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.INFO, "<{0}", xmlline);
    }
    

}
