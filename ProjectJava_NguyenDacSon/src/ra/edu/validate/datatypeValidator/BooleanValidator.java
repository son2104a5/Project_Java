package ra.edu.validate.datatypeValidator;

public class BooleanValidator implements TypeValidator<Boolean> {
    @Override
    public boolean isValid(String value) {
        try {
            Integer.parseInt(value);
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
