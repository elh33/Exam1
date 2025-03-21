package com.example.exam01.daoImpl;

import com.example.exam01.dao.BorrowDAO;
import com.example.exam01.model.Borrow;
import com.example.exam01.model.Document;
import com.example.exam01.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BorrowDAOImpl implements BorrowDAO {
    private EntityManager entityManager;

    public BorrowDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Borrow> get(Long id) {
        return Optional.ofNullable(entityManager.find(Borrow.class, id));
    }

    @Override
    public List<Borrow> getAll() {
        TypedQuery<Borrow> query = entityManager.createQuery("SELECT b FROM Borrow b", Borrow.class);
        return query.getResultList();
    }

    @Override
    public void save(Borrow borrow) {
        entityManager.getTransaction().begin();
        entityManager.persist(borrow);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Borrow borrow) {
        entityManager.getTransaction().begin();
        entityManager.merge(borrow);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Borrow borrow) {
        entityManager.getTransaction().begin();
        entityManager.remove(borrow);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Borrow> findByUser(User user) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b JOIN b.users u WHERE :user MEMBER OF b.users", Borrow.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Borrow> findByDocument(Document document) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b JOIN b.documents d WHERE :document MEMBER OF b.documents", Borrow.class);
        query.setParameter("document", document);
        return query.getResultList();
    }

    @Override
    public List<Borrow> findActiveBorrows() {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.returnDate IS NULL", Borrow.class);
        return query.getResultList();
    }

    @Override
    public List<Borrow> findOverdueBorrows(Date currentDate) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.returnDate IS NULL AND b.dueDate < :currentDate",
                Borrow.class);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }
    @Override
    public List<Borrow> findByBorrowDate(Date date) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.dateBorrow = :date", Borrow.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Borrow> findByReturnDate(Date date) {
        TypedQuery<Borrow> query = entityManager.createQuery(
                "SELECT b FROM Borrow b WHERE b.returnDate = :date", Borrow.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
    @Override
    public void returnDocument(Borrow borrow, Document document, Date returnDate) {
        entityManager.getTransaction().begin();

        // If this is the only document in the borrow, set the return date on the borrow
        if (borrow.getDocuments().size() == 1 && borrow.getDocuments().contains(document)) {
            borrow.setReturnDate(returnDate);
            entityManager.merge(borrow);
        } else {
            // Otherwise, remove this document from the borrow's documents
            borrow.getDocuments().remove(document);
            entityManager.merge(borrow);

            // And create a new borrow record for just this document with a return date
            Borrow returnBorrow = new Borrow();
            List<User> users = new ArrayList<>(borrow.getUsers());
            returnBorrow.setUsers(users);

            List<Document> documents = new ArrayList<>();
            documents.add(document);
            returnBorrow.setDocuments(documents);

            returnBorrow.setDateBorrow(borrow.getDateBorrow());
            returnBorrow.setDueDate(borrow.getDueDate());
            returnBorrow.setReturnDate(returnDate);

            entityManager.persist(returnBorrow);
        }

        entityManager.getTransaction().commit();
    }
}