package com.universityadmissions.util;

import com.universityadmissions.entity.Application;
import com.universityadmissions.service.ApplicationService;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentElection extends Thread {
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
            }
            cyclicBarrier.await();
        } catch (ServiceException | InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(DepartmentElection.class.getName()).log(Level.WARNING, "Department election error", e);
        }
    }
}
