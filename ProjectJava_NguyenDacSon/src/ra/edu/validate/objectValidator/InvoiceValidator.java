package ra.edu.validate.objectValidator;

import ra.edu.validate.datatypeValidator.TypeValidator;
import ra.edu.validate.datatypeValidator.ValidateFactory;

import java.util.Scanner;

public class InvoiceValidator {
    public static <T> T validateInputValue(Scanner scanner, String message, Class<T> type) {
        TypeValidator<T> validator = ValidateFactory.getValidator(type);
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Chưa nhập dữ liệu, vui lòng nhập dữ liệu");
                continue;
            }
            if (validator.isValid(value)) {
                try {
                    return validator.parse(value);
                } catch (Exception e) {
                    System.err.println("Lỗi khi chuyển đổi dữ liệu: " + e.getMessage());
                }
            } else {
                System.err.println("Kiểu dữ liệu không phù hợp, vui lòng nhập lại");
            }
        }
    }
}
