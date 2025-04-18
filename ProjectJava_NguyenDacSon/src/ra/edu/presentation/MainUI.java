package ra.edu.presentation;

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
                    "====================================\n");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    ProductUI.display(scanner);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Đã thoát chương trình.");
                    return;
                default:
                    System.out.println("Chức năng không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
}
