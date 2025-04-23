package ra.edu.presentation;

import ra.edu.business.service.auth.AuthService;
import ra.edu.business.service.auth.AuthServiceImp;
import ra.edu.utils.Color;

import java.util.Scanner;

public class LoginUI {
    public static void display(Scanner sc) {
        AuthService authService = new AuthServiceImp();
        do {
            System.out.println("========= ĐĂNG NHẬP ADMIN ========");
            System.out.print("Tài khoản: ");
            String username = sc.nextLine();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();
            System.out.println("==================================");

            boolean isLoginSuccess = authService.login(username, password);

            if (isLoginSuccess) {
                System.out.println(Color.GREEN + "Đăng nhập thành công!" + Color.RESET);
                MainUI.display(sc);
                return;
            } else {
                System.err.println("Sai username hoặc password!");
            }
        } while (true);
    }
}
