package com.universityadmissions.service;

import com.universityadmissions.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> addNewUser(User user) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    boolean isUsernameExist(String username) throws ServiceException;

    boolean isEmailExist(String email) throws ServiceException;

    void setBlockStatus(int userId, boolean status) throws ServiceException;

    User findUserByUsername(String username) throws ServiceException;

    User findUserById(int id) throws ServiceException;
}
