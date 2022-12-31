package com.universityadmissions.service;

import com.universityadmissions.entity.Exam;

import java.util.List;

public interface ExamService {
    Exam findExamById(int id) throws ServiceException;

    boolean addNewExam(Exam exam) throws ServiceException;

    List<Exam> findAllExams() throws ServiceException;
}
