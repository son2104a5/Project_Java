package ra.edu.validate.objectValidator;

import ra.edu.business.model.Customer;
import ra.edu.validate.regexValidator.EmailValidator;
import ra.edu.validate.regexValidator.PhoneValidator;

import java.util.List;
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
            if (PhoneValidator.isValidPhoneNumberVN(value)) {
                return value;
            }
            System.err.println("Không đúng số điện thoại di đông VN, vui lòng nhập lại");
        }
    }

    public static boolean validateHasExistEmail(String value, List<Customer> customerList) {
        String trimmedValue = value.trim().replaceAll("\\s+", " ");
        for (Customer customer : customerList) {
            String normalizedProductName = customer.getEmail().trim().replaceAll("\\s+", " ");
            if (normalizedProductName.equalsIgnoreCase(trimmedValue)) {
                System.err.println("Email đã tồn tại, vui lòng nhập lại.");
                return true;
            }
        }
        return false;
    }
}
