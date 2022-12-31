package com.universityadmissions.service;

import com.universityadmissions.entity.Role;

public interface RoleService {
    Role findRoleById(int id) throws ServiceException;
}
