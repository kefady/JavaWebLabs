package com.universityadmissions.service;

import com.universityadmissions.entity.CertificateGrade;

import java.util.List;

public interface CertificateGradeService {
    boolean addNewCertificateGrade(CertificateGrade certificateGrade) throws ServiceException;

    boolean isUserCertificateGradeExist(int userId, int examNameId) throws ServiceException;

    List<Integer> findAllUserCertificateGrades(int userId) throws ServiceException;
}
