package ra.edu.validate;

import java.util.Scanner;

public class InvoiceValidator {
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
