package com.universityadmissions.controller.command;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.*;
import com.universityadmissions.service.*;
import com.universityadmissions.util.FormFinalGrade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendApplicationCommand implements Command {
    private static final Logger logger = Logger.getLogger(SendApplicationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationService applicationService = ServiceFactory.getApplicationService();
        UserService userService = ServiceFactory.getUserService();
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        ExamGradeService examGradeService = ServiceFactory.getExamGradeService();
        CertificateGradeService certificateGradeService = ServiceFactory.getCertificateGradeService();
        ExamNameService examNameService = ServiceFactory.getExamNameService();
        Map<String, String> errors = new HashMap<>();
        try {
            if ((Boolean) request.getSession().getAttribute("isLogin")) {
                int countOfUserApplications = (Integer) request.getSession().getAttribute("countOfUserApplications");
                ArrayList<Integer> listOfApplyDepartment = (ArrayList<Integer>) request.getSession().getAttribute("listOfApplyDepartment");
                int departmentId = Integer.parseInt(request.getParameter("departmentId"));
                if (countOfUserApplications < 5 && !listOfApplyDepartment.contains(departmentId)) {
                    int userId = (Integer) request.getSession().getAttribute("userId");
                    int priority = Integer.parseInt(request.getParameter("priority"));
                    int examGrade1 = Integer.parseInt(request.getParameter("firstExamGrade"));
                    int examGrade2 = Integer.parseInt(request.getParameter("secondExamGrade"));
                    int examGrade3 = Integer.parseInt(request.getParameter("thirdExamGrade"));
                    int certificateGrade1 = Integer.parseInt(request.getParameter("certificateGrade1"));
                    int certificateGrade2 = Integer.parseInt(request.getParameter("certificateGrade2"));
                    int certificateGrade3 = Integer.parseInt(request.getParameter("certificateGrade3"));
                    int certificateGradeExamNameId1 = Integer.parseInt(request.getParameter("certificateGradeExamNameId1"));
                    int certificateGradeExamNameId2 = Integer.parseInt(request.getParameter("certificateGradeExamNameId2"));
                    int certificateGradeExamNameId3 = Integer.parseInt(request.getParameter("certificateGradeExamNameId3"));
                    Date date = new Date(System.currentTimeMillis());
                    boolean isExamGradeAdd = false;
                    boolean isCertificateGradeAdd = false;

                    User user = userService.findUserById(userId);
                    Department department = departmentService.findDepartmentById(departmentId);

                    if (addExamGrade(user, examGrade1, department.getFirstExam().getExamName(), examGradeService) &&
                            addExamGrade(user, examGrade2, department.getSecondExam().getExamName(), examGradeService) &&
                            addExamGrade(user, examGrade3, department.getThirdExam().getExamName(), examGradeService)) {
                        isExamGradeAdd = true;
                    }
                    if (certificateGradeExamNameId1 != certificateGradeExamNameId2 && certificateGradeExamNameId1 != certificateGradeExamNameId3 && certificateGradeExamNameId2 != certificateGradeExamNameId3) {
                        if (addCertificateGrade(user, certificateGrade1, examNameService.findExamNameById(certificateGradeExamNameId1), certificateGradeService) &&
                                addCertificateGrade(user, certificateGrade2, examNameService.findExamNameById(certificateGradeExamNameId2), certificateGradeService) &&
                                addCertificateGrade(user, certificateGrade3, examNameService.findExamNameById(certificateGradeExamNameId3), certificateGradeService)) {
                            isCertificateGradeAdd = true;
                        }
                    } else {
                        errors.put("CERTIFICATE_GRADE_ERROR", "Назви предметів із атестату мають бути різними.");
                    }

                    if (isExamGradeAdd && isCertificateGradeAdd) {
                        Application application = new Application();
                        application.setUser(user);
                        application.setDepartment(department);
                        application.setPriority(priority);
                        application.setDate(date);
                        errors = applicationService.addNewApplication(application);
                    } else {
                        errors.put("CREATE_APPLICATION_ERROR", "Не вдалося відправити заявку. Спробуйте пізніше.");
                    }

                    if (errors.isEmpty()) {
                        Thread thread = new FormFinalGrade(userId);
                        thread.start();
                        listOfApplyDepartment.add(departmentId);
                        request.getSession().setAttribute("listOfApplyDepartment", listOfApplyDepartment);
                        countOfUserApplications++;
                        request.getSession().setAttribute("countOfUserApplications", countOfUserApplications);
                        response.sendRedirect("/admission");
                        logger.info("User '" + request.getSession().getAttribute("username") + "' send new application.");
                    } else {
                        for (HashMap.Entry<String, String> error : errors.entrySet()) {
                            request.setAttribute(error.getKey(), error.getValue());
                        }
                        request.setAttribute("department", department);
                        request.setAttribute("examNames", examNameService.findAllExamNames());
                        request.getRequestDispatcher("apply").forward(request, response);
                        logger.warn("Failed to send application by user '" + request.getSession().getAttribute("username") + "'. Wrong data.");
                    }
                } else {
                    logger.warn("User '" + request.getSession().getAttribute("username") + "' has already applied for department with id '" + departmentId + "', or has max applications.");
                }
            } else {
                logger.warn("Unauthorized user attempted to submit a apply form.");
                response.sendRedirect("login");
            }
        } catch (IOException | ServiceException | ServletException e) {
            logger.error("Failed to send application.", e);
        }
    }

    private boolean addExamGrade(User user, int grade, ExamName examName, ExamGradeService examGradeService) {
        boolean result = false;
        try {
            if (!examGradeService.isUserExamGradeExist(user.getId(), examName.getId())) {
                ExamGrade examGrade = new ExamGrade();
                examGrade.setUser(user);
                examGrade.setGrade(grade);
                examGrade.setExamName(examName);
                result = examGradeService.addNewExamGrade(examGrade);
            } else {
                result = true;
            }
        } catch (ServiceException e) {
            logger.error("Failed to add exam grade. ExamGradeService not work.", e);
        }
        return result;
    }

    private boolean addCertificateGrade(User user, int grade, ExamName examName, CertificateGradeService certificateGradeService) {
        boolean result = false;
        try {
            if (!certificateGradeService.isUserCertificateGradeExist(user.getId(), examName.getId())) {
                CertificateGrade certificateGrade = new CertificateGrade();
                certificateGrade.setUser(user);
                certificateGrade.setGrade(grade);
                certificateGrade.setExamName(examName);
                result = certificateGradeService.addNewCertificateGrade(certificateGrade);
            } else {
                result = true;
            }
        } catch (ServiceException e) {
            logger.error("Failed to add certificate grade. CertificateGradeService not work.", e);
        }
        return result;
    }
}
