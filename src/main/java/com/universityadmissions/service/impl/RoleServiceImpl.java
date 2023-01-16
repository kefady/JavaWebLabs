package com.universityadmissions.service.impl;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.RoleDao;
import com.universityadmissions.entity.Role;
import com.universityadmissions.service.RoleService;
import com.universityadmissions.service.ServiceException;
import org.apache.log4j.Logger;

public class RoleServiceImpl implements RoleService {
    private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);
    private static volatile RoleServiceImpl instance;
    private final RoleDao dao;

    private RoleServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getRoleDao();
    }

    public static RoleServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (RoleServiceImpl.class) {
                if (instance == null) {
                    instance = new RoleServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public Role findRoleById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            logger.error("Could not find role.", e);
            throw new ServiceException("Could not find role.", e);
        }
    }
}
