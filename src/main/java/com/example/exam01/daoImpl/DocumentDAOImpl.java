package com.example.exam01.daoImpl;

import com.example.exam01.dao.DocumentDAO;
import com.example.exam01.model.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DocumentDAOImpl implements DocumentDAO {
    private EntityManager entityManager;

    public DocumentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Document> get(Long id) {
        return Optional.ofNullable(entityManager.find(Document.class, id));
    }

    @Override
    public List<Document> getAll() {
        TypedQuery<Document> query = entityManager.createQuery("SELECT d FROM Document d", Document.class);
        return query.getResultList();
    }

    @Override
    public void save(Document document) {
        entityManager.getTransaction().begin();
        entityManager.persist(document);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Document document) {
        entityManager.getTransaction().begin();
        entityManager.merge(document);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Document document) {
        entityManager.getTransaction().begin();
        entityManager.remove(document);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Document> getAllAvailable() {
        TypedQuery<Document> query = entityManager.createQuery(
                "SELECT d FROM Document d WHERE NOT EXISTS " +
                        "(SELECT b FROM Borrow b JOIN b.documents doc WHERE doc = d AND b.returnDate IS NULL)",
                Document.class);
        return query.getResultList();
    }

    @Override
    public List<Document> findByTitle(String title) {
        TypedQuery<Document> query = entityManager.createQuery(
                "SELECT d FROM Document d WHERE d.title LIKE :title", Document.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    public List<Document> findByCreationDate(Date date) {
        TypedQuery<Document> query = entityManager.createQuery(
                "SELECT d FROM Document d WHERE d.dateCreat = :date", Document.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}