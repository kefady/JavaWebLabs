package com.universityadmissions.dao;

import com.universityadmissions.entity.Department;

public interface DepartmentDao extends BaseDao<Integer, Department> {
    Department findByName(String name) throws DaoException;
}
