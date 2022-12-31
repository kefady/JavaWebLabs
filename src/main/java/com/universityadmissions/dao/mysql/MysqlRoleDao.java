package com.universityadmissions.dao.mysql;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.RoleDao;
import com.universityadmissions.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlRoleDao implements RoleDao {
    private static final String SQL_INSERT = "INSERT INTO role (role) VALUES  (?)";
    private static final String SQL_DELETE = "DELETE FROM role WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE role SET role=? WHERE id=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM role WHERE id=?";
    private static final String SQL_SELECT_BY_ROLE = "SELECT * FROM role WHERE role=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM role";

    private static volatile MysqlRoleDao instance = null;
    private static final DaoFactory daoFactory;

    static {
        daoFactory = DaoFactory.getDaoFactory(Databases.MYSQL);
    }

    private MysqlRoleDao() {

    }

    public static MysqlRoleDao getInstance() {
        if (instance == null) {
            synchronized (MysqlRoleDao.class) {
                if (instance == null) {
                    instance = new MysqlRoleDao();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Role role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, role.getName());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create new role.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Role role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, role.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete role.", e);
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
            throw new DaoException("Failed to delete role.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(Role role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to update role.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Role findById(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = new Role(resultSet.getInt("id"), resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find role by id.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return role;
    }

    @Override
    public Role findByRole(String roleName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ROLE);
            statement.setString(1, roleName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = new Role(resultSet.getInt("id"), resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find role by name.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return role;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Role> roles = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("role"));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get list of roles.", e);
        } finally {
            close(statement);
            close(resultSet);
            close(connection);
        }
        return roles;
    }
}
