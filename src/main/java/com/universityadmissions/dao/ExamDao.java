package com.universityadmissions.dao;

import com.universityadmissions.entity.Exam;

public interface ExamDao extends BaseDao<Integer, Exam> {
    Exam findByExamName(String name) throws DaoException;
}
