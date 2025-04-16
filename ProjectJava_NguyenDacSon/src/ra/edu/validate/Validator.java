package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {
    public static final String PHONE_VIETTEL_PREFIXES = "086|096|097|098|039|038|037|036|035|034|033|032";
    private static final String PHONE_VINAPHONE_PREFIXES = "091|094|088|083|084|085|081|082";
    private static final String PHONE_MOBIFONE_PREFIXES = "070|079|077|076|078|089|090|093";

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
