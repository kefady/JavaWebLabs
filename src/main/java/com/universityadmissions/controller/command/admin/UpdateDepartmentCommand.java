package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.Department;
import com.universityadmissions.entity.Exam;
import com.universityadmissions.service.DepartmentService;
import com.universityadmissions.service.ExamService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UpdateDepartmentCommand implements Command {
    private static final Logger logger = Logger.getLogger(UpdateDepartmentCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            DepartmentService departmentService = ServiceFactory.getDepartmentService();
            ExamService examService = ServiceFactory.getExamService();

            int departmentId = Integer.parseInt(request.getParameter("departmentId"));
            String departmentName = request.getParameter("departmentName");
            int departmentBudgetPlace = Integer.parseInt(request.getParameter("departmentBudgetPlace"));
            int departmentAllPlace = Integer.parseInt(request.getParameter("departmentAllPlace"));
            int departmentFirstExamId = Integer.parseInt(request.getParameter("departmentFirstExamId"));
            int departmentSecondExamId = Integer.parseInt(request.getParameter("departmentSecondExamId"));
            int departmentThirdExamId = Integer.parseInt(request.getParameter("departmentThirdExamId"));

            Exam firstExam = null;
            Exam secondExam = null;
            Exam thirdExam = null;
            try {
                firstExam = examService.findExamById(departmentFirstExamId);
                secondExam = examService.findExamById(departmentSecondExamId);
                thirdExam = examService.findExamById(departmentThirdExamId);
            } catch (ServiceException e) {
                // Log
            }

            Map<String, String> errors = new HashMap<>();

            if (firstExam == null) {
                errors.put("FIRST_EXAM_ERROR", "Екзамен №1: невірний ID.");
            }
            if (secondExam == null) {
                errors.put("SECOND_EXAM_ERROR", "Екзамен №2: невірний ID.");
            }
            if (thirdExam == null) {
                errors.put("THIRD_EXAM_ERROR", "Екзамен №3: невірний ID.");
            }

            if (firstExam == null || secondExam == null || thirdExam == null) {
                logger.error("Invalid exam id(s) provided while trying to update a department.");
            }

            if (errors.isEmpty()) {
                Department department = new Department();
                department.setId(departmentId);
                department.setName(departmentName);
                department.setBudgetPlace(departmentBudgetPlace);
                department.setAllPlace(departmentAllPlace);
                department.setFirstExam(firstExam);
                department.setSecondExam(secondExam);
                department.setThirdExam(thirdExam);
                errors.putAll(departmentService.updateDepartment(department));
            }

            if (!errors.isEmpty()) {
                for (HashMap.Entry<String, String> error : errors.entrySet()) {
                    request.setAttribute(error.getKey(), error.getValue());
                }
                logger.error("Error occurred while trying to update a department: " + departmentName);
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            logger.error("An error occurred while trying to update a department: " + e);
        }
    }
}
