package com.universityadmissions.service;

import com.universityadmissions.db.Databases;
import com.universityadmissions.service.impl.*;

public class ServiceFactory {
    private static final Databases database = Databases.MYSQL;

    private ServiceFactory() {

    }

    public static RoleService getRoleService() {
        return RoleServiceImpl.getInstance(database);
    }

    public static UserService getUserService() {
        return UserServiceImpl.getInstance(database);
    }

    public static DepartmentService getDepartmentService() {
        return DepartmentServiceImpl.getInstance(database);
    }

    public static ApplicationService getApplicationService() {
        return ApplicationServiceImpl.getInstance(database);
    }

    public static ExamService getExamService() {
        return ExamServiceImpl.getInstance(database);
    }

    public static ExamGradeService getExamGradeService() {
        return ExamGradeServiceImpl.getInstance(database);
    }

    public static ExamNameService getExamNameService() {
        return ExamNameServiceImpl.getInstance(database);
    }

    public static CertificateGradeService getCertificateGradeService() {
        return CertificateGradeServiceImpl.getInstance(database);
    }
}
