/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author vinyak
 */
@WebServlet(urlPatterns = {"/DisplayImagesServlet"})
public class DisplayImagesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private String encodeImageToBase64(InputStream inputStream) throws IOException {
        byte[] imageBytes = inputStream.readAllBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Database connection parameters
            String jdbcUrl = "jdbc:mysql://localhost/MyDataStorage?autoReconnect=true&useSSL=false";
            String dbUser = "root";
            String dbPassword = "Vinu@8898";
            
            PrintWriter out = response.getWriter();
            //out.print("hello");
            Class.forName("com.mysql.cj.jdbc.Driver");  
            // Create a connection to the database
            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                // Query to fetch data from the database
                String sql = "SELECT image_id, image_name, file, owner, image_price, created_at FROM exhibit_image_details";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        // Create the HTML content dynamically
                        StringBuilder htmlContent = new StringBuilder();
                        //htmlContent.append("<div id=\"img_cont\" class=\"column\">");
                        int gr = 0;
                        StringBuilder htmlCont[] = new StringBuilder[4];
                        for (int i = 0; i < 4; i++){
                            htmlCont[i] = new StringBuilder();
                        }
                        for (int i = 0; i < 4; i++){
                            htmlCont[i].append("<div id=\"img_cont\" class=\"column\">");
                        }
                        while (rs.next()) {
                            int imageId = rs.getInt("image_id");
                            String imageName = rs.getString("image_name");
                            InputStream fileInputStream = rs.getBinaryStream("file");
                            String owner = rs.getString("owner");
                            BigDecimal imagePrice = rs.getBigDecimal("image_price");
                            Timestamp createdAt = rs.getTimestamp("created_at");
                            
                            String base64EncodedImage = encodeImageToBase64(fileInputStream);

                            // Create a child div for each row
                            //htmlContent.append("<div class=\"image-container\">");
//                            htmlContent.append("<p>Image ID: ").append(imageId).append("</p>");
//                            htmlContent.append("<p>Image Name: ").append(imageName).append("</p>");
//                            htmlContent.append("<p>Owner: ").append(owner).append("</p>");
//                            htmlContent.append("<p>Image Price: ").append(imagePrice).append("</p>");
//                            htmlContent.append("<p>Created At: ").append(createdAt).append("</p>");
                            htmlCont[gr].append("<img src=\"data:image/jpeg;base64,").append(base64EncodedImage).append("\" alt=\"Image\" style=\"width:100%\">");
                            //htmlContent.append("</div>");
                            gr++;
                            if (gr == 4){                               
                                gr = 0;
                            }
                        }
                        for (int i = 0; i < 4; i++){
                            htmlCont[i].append("</div>");
                        }
                        String mainStr = "";
                        for (int i = 0; i < 4; i++){
                            mainStr += htmlCont[i].toString();
                        }
                        // Write the HTML content to the response
                        response.getWriter().write(mainStr);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle the exception appropriately
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
        processRequest(request, response);
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

}
