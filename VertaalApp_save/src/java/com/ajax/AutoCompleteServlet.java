/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
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

    private ServletContext context;
    // TO DO: lees Excel en maak VertaalRegel's 

    private final HashMap<String, VertaalRegel> vertaalregels;

    public AutoCompleteServlet() {
        this.vertaalregels = new HashMap<>();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
        try {
            readExcel(vertaalregels);
        } catch (IOException ex) {
            Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Stub:
        /*
        VertaalRegel regel = new VertaalRegel();
        regel.setId("1");
        regel.setDutchLine("verhaalopdracht");
        regel.setEnglishLine("recover order");
        vertaalregels.put(regel.getId(), regel);
        regel = new VertaalRegel();
        regel.setId("2");
        regel.setDutchLine("meldloket");
        regel.setEnglishLine("register counter");
        vertaalregels.put(regel.getId(), regel);
        regel = new VertaalRegel();
        regel.setId("3");
        regel.setDutchLine("medewerker");
        regel.setEnglishLine("employee");
        vertaalregels.put(regel.getId(), regel);
        */
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
            if (!targetId.equals("")) {

                Iterator it = vertaalregels.keySet().iterator();

                while (it.hasNext()) {
                    String id = (String) it.next();
                    VertaalRegel regel = (VertaalRegel) vertaalregels.get(id);

                    if ( // targetId matches dutch line
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
    private void readExcel(HashMap<String, VertaalRegel> vertaalregels) throws IOException {
        File inputWorkbook = new File("C:\\temp\\Vertaaltabel Achmea.xls");
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
                    String englishLine = cell2.getContents();
                    VertaalRegel regel = new VertaalRegel();
                    regel.setId(String.valueOf(i));
                    regel.setDutchLine(dutchLine);
                    regel.setEnglishLine(englishLine);
                    vertaalregels.put(regel.getId(), regel);
                    String tekst = dutchLine + " - " + englishLine;
                    Logger.getLogger(AutoCompleteServlet.class.getName()).log(Level.INFO, tekst);
                }
            }
            w.close();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }

}
