package com.universityadmissions.dao.mysql;

import com.universityadmissions.dao.ApplicationDao;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlApplicationDao implements ApplicationDao {
    private static final String SQL_INSERT = "INSERT INTO application (user_id, department_id, priority, date) VALUES  (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM application WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE application SET user_id=?, department_id=?, priority=?, date=? WHERE id=?";
    private static final String SQL_ADD_FINAL_GRADE = "UPDATE application SET final_grade=? WHERE id=?";
    private static final String SQL_ADD_VERIFY = "UPDATE application SET verified=? WHERE id=?";
    private static final String SQL_ADD_ACCEPT = "UPDATE application SET accepted=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM application WHERE id=?";
    private static final String SQL_SELECT_BY_USER_ID = "SELECT * FROM application WHERE user_id=?";
    private static final String SQL_SELECT_BY_DEPARTMENT_ID_AND_PRIORITY = "SELECT * FROM application WHERE department_id=? AND priority=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM application";

    private static volatile MysqlApplicationDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlApplicationDao() {

    }

    public static MysqlApplicationDao getInstance() {
        if (instance == null) {
            synchronized (MysqlApplicationDao.class) {
                if (instance == null) {
                    instance = new MysqlApplicationDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Application application) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, application.getUser().getId());
            statement.setInt(2, application.getDepartment().getId());
            statement.setInt(3, application.getPriority());
            statement.setDate(4, application.getDate());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new application.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Application application) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, application.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete application.", e);
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
            throw new DaoException("Failed to delete application.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(Application application) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, application.getUser().getId());
            statement.setInt(2, application.getDepartment().getId());
            statement.setInt(3, application.getPriority());
            statement.setDate(4, application.getDate());
            statement.setInt(5, application.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update application.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Application findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Application application = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                application = new Application(
                        resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getDepartmentDao().findById(resultSet.getInt("department_id")),
                        resultSet.getInt("priority"),
                        resultSet.getInt("final_grade"),
                        resultSet.getBoolean("verified"),
                        resultSet.getBoolean("accepted"),
                        resultSet.getDate("date")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find application by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return application;
    }

    @Override
    public List<Application> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Application> applications = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Application application = new Application(
                        resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getDepartmentDao().findById(resultSet.getInt("department_id")),
                        resultSet.getInt("priority"),
                        resultSet.getInt("final_grade"),
                        resultSet.getBoolean("verified"),
                        resultSet.getBoolean("accepted"),
                        resultSet.getDate("date")
                );
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of applications.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return applications;
    }

    @Override
    public void addFinalGrade(Integer id, Integer finalGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_ADD_FINAL_GRADE);
            statement.setInt(1, finalGrade);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to add final grade new application.", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean setVerify(Integer id, Boolean verify) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_ADD_VERIFY);
            statement.setBoolean(1, verify);
            statement.setInt(2, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to set verify new application.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public void setAccept(Integer id, Boolean accept) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_ADD_ACCEPT);
            statement.setBoolean(1, accept);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to set accept new application.", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Application> findAllByUserId(Integer userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Application> applications = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_USER_ID);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application(
                        resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getDepartmentDao().findById(resultSet.getInt("department_id")),
                        resultSet.getInt("priority"),
                        resultSet.getInt("final_grade"),
                        resultSet.getBoolean("verified"),
                        resultSet.getBoolean("accepted"),
                        resultSet.getDate("date")
                );
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of user applications.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return applications;
    }

    @Override
    public List<Application> findAllByDepartmentIdAndPriority(Integer departmentId, Integer priority) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Application> applications = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_DEPARTMENT_ID_AND_PRIORITY);
            statement.setInt(1, departmentId);
            statement.setInt(2, priority);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application(
                        resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getDepartmentDao().findById(resultSet.getInt("department_id")),
                        resultSet.getInt("priority"),
                        resultSet.getInt("final_grade"),
                        resultSet.getBoolean("verified"),
                        resultSet.getBoolean("accepted"),
                        resultSet.getDate("date")
                );
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of applications.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return applications;
    }
}
