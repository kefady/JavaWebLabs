package com.universityadmissions.validator;

import com.universityadmissions.entity.Department;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DepartmentValidator {
    private static final String DEPARTMENT_NAME = "[а-яА-ЯіїєґІЇЄҐ\\s]+";

    private DepartmentValidator() {

    }

    public static Map<String, String> validate(Department department) {
        Map<String, String> errors = new HashMap<>();
        if (!Pattern.matches(DEPARTMENT_NAME, department.getName())) {
            errors.put("INVALID_DEPARTMENT_NAME", "Некоректна назва факультету.");
        }
        if (department.getBudgetPlace() >= department.getAllPlace()) {
            errors.put("BUDGET_PlACE_ERROR", "Кількість бюджетних місць має бути меншою за загальну кількість місць.");
        }
        if (department.getFirstExam().getId() == department.getSecondExam().getId() || department.getFirstExam().getId() == department.getThirdExam().getId() || department.getSecondExam().getId() == department.getThirdExam().getId()) {
            errors.put("EXAM_ERROR", "Екзамени мають бути різними.");
        }
        if (department.getFirstExam().getCoefficient() + department.getSecondExam().getCoefficient() + department.getThirdExam().getCoefficient() != 0.95) {
            errors.put("COEFFICIENT_ERROR", "Сума коефіцієнтів екзаменів має бути рівна 0.95");
        }
        return errors;
    }
}
