package com.universityadmissions.dao.mysql;

import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.DepartmentDao;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.Department;
import com.universityadmissions.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlDepartmentDao implements DepartmentDao {
    private static final String SQL_INSERT = "INSERT INTO department (name, budget_place, all_place, first_exam_id, second_exam_id, third_exam_id) VALUES  (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM department WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE department SET name=?, budget_place=?, all_place=?, first_exam_id=?, second_exam_id=?, third_exam_id=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM department WHERE id=?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM department WHERE name=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM department";

    private static volatile MysqlDepartmentDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlDepartmentDao() {

    }

    public static MysqlDepartmentDao getInstance() {
        if (instance == null) {
            synchronized (MysqlDepartmentDao.class) {
                if (instance == null) {
                    instance = new MysqlDepartmentDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Department department) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, department.getName());
            statement.setInt(2, department.getBudgetPlace());
            statement.setInt(3, department.getAllPlace());
            statement.setInt(4, department.getFirstExam().getId());
            statement.setInt(5, department.getSecondExam().getId());
            statement.setInt(6, department.getThirdExam().getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new department.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Department department) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, department.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete department.", e);
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
            throw new DaoException("Failed to delete department.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(Department department) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, department.getName());
            statement.setInt(2, department.getBudgetPlace());
            statement.setInt(3, department.getAllPlace());
            statement.setInt(4, department.getFirstExam().getId());
            statement.setInt(5, department.getSecondExam().getId());
            statement.setInt(6, department.getThirdExam().getId());
            statement.setInt(7, department.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update department.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Department findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Department department = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = new Department(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("budget_place"),
                        resultSet.getInt("all_place"),
                        daoFactory.getExamDao().findById(resultSet.getInt("first_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("second_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("third_exam_id"))
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find department by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return department;
    }

    @Override
    public Department findByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Department department = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                department = new Department(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("budget_place"),
                        resultSet.getInt("all_place"),
                        daoFactory.getExamDao().findById(resultSet.getInt("first_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("second_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("third_exam_id"))
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find department by name.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return department;
    }

    @Override
    public List<Department> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Department> departments = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Department department = new Department(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("budget_place"),
                        resultSet.getInt("all_place"),
                        daoFactory.getExamDao().findById(resultSet.getInt("first_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("second_exam_id")),
                        daoFactory.getExamDao().findById(resultSet.getInt("third_exam_id"))
                );
                departments.add(department);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of departments.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return departments;
    }
}
