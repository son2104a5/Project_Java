package ra.edu.business.dao.auth;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Admin;
import ra.edu.presentation.LoginUI;
import ra.edu.utils.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAOImp implements AuthDAO {

    @Override
    public Admin login(String username, String password) {
        String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String pass = rs.getString("password");
                LoginUI.currentAdmin = new Admin(id, user, pass);
                return LoginUI.currentAdmin;
            }

        } catch (Exception e) {
            System.out.println(Color.RED + "Lỗi DAO: " + e.getMessage() + Color.RESET);
        }

        return null;
    }
}

