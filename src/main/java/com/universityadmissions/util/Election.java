package com.universityadmissions.util;

import com.universityadmissions.entity.Department;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Election {
    private static final Logger logger = Logger.getLogger(Election.class);

    private Election() {

    }

    public static void start() {
        logger.info("Election is started.");
        try {
            DepartmentService departmentService = ServiceFactory.getDepartmentService();
            List<Department> departments = departmentService.findAllDepartments();
            CyclicBarrier cyclicBarrier = new CyclicBarrier(departments.size());

            for (int i = 1; i <= 5; i++) {
                logger.info("Election for priority '" + i + "' is started.");
                for (Department department : departments) {
                    new DepartmentElection(department.getId(), i, cyclicBarrier).start();
                    Thread.sleep(100);
                }
            }
        } catch (ServiceException | InterruptedException e) {
            logger.error("Failed to start elections. Services not work.", e);
        }
        logger.info("Election is finished.");
    }
}
