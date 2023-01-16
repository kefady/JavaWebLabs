package com.universityadmissions.dao.mysql;

import com.universityadmissions.dao.*;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDaoFactory extends DaoFactory {
    private static final String DATASOURCE_NAME = "jdbc/UACP_MYSQL";
    private static final String URL = "jdbc:mysql://localhost:3306/university_admissions";
    private static final String USER = "root";
    private static final String PASSWORD = "3570";
    private static final Logger logger = Logger.getLogger(MysqlDaoFactory.class);
    private static volatile MysqlDaoFactory instance;
    private static DataSource dataSource = null;
    private static boolean connectionPoolWork = false;

    private MysqlDaoFactory() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
            connectionPoolWork = true;
            logger.info("Created connection pool.");
        } catch (NamingException e) {
            connectionPoolWork = false;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                logger.info("DriverManager is loaded.");
            } catch (ClassNotFoundException ex) {
                logger.error("Connection error.", ex);
            }
            logger.error("Connection pool not work.", e);
        }
    }

    public static MysqlDaoFactory getInstance() {
        if (instance == null) {
            synchronized (MysqlDaoFactory.class) {
                if (instance == null) {
                    instance = new MysqlDaoFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public RoleDao getRoleDao() {
        return MysqlRoleDao.getInstance();
    }

    @Override
    public UserDao getUserDao() {
        return MysqlUserDao.getInstance();
    }

    @Override
    public DepartmentDao getDepartmentDao() {
        return MysqlDepartmentDao.getInstance();
    }

    @Override
    public ApplicationDao getApplicationDao() {
        return MysqlApplicationDao.getInstance();
    }

    @Override
    public ExamDao getExamDao() {
        return MysqlExamDao.getInstance();
    }

    @Override
    public ExamNameDao getExamNameDao() {
        return MysqlExamNameDao.getInstance();
    }

    @Override
    public ExamGradeDao getExamGradeDao() {
        return MysqlExamGradeDao.getInstance();
    }

    @Override
    public CertificateGradeDao getCertificateGradeDao() {
        return MysqlCertificateGradeDao.getInstance();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPoolWork) {
            return dataSource.getConnection();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
