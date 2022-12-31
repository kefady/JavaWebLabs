package com.universityadmissions.service.impl;

import com.universityadmissions.dao.*;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.ExamGrade;
import com.universityadmissions.service.ExamGradeService;
import com.universityadmissions.service.ServiceException;

public class ExamGradeServiceImpl implements ExamGradeService {
    private static volatile ExamGradeServiceImpl instance;
    private final ExamGradeDao dao;

    private ExamGradeServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getExamGradeDao();
    }

    public static ExamGradeServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (ExamGradeServiceImpl.class) {
                if (instance == null) {
                    instance = new ExamGradeServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean addNewExamGrade(ExamGrade examGrade) throws ServiceException {
        try {
            return dao.create(examGrade);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new exam grade.", e);
        }
    }

    @Override
    public boolean isUserExamGradeExist(int userId, int examNameId) throws ServiceException {
        try {
            return dao.findGradeByUserIdAndExamNameId(userId, examNameId) != null;
        } catch (DaoException e) {
            throw new ServiceException("Could not find exam grade.", e);
        }
    }

    @Override
    public Integer findGradeByUserIdAndExamNameId(int userId, int examNameId) throws ServiceException {
        try {
            return dao.findGradeByUserIdAndExamNameId(userId, examNameId);
        } catch (DaoException e) {
            throw new ServiceException("Could not find exam grade.", e);
        }
    }
}
