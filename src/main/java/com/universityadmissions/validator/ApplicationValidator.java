package com.universityadmissions.validator;

import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApplicationValidator {
    private ApplicationValidator() {

    }

    public static Map<String, String> validate(Application application) {
        Map<String, String> errors = new HashMap<>();
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        List<Application> userApplications;
        try {
            userApplications = applicationService.findAllApplicationsByUserId(application.getUser().getId());
            for (Application userApplication : userApplications) {
                if (userApplication.getPriority() == application.getPriority()) {
                    errors.put("APPLICATION_PRIORITY_ERROR", "Ви вже відправили заявку з таким пріорітетом.");
                }
                if (Objects.equals(userApplication.getDepartment().getName(), application.getDepartment().getName())) {
                    errors.put("APPLICATION_DEPARTMENT_ERROR", "Ви вже відправили заявку на даний факультет.");
                }
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return errors;
    }
}
