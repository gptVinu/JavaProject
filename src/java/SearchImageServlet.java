/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search_image")
public class SearchImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String searchImageName = request.getParameter("search_image_name");
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
            String sql = "SELECT * FROM exhibit_image_details WHERE Image_Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, searchImageName);

                // Execute Query
                ResultSet resultSet = statement.executeQuery();

                PrintWriter out = response.getWriter();
                if (resultSet.next()) {
                    // Image found in the database
                    out.println("<div class=\"container\">");
                    out.println("<p class=\"message\" id=\"searchMessage\">Image available in the database.</p>");
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
}