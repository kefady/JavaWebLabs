package com.universityadmissions.validator;

import com.universityadmissions.entity.User;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String NAME = "[а-яА-ЯіїєґІЇЄҐ'\\s]{2,}";
    private static final String CITY = "[а-яА-ЯіїєґІЇЄҐ'\\-\\s]{2,}";
    private static final Logger logger = Logger.getLogger(UserValidator.class);

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
        logger.info("Validation of user '" + user.getUsername() + "' is completed.");
        return errors;
    }
}
