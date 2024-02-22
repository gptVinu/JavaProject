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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vinyak
 */
@WebServlet(urlPatterns = {"/DeleteImageServlet"})
public class DeleteImageServlet extends HttpServlet {

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
        String deleteImageName = request.getParameter("delete_image_name");
        try {
            // JDBC Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost/myDataStorage?autoReconnect=true&useSSL=false";
            String dbUsername = "root";
            String dbPassword = "Vinu@8898";
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the database");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            // SQL Query
            String sql = "delete from exhibit_image_details WHERE Image_Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, deleteImageName);

                // Execute Query
                int ret = statement.executeUpdate();

                PrintWriter out = response.getWriter();
                if (ret > 0) {
                    // Image found in the database
                    out.println("<div class=\"container\">");
                    out.println("<p class=\"message\" id=\"searchMessage\">Image deleted from database.</p>");
                    // You can retrieve other details from resultSet and display as needed
                    out.println("</div>");
                } else {
                    // Image not found in the database
                    out.println("<div class=\"container\">");
                    out.println("<p class=\"message\" id=\"searchMessage\">Image not available in the database.</p>");
                    out.println("</div>");
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging purposes
            // Handle the exception properly in a real application
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
