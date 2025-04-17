package ra.edu;

import ra.edu.presentation.LoginUI;

import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("========= HỆ THỐNG QUẢN LÍ CỬA HÀNG =========\n" +
                    "1. Đăng nhập Admin\n" +
                    "2. Thoát\n" +
                    "==============================================");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    LoginUI.display(sc);
                    break;
                case 2:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình.");
                    System.exit(0);
            }
        } while (true);
    }
}
