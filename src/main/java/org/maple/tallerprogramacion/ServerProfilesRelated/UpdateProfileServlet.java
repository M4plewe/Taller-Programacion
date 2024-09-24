package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "updateProfileServlet", value = "/update-profile-servlet")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tallerdeprogramacion";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            deleteProfile(request, response);
        } else {
            updateProfile(request, response);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        Part filePart = request.getPart("profileImage");

        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");
        String currentEmail = (String) session.getAttribute("email");
        String currentProfileImage = (String) session.getAttribute("profileImage");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false);

            // Verificar si el nuevo nombre de usuario ya existe
            String usernameCheckQuery = "SELECT username FROM Users WHERE username = ?";
            boolean usernameExists = false;
            try (PreparedStatement usernameCheckStmt = conn.prepareStatement(usernameCheckQuery)) {
                usernameCheckStmt.setString(1, newUsername);
                ResultSet rs = usernameCheckStmt.executeQuery();
                if (rs.next() && !currentUsername.equals(newUsername)) {
                    usernameExists = true;
                }
            }

            // Verificar si el nuevo correo electrónico ya existe (solo si está cambiando)
            boolean emailExists = false;
            if (!email.equals(currentEmail)) {
                String emailCheckQuery = "SELECT email FROM Users WHERE email = ?";
                try (PreparedStatement emailCheckStmt = conn.prepareStatement(emailCheckQuery)) {
                    emailCheckStmt.setString(1, email);
                    ResultSet rs = emailCheckStmt.executeQuery();
                    if (rs.next()) {
                        emailExists = true;
                    }
                }
            }

            if (usernameExists) {
                response.sendRedirect("editarperfil.jsp?error=Username already exists");
                return;
            } else if (emailExists) {
                response.sendRedirect("editarperfil.jsp?error=Email already exists");
                return;
            }

            // Actualizar la información del perfil
            String profileImagePath = filePart != null && filePart.getSize() > 0 ? saveProfileImage(filePart) : currentProfileImage;

            String updateQuery = "UPDATE Users SET username = ?, email = ?, picture = ? WHERE username = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, newUsername);
                updateStmt.setString(2, email);
                updateStmt.setString(3, profileImagePath); // Mantener la imagen si no se ha subido una nueva
                updateStmt.setString(4, currentUsername);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    session.setAttribute("username", newUsername);
                    session.setAttribute("email", email);
                    session.setAttribute("profileImage", profileImagePath);
                    response.sendRedirect("editarperfil.jsp");
                } else {
                    response.sendRedirect("editarperfil.jsp?error=User not found");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }


    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false);

            String deleteQuery = "DELETE FROM Users WHERE username = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, currentUsername);

                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    session.invalidate();
                    response.sendRedirect("login.jsp");
                } else {
                    response.sendRedirect("editarperfil.jsp?error=User not found");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw new ServletException("Database error: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new ServletException("Database connection error: " + e.getMessage(), e);
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
