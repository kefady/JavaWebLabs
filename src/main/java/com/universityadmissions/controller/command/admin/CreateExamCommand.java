package com.universityadmissions.controller.command.admin;

import com.universityadmissions.controller.Command;
import com.universityadmissions.entity.Exam;
import com.universityadmissions.entity.ExamName;
import com.universityadmissions.service.ExamNameService;
import com.universityadmissions.service.ExamService;
import com.universityadmissions.service.ServiceException;
import com.universityadmissions.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateExamCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            ExamService examService = ServiceFactory.getExamService();
            ExamNameService examNameService = ServiceFactory.getExamNameService();

            int examNameId = Integer.parseInt(request.getParameter("examNameId"));
            int minGrade = Integer.parseInt(request.getParameter("examMinGrade"));
            double coefficient = Double.parseDouble(request.getParameter("examCoefficient"));
            ExamName examName = examNameService.findExamNameById(examNameId);

            Exam exam = new Exam();
            exam.setExamName(examName);
            exam.setMinGrade(minGrade);
            exam.setCoefficient(coefficient);

            boolean isExamCreated = examService.addNewExam(exam);

            if (!isExamCreated) {
                request.setAttribute("CREATE_EXAM_ERROR", "Не вдалося створити екзамен. Спробуйте пізніше.");
            }

            ConsoleCommand consoleCommand = new ConsoleCommand();
            consoleCommand.execute(request, response);
        } catch (ServiceException e) {
            Logger.getLogger(CreateExamCommand.class.getName()).log(Level.WARNING, "Failed to create new exam.", e);
        }
    }
}
