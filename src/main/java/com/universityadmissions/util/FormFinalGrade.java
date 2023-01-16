package com.universityadmissions.util;

import com.universityadmissions.entity.Application;
import com.universityadmissions.service.*;
import org.apache.log4j.Logger;

import java.util.List;

public class FormFinalGrade extends Thread {
    private static final Logger logger = Logger.getLogger(FormFinalGrade.class);
    private final int userId;

    public FormFinalGrade(int userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            ApplicationService applicationService = ServiceFactory.getApplicationService();
            ExamGradeService examGradeService = ServiceFactory.getExamGradeService();
            CertificateGradeService certificateGradeService = ServiceFactory.getCertificateGradeService();

            List<Application> applications = applicationService.findAllApplicationsByUserId(userId);
            List<Integer> certificateGrades = certificateGradeService.findAllUserCertificateGrades(userId);

            for (Application application : applications) {
                if (application.getFinalGrade() == 0) {
                    int firstExamNameId = application.getDepartment().getFirstExam().getExamName().getId();
                    double firstExamCoefficient = application.getDepartment().getFirstExam().getCoefficient();
                    int secondExamNameId = application.getDepartment().getSecondExam().getExamName().getId();
                    double secondExamCoefficient = application.getDepartment().getSecondExam().getCoefficient();
                    int thirdExamNameId = application.getDepartment().getThirdExam().getExamName().getId();
                    double thirdExamCoefficient = application.getDepartment().getThirdExam().getCoefficient();

                    int firsGrade = examGradeService.findGradeByUserIdAndExamNameId(userId, firstExamNameId);
                    int secondGrade = examGradeService.findGradeByUserIdAndExamNameId(userId, secondExamNameId);
                    int thirdGrade = examGradeService.findGradeByUserIdAndExamNameId(userId, thirdExamNameId);

                    double certificateGradeSum = 0;
                    for (Integer certificateGrade : certificateGrades) {
                        certificateGradeSum += certificateGrade;
                    }
                    double certificateGPA = ((certificateGradeSum / 3) * 200) / 12;

                    double finalGrade = firsGrade * firstExamCoefficient + secondGrade * secondExamCoefficient + thirdGrade * thirdExamCoefficient + certificateGPA * 0.05;

                    application.setFinalGrade((int) Math.round(finalGrade));
                    applicationService.addFinalGrade(application.getId(), application.getFinalGrade());
                    logger.info("Formed final grade for application with id '" + application.getId() + "' of user '" + application.getUser().getUsername() + "'.");
                }
            }
        } catch (ServiceException e) {
            logger.error("Filed to form final grade for user with id '" + userId + "'. Services not work.", e);
        }
    }
}
