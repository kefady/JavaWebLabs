package com.universityadmissions.service;

import com.universityadmissions.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department findDepartmentById(int id) throws ServiceException;

    boolean deleteDepartmentById(int id) throws ServiceException;

    boolean isDepartmentExist(String name) throws ServiceException;

    Map<String, String> addNewDepartment(Department department) throws ServiceException;

    Map<String, String> updateDepartment(Department department) throws ServiceException;

    List<Department> findAllDepartments() throws ServiceException;
}
