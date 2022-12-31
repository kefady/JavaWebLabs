package com.universityadmissions.validator;

import com.universityadmissions.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String NAME = "[а-яА-ЯіїєґІЇЄҐ'\\s]{2,}";
    private static final String CITY = "[а-яА-ЯіїєґІЇЄҐ'\\-\\s]{2,}";

    private UserValidator() {

    }

    public static Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();
        if (!Pattern.matches(NAME, user.getSurname())) {
            errors.put("INVALID_SURNAME", "Ви ввели некоректне прізвище.");
        }
        if (!Pattern.matches(NAME, user.getName())) {
            errors.put("INVALID_NAME", "Ви ввели некоректне ім'я.");
        }
        if (!Pattern.matches(NAME, user.getPatronymic())) {
            errors.put("INVALID_PATRONYMIC", "Ви ввели некоректне по-батькові.");
        }
        if (!Pattern.matches(CITY, user.getCity())) {
            errors.put("INVALID_CITY", "Ви ввели некоректне місто.");
        }
        if (!Pattern.matches(CITY, user.getRegion())) {
            errors.put("INVALID_REGION", "Ви ввели некоректну область.");
        }
        return errors;
    }
}
