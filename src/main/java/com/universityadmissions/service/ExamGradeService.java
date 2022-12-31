package com.universityadmissions.service;

import com.universityadmissions.entity.ExamGrade;

public interface ExamGradeService {
    boolean addNewExamGrade(ExamGrade examGrade) throws ServiceException;

    boolean isUserExamGradeExist(int userId, int examNameId) throws ServiceException;

    Integer findGradeByUserIdAndExamNameId(int userId, int examNameId) throws ServiceException;
}
