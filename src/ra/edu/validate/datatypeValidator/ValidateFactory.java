package ra.edu.validate.datatypeValidator;

import java.time.LocalDate;

public class ValidateFactory {
    public static <T> TypeValidator<T> getValidator(Class<T> type) {
        if (type == Integer.class) return (TypeValidator<T>) new IntegerValidator();
        if (type == String.class) return (TypeValidator<T>) new StringValidator();
        if (type == Double.class) return (TypeValidator<T>) new DoubleValidator();
        if (type == Boolean.class) return (TypeValidator<T>) new BooleanValidator();
        if (type == LocalDate.class) return (TypeValidator<T>) new LocalDateValidator();
        throw new UnsupportedOperationException("Không hỗ trợ kiểu dữ liệu: " + type.getName());
    }
}
