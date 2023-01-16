package com.universityadmissions.service.impl;

import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.ExamNameDao;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.ExamName;
import com.universityadmissions.service.ExamNameService;
import com.universityadmissions.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class ExamNameServiceImpl implements ExamNameService {
    private static final Logger logger = Logger.getLogger(ExamNameServiceImpl.class);
    private static volatile ExamNameServiceImpl instance;
    private final ExamNameDao dao;

    private ExamNameServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getExamNameDao();
    }

    public static ExamNameServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (ExamNameServiceImpl.class) {
                if (instance == null) {
                    instance = new ExamNameServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public ExamName findExamNameById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            logger.error("Could not find exam.", e);
            throw new ServiceException("Could not find exam.", e);
        }
    }

    @Override
    public List<ExamName> findAllExamNames() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to get list of exam names.", e);
            throw new ServiceException("Failed to get list of exam names.", e);
        }
    }
}
