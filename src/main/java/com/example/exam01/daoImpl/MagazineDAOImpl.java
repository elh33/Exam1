package com.example.exam01.daoImpl;

import com.example.exam01.dao.MagazineDAO;
import com.example.exam01.model.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MagazineDAOImpl implements MagazineDAO {
    private EntityManager entityManager;

    public MagazineDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Magazine> get(Long id) {
        return Optional.ofNullable(entityManager.find(Magazine.class, id));
    }

    @Override
    public List<Magazine> getAll() {
        TypedQuery<Magazine> query = entityManager.createQuery("SELECT m FROM Magazine m", Magazine.class);
        return query.getResultList();
    }

    @Override
    public void save(Magazine magazine) {
        entityManager.getTransaction().begin();
        entityManager.persist(magazine);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Magazine magazine) {
        entityManager.getTransaction().begin();
        entityManager.merge(magazine);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Magazine magazine) {
        entityManager.getTransaction().begin();
        entityManager.remove(magazine);
        entityManager.getTransaction().commit();
    }
    @Override
    public List<Magazine> findByPublisher(String publisher) {
        TypedQuery<Magazine> query = entityManager.createQuery(
                "SELECT m FROM Magazine m WHERE m.publisher LIKE :publisher", Magazine.class);
        query.setParameter("publisher", "%" + publisher + "%");
        return query.getResultList();
    }

    @Override
    public List<Magazine> findByIssueNumber(String issueNumber) {
        TypedQuery<Magazine> query = entityManager.createQuery(
                "SELECT m FROM Magazine m WHERE m.issueNumber = :issueNumber", Magazine.class);
        query.setParameter("issueNumber", issueNumber);
        return query.getResultList();
    }

    @Override
    public List<Magazine> findByIssueDate(Date date) {
        TypedQuery<Magazine> query = entityManager.createQuery(
                "SELECT m FROM Magazine m WHERE m.dateIssue = :date", Magazine.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}