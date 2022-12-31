package com.universityadmissions.dao.mysql;

import com.universityadmissions.dao.CertificateGradeDao;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.CertificateGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlCertificateGradeDao implements CertificateGradeDao {
    private static final String SQL_INSERT = "INSERT INTO certificate_grade (user_id, exam_name_id, grade) VALUES  (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM certificate_grade WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE certificate_grade SET user_id=?, exam_name_id=?, grade=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM certificate_grade WHERE id=?";
    private static final String SQL_SELECT_BY_EXAM_NAME_ID_AND_USER_ID = "SELECT id FROM certificate_grade WHERE user_id=? AND exam_name_id=?";
    private static final String SQL_SELECT_GRADES_BY_USER_ID = "SELECT grade FROM certificate_grade WHERE user_id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM certificate_grade";

    private static volatile MysqlCertificateGradeDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlCertificateGradeDao() {

    }

    public static MysqlCertificateGradeDao getInstance() {
        if (instance == null) {
            synchronized (MysqlCertificateGradeDao.class) {
                if (instance == null) {
                    instance = new MysqlCertificateGradeDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(CertificateGrade certificateGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, certificateGrade.getUser().getId());
            statement.setInt(2, certificateGrade.getExamName().getId());
            statement.setInt(3, certificateGrade.getGrade());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new certificate grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(CertificateGrade certificateGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, certificateGrade.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete certificate grade.", e);
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
            throw new DaoException("Failed to delete certificate grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(CertificateGrade certificateGrade) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, certificateGrade.getUser().getId());
            statement.setInt(2, certificateGrade.getExamName().getId());
            statement.setInt(3, certificateGrade.getGrade());
            statement.setInt(4, certificateGrade.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update certificate grade.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public CertificateGrade findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        CertificateGrade certificateGrade = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                certificateGrade = new CertificateGrade(resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("grade"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find certificate grade by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return certificateGrade;
    }

    @Override
    public List<Integer> findGradesByUserId(Integer userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> grades = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_GRADES_BY_USER_ID);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer grade = resultSet.getInt("grade");
                grades.add(grade);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of certificate grades.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return grades;
    }

    @Override
    public Integer findIdByExamNameIdAndUserId(Integer userId, Integer examNameId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_EXAM_NAME_ID_AND_USER_ID);
            statement.setInt(1, userId);
            statement.setInt(2, examNameId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find certificate grade id by exam name id and user id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return id;
    }

    @Override
    public List<CertificateGrade> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<CertificateGrade> certificateGrades = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                CertificateGrade certificateGrade = new CertificateGrade(resultSet.getInt("id"),
                        daoFactory.getUserDao().findById(resultSet.getInt("user_id")),
                        daoFactory.getExamNameDao().findById(resultSet.getInt("exam_name_id")),
                        resultSet.getInt("grade"));
                certificateGrades.add(certificateGrade);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of certificate grades.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return certificateGrades;
    }
}
