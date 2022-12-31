package com.universityadmissions.util;

import com.universityadmissions.entity.Application;
import com.universityadmissions.service.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormFinalGrade extends Thread {
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
                }
            }
        } catch (ServiceException e) {
            Logger.getLogger(FormFinalGrade.class.getName()).log(Level.WARNING, "Final grade form error.", e);
        }
    }
}
