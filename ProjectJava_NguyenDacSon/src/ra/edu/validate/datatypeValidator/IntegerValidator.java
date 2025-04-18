package ra.edu.validate.datatypeValidator;

public class IntegerValidator implements TypeValidator<Integer> {
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
    public Integer parse(String value) {
        return Integer.parseInt(value);
    }
}
