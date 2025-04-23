package ra.edu.presentation;

import ra.edu.validate.objectValidator.InputValidator;

import java.util.Scanner;

public class MainUI {
    public static void display(Scanner scanner) {
        do {
            System.out.println("========== MENU CHÍNH ==========\n" +
                    "1. Quản lí sản phẩm\n" +
                    "2. Quản lí khách hàng\n" +
                    "3. Quản lí hóa đơn\n" +
                    "4. Thống kê doanh thu\n" +
                    "5. Đăng xuất\n" +
                    "====================================");
            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1:
                    ProductUI.display(scanner);
                    break;
                case 2:
                    CustomerUI.display(scanner);
                    break;
                case 3:
                    InvoiceUI.display(scanner);
                    break;
                case 4:
                    StatisticUI.display(scanner);
                    break;
                case 5:
                    System.out.println("Đã thoát chương trình.");
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
}
