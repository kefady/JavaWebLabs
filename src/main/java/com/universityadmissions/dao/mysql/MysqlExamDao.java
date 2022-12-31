package com.universityadmissions.dao.mysql;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.*;
import com.universityadmissions.entity.Exam;
import com.universityadmissions.entity.ExamName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlExamDao implements ExamDao {
    private static final String SQL_INSERT = "INSERT INTO exam (exam_name_id, min_grade, coefficient) VALUES  (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM exam WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE exam SET exam_name_id=?, min_grade=?, coefficient=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM exam WHERE id=?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM exam WHERE exam_name_id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM exam";

    private static volatile MysqlExamDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlExamDao() {

    }

    public static MysqlExamDao getInstance() {
        if (instance == null) {
            synchronized (MysqlExamDao.class) {
                if (instance == null) {
                    instance = new MysqlExamDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Exam exam) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, exam.getExamName().getId());
            statement.setInt(2, exam.getMinGrade());
            statement.setDouble(3, exam.getCoefficient());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new exam.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Exam exam) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, exam.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete exam.", e);
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
            throw new DaoException("Failed to delete exam.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(Exam exam) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, exam.getExamName().getId());
            statement.setInt(2, exam.getMinGrade());
            statement.setDouble(3, exam.getCoefficient());
            statement.setInt(4, exam.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update exam.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Exam findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Exam exam = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exam = new Exam(
                        resultSet.getInt("id"),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("min_grade"),
                        resultSet.getDouble("coefficient")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return exam;
    }

    @Override
    public Exam findByExamName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Exam exam = null;
        ExamName examName;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            examName = daoFactory.getExamNameDao().findByName(name);
            statement.setInt(1, examName.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exam = new Exam(
                        resultSet.getInt("id"),
                        examName,
                        resultSet.getInt("min_grade"),
                        resultSet.getDouble("coefficient")
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam by name.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return exam;
    }

    @Override
    public List<Exam> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Exam> exams = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Exam exam = new Exam(
                        resultSet.getInt("id"),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("min_grade"),
                        resultSet.getDouble("coefficient")
                );
                exams.add(exam);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of exams.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return exams;
    }
}
