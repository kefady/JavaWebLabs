package com.universityadmissions.dao;

import com.universityadmissions.entity.ExamGrade;

public interface ExamGradeDao extends BaseDao<Integer, ExamGrade> {
    Integer findGradeByUserIdAndExamNameId(Integer userId, Integer examNameId) throws DaoException;

    Integer findIdByExamNameId(Integer examNameId) throws DaoException;
}
