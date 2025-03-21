package com.example.exam01.servlet;

import com.example.exam01.dao.BorrowDAO;
import com.example.exam01.dao.DocumentDAO;
import com.example.exam01.dao.UserDAO;
import com.example.exam01.daoImpl.BorrowDAOImpl;
import com.example.exam01.daoImpl.DocumentDAOImpl;
import com.example.exam01.daoImpl.UserDAOImpl;
import com.example.exam01.model.Borrow;
import com.example.exam01.model.Document;
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
import java.util.*;

@WebServlet("/api/borrows")
public class BorrowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        BorrowDAO borrowDAO = new BorrowDAOImpl(em);

        try {
            List<Borrow> borrows = borrowDAO.findActiveBorrows();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(borrows));
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        BorrowDAO borrowDAO = new BorrowDAOImpl(em);
        UserDAO userDAO = new UserDAOImpl(em);
        DocumentDAO documentDAO = new DocumentDAOImpl(em);

        try {
            // Parse request parameters
            Long userId = Long.parseLong(request.getParameter("userId"));
            Long documentId = Long.parseLong(request.getParameter("documentId"));

            // Get user and document
            Optional<User> userOpt = userDAO.get(userId);
            Optional<Document> documentOpt = documentDAO.get(documentId);

            if (userOpt.isPresent() && documentOpt.isPresent()) {
                User user = userOpt.get();
                Document document = documentOpt.get();

                // Create borrow
                Borrow borrow = new Borrow();

                List<User> users = new ArrayList<>();
                users.add(user);
                borrow.setUsers(users);

                List<Document> documents = new ArrayList<>();
                documents.add(document);
                borrow.setDocuments(documents);

                borrow.setDateBorrow(new Date());

                // Set due date to 14 days from now
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                borrow.setDueDate(calendar.getTime());

                borrowDAO.save(borrow);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JsonUtil.toJson(borrow));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("User or document not found");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error processing request: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}