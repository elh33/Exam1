package com.example.exam01.daoImpl;

import com.example.exam01.dao.BookDAO;
import com.example.exam01.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {
    private EntityManager entityManager;

    public BookDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Book> get(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void save(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Book book) {
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Book book) {
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.author LIKE :author", Book.class);
        query.setParameter("author", "%" + author + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> findByISBN(String isbn) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    @Override
    public List<Book> findByPublicationDate(Date date) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.datePublication = :date", Book.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}