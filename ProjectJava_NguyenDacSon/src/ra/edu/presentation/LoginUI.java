package ra.edu.presentation;

import ra.edu.business.service.auth.AuthService;
import ra.edu.business.service.auth.AuthServiceImp;
import ra.edu.validate.objectValidator.AuthValidator;

import java.util.Scanner;

public class LoginUI {
    public static void display(Scanner sc) {
        AuthService authService = new AuthServiceImp();
        System.out.println("========= ĐĂNG NHÂP ADMIN ========");
        System.out.print("Tài khoản: ");
        String username = sc.nextLine();
        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        System.out.println("==================================");

        boolean isLoginSuccess = authService.login(username, password);

        if (isLoginSuccess) {
            System.out.println("Đăng nhập thành công!");
            MainUI.display(sc);
        } else {
            AuthValidator.validateLogin();
        }
    }
}
