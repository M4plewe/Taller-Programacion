package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "UpdateProfileImageServlet", value = "/UpdateProfileImageServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UpdateProfileImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute("currentUserId");

        Part filePart = request.getPart("profileImage");
        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect("new_settings.jsp?error=No file selected");
            return;
        }

        String fileName;
        try {
            fileName = saveProfileImage(filePart);
        } catch (IOException e) {
            response.sendRedirect("new_settings.jsp?error=" + e.getMessage());
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String updateSql = "UPDATE users SET picture = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, fileName);
                pstmt.setInt(2, currentUserId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    session.setAttribute("currentProfileImage", fileName);
                    response.sendRedirect("new_settings.jsp?message=Profile image updated successfully");
                } else {
                    response.sendRedirect("new_settings.jsp?error=Error updating profile image");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_settings.jsp?error=Database error");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveProfileImage(Part filePart) throws IOException {
        String uploadDir = getServletContext().getRealPath("/resources/profilepictures/userprofilepictures");
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + getFileExtension(filePart);

        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IOException("Invalid file type. Only images are allowed.");
        }

        File file = new File(uploadDirFile, fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return "profilepictures/userprofilepictures/" + fileName;
    }

    private String getFileExtension(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }
}