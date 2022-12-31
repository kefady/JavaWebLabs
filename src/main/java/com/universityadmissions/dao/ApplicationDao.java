package com.universityadmissions.dao;

import com.universityadmissions.entity.Application;

import java.util.List;

public interface ApplicationDao extends BaseDao<Integer, Application> {
    void addFinalGrade(Integer id, Integer finalGrade) throws DaoException;

    boolean setVerify(Integer id, Boolean verify) throws DaoException;

    void setAccept(Integer id, Boolean accept) throws DaoException;

    List<Application> findAllByUserId(Integer userId) throws DaoException;

    List<Application> findAllByDepartmentIdAndPriority(Integer departmentId, Integer priority) throws DaoException;
}
