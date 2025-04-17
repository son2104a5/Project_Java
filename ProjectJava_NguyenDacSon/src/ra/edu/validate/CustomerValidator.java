package ra.edu.validate;

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
            if (Validator.isValidEmail(value)) {
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
        System.out.println(message);
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.err.println("Chưa nhập dữ liệu, vui lòng nhập dữ liệu");
                continue;
            }
            if (Validator.isValidDataType(value, type)) {
                try {
                    return Validator.parseValue(value, type);
                } catch (Exception e) {
                    System.err.println("Lỗi khi chuyển đổi dữ liệu: " + e.getMessage());
                }
            } else {
                System.out.println("Kiểu dữ liệu không phù hợp, vui lòng nhập lại");
            }
        }
    }
}
