package com.universityadmissions.service;

import com.universityadmissions.entity.Application;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    Map<String, String> addNewApplication(Application application) throws ServiceException;

    List<Application> findAllApplications() throws ServiceException;

    List<Application> findAllApplicationsByUserId(int userId) throws ServiceException;

    List<Application> findAllForElection(int departmentId, int priority) throws ServiceException;

    Application findApplicationById(int id) throws ServiceException;

    Integer getAmountOfUserApplications(int userId) throws ServiceException;

    boolean deleteApplicationById(int id) throws ServiceException;

    boolean setVerify(int applicationId, boolean verify) throws ServiceException;

    void setAccept(int applicationId, boolean accept) throws ServiceException;

    void addFinalGrade(int applicationId, int finalGrade) throws ServiceException;
}
