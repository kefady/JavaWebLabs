package com.universityadmissions.dao.mysql;

import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.ExamGradeDao;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.ExamGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlExamGradeDao implements ExamGradeDao {
    private static final String SQL_INSERT = "INSERT INTO exam_grade (user_id, exam_name_id, grade) VALUES  (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM exam_grade WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE exam_grade SET user_id=?, exam_name_id=?, grade=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM exam_grade WHERE id=?";
    private static final String SQL_SELECT_BY_EXAM_NAME_ID = "SELECT id FROM exam_grade WHERE exam_name_id=?";
    private static final String SQL_SELECT_GRADE_BY_USER_ID_AND_EXAM_NAME_ID = "SELECT grade FROM exam_grade WHERE user_id=? AND exam_name_id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM exam_grade";

    private static volatile MysqlExamGradeDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlExamGradeDao() {

    }

    public static MysqlExamGradeDao getInstance() {
        if (instance == null) {
            synchronized (MysqlExamGradeDao.class) {
                if (instance == null) {
                    instance = new MysqlExamGradeDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(ExamGrade examGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, examGrade.getUser().getId());
            statement.setInt(2, examGrade.getExamName().getId());
            statement.setInt(3, examGrade.getGrade());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new exam grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(ExamGrade examGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, examGrade.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete exam grade.", e);
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
            throw new DaoException("Failed to delete exam grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(ExamGrade examGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, examGrade.getUser().getId());
            statement.setInt(2, examGrade.getExamName().getId());
            statement.setInt(3, examGrade.getGrade());
            statement.setInt(4, examGrade.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update exam grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public ExamGrade findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExamGrade examGrade = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                examGrade = new ExamGrade(resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("grade"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam name by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return examGrade;
    }

    @Override
    public Integer findGradeByUserIdAndExamNameId(Integer userId, Integer examNameId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer grade = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_GRADE_BY_USER_ID_AND_EXAM_NAME_ID);
            statement.setInt(1, userId);
            statement.setInt(2, examNameId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                grade = resultSet.getInt("grade");
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find grade by user id and exam name id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return grade;
    }

    @Override
    public Integer findIdByExamNameId(Integer examNameId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_EXAM_NAME_ID);
            statement.setInt(1, examNameId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find exam grade id by exam name id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return id;
    }

    @Override
    public List<ExamGrade> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ExamGrade> examGrades = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                ExamGrade examGrade = new ExamGrade(resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("grade"));
                examGrades.add(examGrade);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of exam grades.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return examGrades;
    }
}
