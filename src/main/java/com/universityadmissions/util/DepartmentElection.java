package com.universityadmissions.util;

import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DepartmentElection extends Thread {
    private static final Logger logger = Logger.getLogger(DepartmentElection.class);
    private final int departmentId;
    private final int priority;
    private final CyclicBarrier cyclicBarrier;

    public DepartmentElection(int departmentId, int priority, CyclicBarrier cyclicBarrier) {
        this.departmentId = departmentId;
        this.priority = priority;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            ApplicationService applicationService = ServiceFactory.getApplicationService();
            DepartmentService departmentService = ServiceFactory.getDepartmentService();

            List<Application> applications = applicationService.findAllForElection(departmentId, priority);
            int maxPlaces = departmentService.findDepartmentById(departmentId).getAllPlace();

            if (!applications.isEmpty()) {
                applications.sort(Comparator.comparing(Application::getFinalGrade).reversed());
                for (int i = 0; i < applications.size(); i++) {
                    if (i < maxPlaces) {
                        applicationService.setAccept(applications.get(i).getId(), true);
                        List<Application> userApplications = applicationService.findAllApplicationsByUserId(applications.get(i).getUser().getId());
                        for (Application userApplication : userApplications) {
                            applicationService.setVerify(userApplication.getId(), false);
                        }
                    } else {
                        applicationService.setAccept(applications.get(i).getId(), false);
                    }
                }
            } else {
                logger.warn("No applications for department '" + departmentService.findDepartmentById(departmentId).getName() + "'.");
            }
            cyclicBarrier.await();
        } catch (ServiceException | InterruptedException | BrokenBarrierException e) {
            logger.error("Election for department with id '" + departmentId + "' failed. Services not work.", e);
        }
        logger.info("Election for department with id '" + departmentId + "' is finished.");
    }
}
