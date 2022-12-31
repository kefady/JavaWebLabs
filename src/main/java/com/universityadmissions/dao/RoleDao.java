package com.universityadmissions.dao;

import com.universityadmissions.entity.Role;

public interface RoleDao extends BaseDao<Integer, Role> {
    Role findByRole(String roleName) throws DaoException;
}
