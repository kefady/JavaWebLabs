package com.universityadmissions.dao;

import com.universityadmissions.entity.CertificateGrade;

import java.util.List;

public interface CertificateGradeDao extends BaseDao<Integer, CertificateGrade> {
    List<Integer> findGradesByUserId(Integer userId) throws DaoException;

    Integer findIdByExamNameIdAndUserId(Integer userId, Integer examNameId) throws DaoException;
}
