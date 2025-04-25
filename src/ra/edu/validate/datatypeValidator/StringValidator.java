package ra.edu.validate.datatypeValidator;

public class StringValidator implements TypeValidator<String> {
    @Override
    public boolean isValid(String value) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String parse(String value) {
        return value;
    }
}
