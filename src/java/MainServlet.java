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
import java.sql.*;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 *
 * @author vinyak
 */
@WebServlet(urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

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
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet MainServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            out.println("hello");
        
        String imageId = request.getParameter("image_id");
        String imageName = request.getParameter("image_name");
        String imageAuthor = request.getParameter("image_author");
        String imagePrice = request.getParameter("image_price");
       


        // Fetching the image file
        Part filePart = request.getPart("image");
        InputStream fileInputStream = filePart.getInputStream();
                  
            try
          {  
               //load the driver
               Class.forName("com.mysql.cj.jdbc.Driver");  
               out.print("till here");
               //create connection object
               Connection con=DriverManager.getConnection("jdbc:mysql://localhost/MyDataStorage?autoReconnect=true&useSSL=false","root","Vinu@8898");  
               // create the prepared statement object
               PreparedStatement pstmt=con.prepareStatement("insert into Exhibit_image_details values(?,?,?,?,?,?)");  
 
                
                pstmt.setInt(1, Integer.parseInt(imageId));
                pstmt.setString(2, imageName);
                pstmt.setBlob(3, fileInputStream);
                pstmt.setString(4, imageAuthor);
                pstmt.setBigDecimal(5, new BigDecimal(imagePrice));
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

                int i=pstmt.executeUpdate();
               
               if(i>0)  
               out.print("You are successfully registered...");  
  
            }
            catch (Exception ex)
            {
                out.print(ex.getMessage());
                 ex.printStackTrace();
            }
            out.close();
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
