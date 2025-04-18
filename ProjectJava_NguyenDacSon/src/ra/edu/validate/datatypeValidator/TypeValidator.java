package ra.edu.validate.datatypeValidator;

public interface TypeValidator<T> {
    boolean isValid(String value);
    T parse(String value);
}
