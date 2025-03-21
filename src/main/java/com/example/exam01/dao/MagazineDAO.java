package com.example.exam01.dao;

import com.example.exam01.model.Magazine;
import java.util.List;
import java.util.Date;

public interface MagazineDAO extends DAO<Magazine> {
    List<Magazine> findByPublisher(String publisher);
    List<Magazine> findByIssueNumber(String issueNumber);
    List<Magazine> findByIssueDate(Date date);
}