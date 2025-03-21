package com.example.exam01.dao;

import com.example.exam01.model.Document;
import java.util.List;
import java.util.Date;

public interface DocumentDAO extends DAO<Document> {
    List<Document> getAllAvailable();
    List<Document> findByTitle(String title);
    List<Document> findByCreationDate(Date date);
}