package com.example.exam01;

import com.example.exam01.dao.*;
import com.example.exam01.daoImpl.*;
import com.example.exam01.model.*;
import com.example.exam01.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            DocumentDAO documentDAO = new DocumentDAOImpl(em);
            BookDAO bookDAO = new BookDAOImpl(em);
            UserDAO userDAO = new UserDAOImpl(em);
            BorrowDAO borrowDAO = new BorrowDAOImpl(em);

            User user = new User();
            user.setName("John Doe");
            user.setMail("john@example.com");
            userDAO.save(user);

            Book book = new Book();
            book.setTitle("Java Programming");
            book.setAuthor("James Gosling");
            book.setIsbn("123-456-789");
            book.setDateCreat(new Date());
            book.setDatePublication(new Date());
            bookDAO.save(book);

            Borrow borrow = new Borrow();
            List<User> users = new ArrayList<>();
            users.add(user);
            borrow.setUsers(users);

            List<Document> documents = new ArrayList<>();
            documents.add(book);
            borrow.setDocuments(documents);

            borrow.setDateBorrow(new Date());

            Date dueDate = new Date();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(dueDate);
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 14);
            borrow.setDueDate(calendar.getTime());

            borrowDAO.save(borrow);

            System.out.println("Active borrows:");
            List<Borrow> activeBorrows = borrowDAO.findActiveBorrows();
            for (Borrow b : activeBorrows) {
                for (User u : b.getUsers()) {
                    for (Document d : b.getDocuments()) {
                        System.out.println(" - " + u.getName() + " borrowed " + d.getTitle());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}