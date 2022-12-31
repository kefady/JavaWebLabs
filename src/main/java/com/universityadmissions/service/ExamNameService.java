package com.universityadmissions.service;

import com.universityadmissions.entity.ExamName;

import java.util.List;

public interface ExamNameService {
    ExamName findExamNameById(int id) throws ServiceException;

    List<ExamName> findAllExamNames() throws ServiceException;
}
