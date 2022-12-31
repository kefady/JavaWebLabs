package com.universityadmissions.dao.mysql;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.ExamNameDao;
import com.universityadmissions.entity.ExamName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlExamNameDao implements ExamNameDao {
    private static final String SQL_INSERT = "INSERT INTO exam_name (name) VALUES  (?)";
    private static final String SQL_DELETE = "DELETE FROM exam_name WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE exam_name SET name=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM exam_name WHERE id=?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM exam_name WHERE name=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM exam_name";

    private static volatile MysqlExamNameDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlExamNameDao() {

    }

    public static MysqlExamNameDao getInstance() {
        if (instance == null) {
            synchronized (MysqlExamNameDao.class) {
                if (instance == null) {
                    instance = new MysqlExamNameDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(ExamName examName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, examName.getName());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new exam name.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(ExamName examName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, examName.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete exam name.", e);
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
            throw new DaoException("Failed to delete exam name.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(ExamName examName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, examName.getName());
            statement.setInt(2, examName.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update exam name.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public ExamName findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExamName examName = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                examName = new ExamName(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam name by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return examName;
    }

    @Override
    public ExamName findByName(String examName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExamName exam = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            statement.setString(1, examName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exam = new ExamName(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam name by name.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return exam;
    }

    @Override
    public List<ExamName> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ExamName> examNames = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                ExamName examName = new ExamName(resultSet.getInt("id"), resultSet.getString("name"));
                examNames.add(examName);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of exam names.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return examNames;
    }
}
