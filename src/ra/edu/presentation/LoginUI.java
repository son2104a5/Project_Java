package ra.edu.presentation;

import ra.edu.business.model.Admin;
import ra.edu.business.service.auth.AuthService;
import ra.edu.business.service.auth.AuthServiceImp;
import ra.edu.utils.Color;

import java.util.Scanner;

public class LoginUI {
    public static Admin currentAdmin = null;

    public static void display(Scanner sc) {
        AuthService authService = new AuthServiceImp();
        do {
            System.out.println(Color.BLUE + "=========" + Color.PURPLE + " ĐĂNG NHẬP ADMIN " + Color.BLUE +  "========" + Color.RESET);
            System.out.print(Color.CYAN + "Tài khoản: " + Color.RESET);
            String username = sc.nextLine();
            System.out.print(Color.CYAN + "Mật khẩu: " + Color.RESET);
            String password = sc.nextLine();
            System.out.println(Color.BLUE + "==================================" + Color.RESET);

            boolean isLoginSuccess = authService.login(username, password);

            if (isLoginSuccess) {
                System.out.println(Color.GREEN + "Đăng nhập thành công, Welcome: " + currentAdmin.getUsername() + "!" + Color.RESET);
                MainUI.display(sc);
                return;
            } else {
                System.out.println(Color.RED + "Sai username hoặc password!" + Color.RESET);
            }
        } while (true);
    }
}
