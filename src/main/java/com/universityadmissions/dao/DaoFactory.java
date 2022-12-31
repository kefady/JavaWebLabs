package com.universityadmissions.dao;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.mysql.MysqlDaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoFactory {
    public abstract RoleDao getRoleDao();

    public abstract UserDao getUserDao();

    public abstract DepartmentDao getDepartmentDao();

    public abstract ApplicationDao getApplicationDao();

    public abstract ExamDao getExamDao();

    public abstract ExamNameDao getExamNameDao();

    public abstract ExamGradeDao getExamGradeDao();

    public abstract CertificateGradeDao getCertificateGradeDao();

    public abstract Connection getConnection() throws SQLException;

    public static DaoFactory getDaoFactory(Databases factory) {
        return switch (factory) {
            case MYSQL -> MysqlDaoFactory.getInstance();
        };
    }
}
