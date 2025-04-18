package ra.edu.business.dao.auth;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Admin;

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
                return new Admin(id, user, pass);
            }

        } catch (Exception e) {
            System.out.println("Lỗi DAO: " + e.getMessage());
        }

        return null;
    }
}

