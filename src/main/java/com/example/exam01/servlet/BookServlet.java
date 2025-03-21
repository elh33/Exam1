package com.example.exam01.servlet;

import com.example.exam01.dao.BookDAO;
import com.example.exam01.daoImpl.BookDAOImpl;
import com.example.exam01.model.Book;
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

@WebServlet("/api/books")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        BookDAO bookDAO = new BookDAOImpl(em);

        try {
            List<Book> books = bookDAO.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(books));
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        BookDAO bookDAO = new BookDAOImpl(em);

        try {
            // Get request body
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Book book = JsonUtil.fromJson(requestBody, Book.class);

            bookDAO.save(book);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(book));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error processing request: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}