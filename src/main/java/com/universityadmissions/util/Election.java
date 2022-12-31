package com.universityadmissions.util;

import com.universityadmissions.entity.Department;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Election {
    private Election() {

    }

    public static void start() {
        try {
            DepartmentService departmentService = ServiceFactory.getDepartmentService();
            List<Department> departments = departmentService.findAllDepartments();
            CyclicBarrier cyclicBarrier = new CyclicBarrier(departments.size());

            for (int i = 1; i <= 5; i++) {
                for (Department department : departments) {
                    new DepartmentElection(department.getId(), i, cyclicBarrier).start();
                    Thread.sleep(100);
                }
            }
        } catch (ServiceException | InterruptedException e) {
            Logger.getLogger(Election.class.getName()).log(Level.WARNING, "Election error.", e);
        }

    }
}
