package com.universityadmissions.service.impl;

import com.universityadmissions.dao.ApplicationDao;
import com.universityadmissions.dao.DaoException;
import com.universityadmissions.dao.DaoFactory;
import com.universityadmissions.db.Databases;
import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.validator.ApplicationValidator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
    private static volatile ApplicationServiceImpl instance;
    private final ApplicationDao dao;

    private ApplicationServiceImpl(Databases database) {
        dao = DaoFactory.getDaoFactory(database).getApplicationDao();
    }

    public static ApplicationServiceImpl getInstance(Databases database) {
        if (instance == null) {
            synchronized (ApplicationServiceImpl.class) {
                if (instance == null) {
                    instance = new ApplicationServiceImpl(database);
                }
            }
        }
        return instance;
    }

    @Override
    public Map<String, String> addNewApplication(Application application) throws ServiceException {
        Map<String, String> errors;
        boolean isApplicationCreate;
        try {
            errors = ApplicationValidator.validate(application);
            if (errors.isEmpty()) {
                isApplicationCreate = dao.create(application);
            } else {
                return errors;
            }
        } catch (DaoException e) {
            logger.error("Failed to add new application.", e);
            throw new ServiceException("Failed to add new application.", e);
        }
        if (!isApplicationCreate) {
            errors.put("APPLICATION_ERROR", "Не вдалося відправити заявку. Спробуйте пізніше.");
        } else {
            logger.info("Added new application in database.");
        }
        return errors;
    }

    @Override
    public List<Application> findAllApplications() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            logger.error("Failed to get list of applications.", e);
            throw new ServiceException("Failed to get list of applications.", e);
        }
    }

    @Override
    public List<Application> findAllApplicationsByUserId(int userId) throws ServiceException {
        try {
            return dao.findAllByUserId(userId);
        } catch (DaoException e) {
            logger.error("Failed to get list of applications.", e);
            throw new ServiceException("Failed to get list of applications.", e);
        }
    }

    @Override
    public List<Application> findAllForElection(int departmentId, int priority) throws ServiceException {
        try {
            List<Application> applications = dao.findAllByDepartmentIdAndPriority(departmentId, priority);
            applications.removeIf(application -> !application.isVerified());
            applications.removeIf(Application::isAccepted);
            return applications;
        } catch (DaoException e) {
            logger.error("Failed to get list of applications.", e);
            throw new ServiceException("Failed to get list of applications.", e);
        }
    }

    @Override
    public Application findApplicationById(int id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException e) {
            logger.error("Failed to find application by id.", e);
            throw new ServiceException("Failed to find application by id.", e);
        }
    }

    @Override
    public Integer getAmountOfUserApplications(int userId) throws ServiceException {
        try {
            return dao.findAllByUserId(userId).size();
        } catch (DaoException e) {
            logger.error("Failed to get amount of user applications.", e);
            throw new ServiceException("Failed to get amount of user applications.", e);
        }
    }

    @Override
    public boolean deleteApplicationById(int id) throws ServiceException {
        try {
            return dao.delete(id);
        } catch (DaoException e) {
            logger.error("Failed to delete application.", e);
            throw new ServiceException("Failed to delete application.", e);
        }
    }

    @Override
    public boolean setVerify(int applicationId, boolean verify) throws ServiceException {
        try {
            return dao.setVerify(applicationId, verify);
        } catch (DaoException e) {
            logger.error("Failed to set application verify.", e);
            throw new ServiceException("Failed to set application verify.", e);
        }
    }

    @Override
    public void setAccept(int applicationId, boolean accept) throws ServiceException {
        try {
            dao.setAccept(applicationId, accept);
        } catch (DaoException e) {
            logger.error("Failed to set application accept.", e);
            throw new ServiceException("Failed to set application accept.", e);
        }
    }

    @Override
    public void addFinalGrade(int applicationId, int finalGrade) throws ServiceException {
        try {
            dao.addFinalGrade(applicationId, finalGrade);
        } catch (DaoException e) {
            logger.error("Failed to add final grade to application.", e);
            throw new ServiceException("Failed to add final grade to application.", e);
        }
    }
}
