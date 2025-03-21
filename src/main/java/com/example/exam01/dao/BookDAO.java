package com.example.exam01.dao;

import com.example.exam01.model.Book;
import java.util.List;
import java.util.Date;

public interface BookDAO extends DAO<Book> {
    List<Book> findByAuthor(String author);
    List<Book> findByISBN(String isbn);
    List<Book> findByPublicationDate(Date date);
}