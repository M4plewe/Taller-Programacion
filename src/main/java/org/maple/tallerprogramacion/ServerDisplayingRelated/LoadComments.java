package org.maple.tallerprogramacion.ServerDisplayingRelated;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/load-comments")
public class LoadComments extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("post_id");
        if (postId == null || postId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing post_id parameter");
            return;
        }

        List<Comment> comments = new ArrayList<>();
        try (Connection conn = new MySQLDBConnection().getConnection()) {
            String sql = "SELECT c.*, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.post_id = ? ORDER BY c.created_at ASC";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, postId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setId(resultSet.getInt("id"));
                        comment.setPostId(resultSet.getInt("post_id"));
                        comment.setUsername(resultSet.getString("username"));
                        comment.setContent(resultSet.getString("content"));
                        comment.setCreatedAt(resultSet.getTimestamp("created_at"));
                        comments.add(comment);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error loading comments", e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            out.print(gson.toJson(comments));
            out.flush();
        }
    }
}