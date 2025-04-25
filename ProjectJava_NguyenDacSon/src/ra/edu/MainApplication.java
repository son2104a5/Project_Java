package ra.edu;

import ra.edu.presentation.LoginUI;
import ra.edu.utils.Color;
import ra.edu.validate.objectValidator.InputValidator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MainApplication {
    public static final int PAGE_SIZE = 5;
    public static final int FIRST_PAGE = 1;
    public static final DecimalFormat df = new DecimalFormat("#.##");
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(Color.BLUE + "=========" + Color.PURPLE + " HỆ THỐNG QUẢN LÍ CỬA HÀNG " + Color.BLUE + "=========\n" + Color.RESET +
                    Color.CYAN + "1. Đăng nhập Admin\n" +
                    "0. Thoát\n" + Color.RESET +
                    Color.BLUE + "==============================================" + Color.RESET);
            int choice = InputValidator.validateInputValue(sc, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1:
                    LoginUI.display(sc);
                    break;
                case 0:
                    System.out.println(Color.GREEN + "Cảm ơn bạn đã sử dụng chương trình." + Color.RESET);
                    System.exit(0);
                default:
                    System.err.println("Chức năng không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
}
