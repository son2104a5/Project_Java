package ra.edu.business.config;

import ra.edu.utils.Color;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private final static String URL = "jdbc:mysql://localhost:3306/project_phone_application";
    private final static String USER = "root";
    private final static String PASSWORD = "snd21804@";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            System.out.println(Color.RED + "Lỗi: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED + "Lỗi không xác định: " + e.getMessage() + Color.RESET);
        }
        return conn;
    }

    public static void close(Connection conn, CallableStatement cs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (cs != null) {
            try {
                cs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
