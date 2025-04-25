package ra.edu.presentation;

import ra.edu.utils.Color;
import ra.edu.validate.objectValidator.InputValidator;

import java.util.Scanner;

public class MainUI {
    public static void display(Scanner scanner) {
        do {
            System.out.println(Color.BLUE + "==========" + Color.PURPLE + " MENU CHÍNH " + Color.BLUE + "==========\n" +
                    Color.CYAN + "1. Quản lí sản phẩm\n" +
                    "2. Quản lí khách hàng\n" +
                    "3. Quản lí hóa đơn\n" +
                    "4. Thống kê doanh thu\n" +
                    "0. Đăng xuất\n" +
                    Color.BLUE + "================================" + Color.RESET);
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
                case 0:
                    System.out.println(Color.GREEN + "Đã thoát chương trình." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Lựa chọn của bạn không hợp lê, vui lòng nhập lại" + Color.RESET);
                    break;
            }
        } while (true);
    }
}
