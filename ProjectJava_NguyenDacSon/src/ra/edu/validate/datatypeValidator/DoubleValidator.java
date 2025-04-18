package ra.edu.validate.datatypeValidator;

public class DoubleValidator implements TypeValidator<Double> {
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
    public Double parse(String value) {
        return Double.parseDouble(value);
    }
}
