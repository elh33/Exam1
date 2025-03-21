package com.example.exam01.servlet;

import com.example.exam01.dao.DocumentDAO;
import com.example.exam01.daoImpl.DocumentDAOImpl;
import com.example.exam01.model.Document;
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

@WebServlet("/api/documents")
public class DocumentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        DocumentDAO documentDAO = new DocumentDAOImpl(em);

        List<Document> documents = documentDAO.getAll();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJson(documents));
    }
}