package com.universityadmissions.service.impl;

import com.universityadmissions.dao.CertificateGradeDao;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.CertificateGrade;
import com.universityadmissions.service.CertificateGradeService;
import com.universityadmissions.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class CertificateGradeServiceImpl implements CertificateGradeService {
    private static final Logger logger = Logger.getLogger(CertificateGradeServiceImpl.class);
    private static volatile CertificateGradeServiceImpl instance;
    private final CertificateGradeDao dao;

    private CertificateGradeServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getCertificateGradeDao();
    }

    public static CertificateGradeServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (CertificateGradeServiceImpl.class) {
                if (instance == null) {
                    instance = new CertificateGradeServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean addNewCertificateGrade(CertificateGrade certificateGrade) throws ServiceException {
        try {
            boolean result = dao.create(certificateGrade);
            if (result) {
                logger.info("Added new certificate grade in database.");
            }
            return result;
        } catch (DaoException e) {
            logger.error("Failed to create new certificate grade.", e);
            throw new ServiceException("Failed to create new certificate grade.", e);
        }
    }

    @Override
    public boolean isUserCertificateGradeExist(int userId, int examNameId) throws ServiceException {
        try {
            return dao.findIdByExamNameIdAndUserId(userId, examNameId) != null;
        } catch (DaoException e) {
            logger.error("Could not find certificate grade.", e);
            throw new ServiceException("Could not find certificate grade.", e);
        }
    }

    @Override
    public List<Integer> findAllUserCertificateGrades(int userId) throws ServiceException {
        try {
            return dao.findGradesByUserId(userId);
        } catch (DaoException e) {
            logger.error("Failed to get list of certificate grades.", e);
            throw new ServiceException("Failed to get list of certificate grades.", e);
        }
    }
}
