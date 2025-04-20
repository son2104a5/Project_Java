package ra.edu.validate.datatypeValidator;

public class BooleanValidator implements TypeValidator<Boolean> {
    @Override
    public boolean isValid(String value) {
        try {
            Boolean.parseBoolean(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean parse(String value) {
        return Boolean.parseBoolean(value);
    }
}
