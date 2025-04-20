package ra.edu;

import ra.edu.presentation.LoginUI;
import ra.edu.validate.objectValidator.InputValidator;

import java.io.IOException;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("========= HỆ THỐNG QUẢN LÍ CỬA HÀNG =========\n" +
                    "1. Đăng nhập Admin\n" +
                    "2. Thoát\n" +
                    "==============================================");
            int choice = InputValidator.validateInputValue(sc, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1:
                    LoginUI.display(sc);
                    break;
                case 2:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Chức năng không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
}
