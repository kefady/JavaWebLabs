package com.universityadmissions.dao;

import com.universityadmissions.entity.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends Entity> {

    boolean create(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(K id) throws DaoException;

    boolean update(T t) throws DaoException;

    T findById(K id) throws DaoException;

    List<T> findAll() throws DaoException;

    default void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Log
            throw new DaoException("Failed to close Statement.", e);
        }
    }

    default void close(ResultSet resultSet) throws DaoException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            // Log
            throw new DaoException("Failed to close ResultSet.", e);
        }
    }

    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Log
            throw new DaoException("Failed to close Connection.", e);
        }
    }
}
