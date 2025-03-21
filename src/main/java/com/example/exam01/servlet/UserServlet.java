package com.example.exam01.servlet;

import com.example.exam01.dao.UserDAO;
import com.example.exam01.daoImpl.UserDAOImpl;
import com.example.exam01.model.User;
import com.example.exam01.util.JPAUtil;
import com.example.exam01.util.JsonUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        UserDAO userDAO = new UserDAOImpl(em);

        try {
            List<User> users = userDAO.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(users));
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        UserDAO userDAO = new UserDAOImpl(em);

        try {
            // Get request body
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            User user = JsonUtil.fromJson(requestBody, User.class);

            userDAO.save(user);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(user));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error processing request: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}