package ra.edu.presentation;

import ra.edu.business.service.auth.AuthService;
import ra.edu.business.service.auth.AuthServiceImp;

import java.util.Scanner;

public class LoginUI {
    public static void display(Scanner sc) {
        AuthService authService = new AuthServiceImp();
        System.out.println("========= ĐĂNG NHÂP ADMIN ========");
        System.out.println("Tài khoản: ");
        System.out.println("Mật khẩu : ");
        System.out.println("==================================");

        String username = sc.nextLine();
        String password = sc.nextLine();

        boolean isLoginSuccess = authService.login(username, password);

        if (isLoginSuccess) {
            System.out.println("Đăng nhập thành công!");

        } else {
            System.out.println("Sai username hoặc password!");
        }
    }
}
