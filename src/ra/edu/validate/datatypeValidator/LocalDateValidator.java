package ra.edu.validate.datatypeValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateValidator implements TypeValidator<LocalDate> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public boolean isValid(String value) {
        try {
            LocalDate.parse(value, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LocalDate parse(String value) {
        return LocalDate.parse(value, formatter);
    }
}
