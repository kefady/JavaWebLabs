package com.universityadmissions.service.impl;

import com.universityadmissions.db.Databases;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.dao.UserDao;
import com.universityadmissions.entity.User;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.UserService;
import com.universityadmissions.validator.UserValidator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static volatile UserServiceImpl instance;
    private final UserDao dao;

    private UserServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getUserDao();
    }

    public static UserServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (RoleServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public Map<String, String> addNewUser(User user) throws ServiceException {
        Map<String, String> errors = new HashMap<>();
        boolean isUserCreate = false;
        try {
            if (isUsernameExist(user.getUsername())) {
                errors.put("USERNAME_IS_EXIST", "Даний нікнейм вже зареєстровано.");
                return errors;
            }
            if (isEmailExist(user.getEmail())) {
                errors.put("EMAIL_IS_EXIST", "Дана електрона адреса вже зареєстрована.");
                return errors;
            }
            errors = UserValidator.validate(user);
            if (errors.isEmpty()) {
                isUserCreate = dao.create(user);
            }
        } catch (DaoException e) {
            logger.error("Failed to create new user.", e);
            throw new ServiceException("Failed to create new user.", e);
        }
        if (!isUserCreate) {
            errors.put("CREATE_USER_ERROR", "Виникла помилка при реєстрації, спробуйте пізніше.");
        } else {
            logger.info("Added new user in database.");
        }
        return errors;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to get list of users.", e);
            throw new ServiceException("Failed to get list of users.", e);
        }
    }

    @Override
    public boolean isUsernameExist(String username) throws ServiceException {
        try {
            return dao.findByUsername(username) != null;
        } catch (DaoException e) {
            logger.error("Can not find username.", e);
            throw new ServiceException("Can not find username.", e);
        }
    }

    @Override
    public boolean isEmailExist(String email) throws ServiceException {
        try {
            return dao.findByEmail(email) != null;
        } catch (DaoException e) {
            logger.error("Can not find email.", e);
            throw new ServiceException("Can not find email.", e);
        }
    }

    @Override
    public void setBlockStatus(int userId, boolean status) throws ServiceException {
        try {
            dao.setBlocked(userId, status);
        } catch (DaoException e) {
            logger.error("Failed to set user blocked status.", e);
            throw new ServiceException("Failed to set user blocked status.", e);
        }
    }

    @Override
    public User findUserByUsername(String username) throws ServiceException {
        try {
            return dao.findByUsername(username);
        } catch (DaoException e) {
            logger.error("Can not find user.", e);
            throw new ServiceException("Can not find user.", e);
        }
    }

    @Override
    public User findUserById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            logger.error("Can not find user.", e);
            throw new ServiceException("Can not find user.", e);
        }
    }
}
