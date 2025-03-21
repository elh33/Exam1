package com.example.exam01.servlet;

import com.example.exam01.dao.MagazineDAO;
import com.example.exam01.daoImpl.MagazineDAOImpl;
import com.example.exam01.model.Magazine;
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

@WebServlet("/api/magazines")
public class MagazineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        MagazineDAO magazineDAO = new MagazineDAOImpl(em);

        try {
            List<Magazine> magazines = magazineDAO.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(magazines));
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        MagazineDAO magazineDAO = new MagazineDAOImpl(em);

        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Magazine magazine = JsonUtil.fromJson(requestBody, Magazine.class);

            magazineDAO.save(magazine);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(magazine));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error processing request: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}