package com.example.exam01.dao;

import com.example.exam01.model.Borrow;
import com.example.exam01.model.Document;
import com.example.exam01.model.User;
import java.util.Date;
import java.util.List;

public interface BorrowDAO extends DAO<Borrow> {
    List<Borrow> findByUser(User user);
    List<Borrow> findByDocument(Document document);
    List<Borrow> findActiveBorrows();
    List<Borrow> findOverdueBorrows(Date currentDate);
    List<Borrow> findByBorrowDate(Date date);
    List<Borrow> findByReturnDate(Date date);
    void returnDocument(Borrow borrow, Document document, Date returnDate);
}
