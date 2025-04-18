package ra.edu.validate.objectValidator;

import ra.edu.validate.datatypeValidator.TypeValidator;
import ra.edu.validate.datatypeValidator.ValidateFactory;
import ra.edu.validate.regexValidator.EmailValidator;
import ra.edu.validate.regexValidator.PhoneValidator;

import java.util.Scanner;

public class CustomerValidator {
    public static String validateEmail(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập email, vui lòng nhập dữ liệu");
                continue;
            }
            if (EmailValidator.isValidEmail(value)) {
                return value;
            }
            System.err.println("Không đúng định dạng email, vui lòng nhập lại");
        }
    }

    public static String validatePhone(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Bạn chưa nhập số điện thoại, vui lòng nhập lại");
                continue;
            }
            if (PhoneValidator.isValidPhoneNumberVN(value)) {
                return value;
            }
            System.err.println("Không đúng số điện thoại di đông VN, vui lòng nhập lại");
        }
    }

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
