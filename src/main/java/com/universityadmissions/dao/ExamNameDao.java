package com.universityadmissions.dao;

import com.universityadmissions.entity.ExamName;

public interface ExamNameDao extends BaseDao<Integer, ExamName> {
    ExamName findByName(String examName) throws DaoException;
}
