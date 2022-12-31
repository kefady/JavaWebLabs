package com.universityadmissions.service.impl;

import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.DepartmentDao;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.Department;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.validator.DepartmentValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentServiceImpl implements DepartmentService {
    private static volatile DepartmentServiceImpl instance;
    private final DepartmentDao dao;

    private DepartmentServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getDepartmentDao();
    }

    public static DepartmentServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (DepartmentServiceImpl.class) {
                if (instance == null) {
                    instance = new DepartmentServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public Department findDepartmentById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Could not find department by id.", e);
        }
    }

    @Override
    public boolean deleteDepartmentById(int id) throws ServiceException {
        try {
            return dao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete department.", e);
        }
    }

    @Override
    public boolean isDepartmentExist(String name) throws ServiceException {
        try {
            return dao.findByName(name) != null;
        } catch (DaoException e) {
            throw new ServiceException("Failed to find department.", e);
        }
    }

    @Override
    public Map<String, String> addNewDepartment(Department department) throws ServiceException {
        Map<String, String> errors = new HashMap<>();
        boolean isDepartmentCreate;
        try {
            if (isDepartmentExist(department.getName())) {
                errors.put("DEPARTMENT_EXIST", "Факультет з таким іменем вже існує");
                return errors;
            }
            errors = DepartmentValidator.validate(department);
            if (errors.isEmpty()) {
                isDepartmentCreate = dao.create(department);
            } else {
                return errors;
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to create department.", e);
        }
        if(!isDepartmentCreate) {
            errors.put("CREATE_DEPARTMENT_FAILED", "Не вдалось створити факультет. Спробуйте пізніше.");
        }
        return errors;
    }

    @Override
    public Map<String, String> updateDepartment(Department department) throws ServiceException {
        Map<String, String> errors;
        boolean isDepartmentUpdate;
        try {
            errors = DepartmentValidator.validate(department);
            if (errors.isEmpty()) {
                isDepartmentUpdate = dao.update(department);
            } else {
                return errors;
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to create department.", e);
        }
        if(!isDepartmentUpdate) {
            errors.put("UPDATE_DEPARTMENT_FAILED", "Не вдалось обновити факультет. Спробуйте пізніше.");
        }
        return errors;
    }

    @Override
    public List<Department> findAllDepartments() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get list of departments.", e);
        }
    }
}
