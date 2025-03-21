package com.example.exam01.dao;

import com.example.exam01.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAO<User> {
    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
    List<User> findUsersWithActiveBorrows();
}