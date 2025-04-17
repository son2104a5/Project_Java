package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidEmail(String value) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return value != null && Pattern.matches(emailRegex, value.trim());
    }

    public static <T> boolean isValidDataType(String value, Class<T> type) {
        try {
            if (type == Integer.class) {
                Integer.parseInt(value);
            } else if (type == Double.class) {
                Double.parseDouble(value);
            } else if (type == Float.class) {
                Float.parseFloat(value);
            } else if (type == Boolean.class) {
                Boolean.parseBoolean(value);
            } else if (type == LocalDate.class) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(value, formatter);
            } else if (type.isEnum()) {
                parseEnumValue(value, type);
            } else {
                throw new UnsupportedOperationException("Không hỗ trợ dữ liệu " + type.getName());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean parseEnumValue(String input, Class<?> type) {
        Class<? extends Enum> enumType = (Class<? extends Enum>) type;
        for (Enum constant : enumType.getEnumConstants()) {
            if (input.equalsIgnoreCase(constant.name())) {
                return true;
            }
        }
        return false;
    }

    public static <T> T parseValue(String value, Class<T> type) {
        if (type == Integer.class) {
            return type.cast(Integer.parseInt(value));
        } else if (type == Double.class) {
            return type.cast(Double.parseDouble(value));
        } else if (type == Float.class) {
            return type.cast(Float.parseFloat(value));
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(value));
        } else if (type == LocalDate.class) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return type.cast(LocalDate.parse(value, formatter));
        } else if (type.isEnum()) {
            for (Object constant : type.getEnumConstants()) {
                if (value.equalsIgnoreCase(((Enum<?>) constant).name())) {
                    return type.cast(constant);
                }
            }
            throw new IllegalArgumentException("Giá trị không khớp enum");
        } else {
            throw new UnsupportedOperationException("Không hỗ trợ kiểu dữ liệu: " + type.getName());
        }
    }
}
