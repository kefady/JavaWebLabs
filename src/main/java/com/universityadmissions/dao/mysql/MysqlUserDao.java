package com.universityadmissions.dao.mysql;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.*;
import com.universityadmissions.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private static final String SQL_INSERT = "INSERT INTO user (username, password, surname, name, patronymic, email, city, region, edu_name, birthday, blocked) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE user SET role_id=?, username=?, password=?, surname=?, name=?, patronymic=?, email=?, city=?, region=?, edu_name=?, birthday=?, blocked=? WHERE id=?";
    private static final String SQL_BLOCKED = "UPDATE user SET blocked=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id=?";
    private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM user WHERE username=?";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String SQL_SELECT_BY_SURNAME = "SELECT * FROM user WHERE surname=?";
    private static final String SQL_SELECT_BY_SURNAME_AND_NAME = "SELECT * FROM user WHERE surname=? AND name=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM user";

    private static volatile MysqlUserDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlUserDao() {

    }

    public static MysqlUserDao getInstance() {
        if (instance == null) {
            synchronized (MysqlUserDao.class) {
                if (instance == null) {
                    instance = new MysqlUserDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getName());
            statement.setString(5, user.getPatronymic());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getCity());
            statement.setString(8, user.getRegion());
            statement.setString(9, user.getEduName());
            statement.setDate(10, user.getBirthday());
            statement.setBoolean(11, user.isBlocked());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new user.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete user.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete user.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, user.getRole().getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getName());
            statement.setString(6, user.getPatronymic());
            statement.setString(7, user.getEmail());
            statement.setString(8, user.getCity());
            statement.setString(9, user.getRegion());
            statement.setString(10, user.getEduName());
            statement.setDate(11, user.getBirthday());
            statement.setBoolean(12, user.isBlocked());
            statement.setInt(13, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update user.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public User findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find user by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_USERNAME);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find user by username.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find user by email.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return user;
    }

    @Override
    public List<User> findBySurname(String surname) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_SURNAME);
            statement.setString(1, surname);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find user by surname.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findBySurnameAndName(String surname, String name) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_SURNAME_AND_NAME);
            statement.setString(1, surname);
            statement.setString(2, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find user by surname and name.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return users;
    }

    @Override
    public void setBlocked(Integer id, boolean blocked) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_BLOCKED);
            statement.setBoolean(1, blocked);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to block user.", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        daoFactory.getRoleDao().findById(resultSet.getInt("role_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("email"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("edu_name"),
                        resultSet.getDate("birthday"),
                        resultSet.getBoolean("blocked")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of users.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return users;
    }
}
