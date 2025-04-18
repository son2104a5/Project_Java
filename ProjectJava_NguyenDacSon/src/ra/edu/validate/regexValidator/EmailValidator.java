package ra.edu.validate.regexValidator;

import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean isValidEmail(String value) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return value != null && Pattern.matches(emailRegex, value.trim());
    }
}
