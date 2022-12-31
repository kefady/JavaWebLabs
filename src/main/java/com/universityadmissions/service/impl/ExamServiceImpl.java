package com.universityadmissions.service.impl;

import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.ExamDao;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.Exam;
import com.universityadmissions.service.ExamService;
import com.universityadmissions.service.ServiceException;

import java.util.List;

public class ExamServiceImpl implements ExamService {
    private static volatile ExamServiceImpl instance;
    private final ExamDao dao;

    private ExamServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getExamDao();
    }

    public static ExamServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (ExamServiceImpl.class) {
                if (instance == null) {
                    instance = new ExamServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public Exam findExamById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Could not find exam.", e);
        }
    }

    @Override
    public boolean addNewExam(Exam exam) throws ServiceException {
        try {
             return dao.create(exam);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new exam.", e);
        }
    }

    @Override
    public List<Exam> findAllExams() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get list of exams.", e);
        }
    }
}
