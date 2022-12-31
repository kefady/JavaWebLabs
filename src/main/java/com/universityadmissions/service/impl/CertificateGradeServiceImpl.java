package com.universityadmissions.service.impl;

import com.universityadmissions.dao.CertificateGradeDao;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.CertificateGrade;
import com.universityadmissions.service.CertificateGradeService;
import com.universityadmissions.service.ServiceException;

import java.util.List;

public class CertificateGradeServiceImpl implements CertificateGradeService {
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
            return dao.create(certificateGrade);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new certificate grade.", e);
        }
    }

    @Override
    public boolean isUserCertificateGradeExist(int userId, int examNameId) throws ServiceException {
        try {
            return dao.findIdByExamNameIdAndUserId(userId, examNameId) != null;
        } catch (DaoException e) {
            throw new ServiceException("Could not find certificate grade.", e);
        }
    }

    @Override
    public List<Integer> findAllUserCertificateGrades(int userId) throws ServiceException {
        try {
            return dao.findGradesByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get list of certificate grades.", e);
        }
    }
}
