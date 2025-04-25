package ra.edu.validate.objectValidator;

import ra.edu.utils.Color;
import ra.edu.validate.datatypeValidator.TypeValidator;
import ra.edu.validate.datatypeValidator.ValidateFactory;

import java.util.Scanner;

public class InputValidator {
    public static <T> T validateInputValue(Scanner scanner, String message, Class<T> type) {
        TypeValidator<T> validator = ValidateFactory.getValidator(type);
        System.out.print(Color.YELLOW + message + Color.RESET);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println(Color.RED + "Chưa nhập dữ liệu, vui lòng nhập dữ liệu" + Color.RESET);
                continue;
            }
            if (validator.isValid(value)) {
                try {
                    return validator.parse(value);
                } catch (Exception e) {
                    System.out.println(Color.RED + "Lỗi khi chuyển đổi dữ liệu: " + e.getMessage() + Color.RESET);
                }
            } else {
                System.out.println(Color.RED + "Kiểu dữ liệu không phù hợp, vui lòng nhập lại" + Color.RESET);
            }
        }
    }
}
