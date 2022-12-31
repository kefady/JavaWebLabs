package com.universityadmissions.dao;

import com.universityadmissions.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<Integer, User> {
    User findByUsername(String username) throws DaoException;
    User findByEmail(String email) throws DaoException;

    List<User> findBySurname(String surname) throws DaoException;

    List<User> findBySurnameAndName(String surname, String name) throws DaoException;
    void setBlocked(Integer id, boolean blocked) throws DaoException;
}
