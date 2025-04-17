package ra.edu.business.dao.product;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements ProductDAO {
    @Override
    public List<Product> findAll() {
        Connection conn = null;
        CallableStatement cs = null;
        List<Product> products = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call display_all_product()}");
            ResultSet rs = cs.executeQuery();
            products = new ArrayList<Product>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return products;
    }

    @Override
    public boolean save(Product product) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call add_product(?, ?, ?, ?)}");
            cs.setString(1, product.getName());
            cs.setString(2, product.getBrand());
            cs.setDouble(3, product.getPrice());
            cs.setInt(4, product.getStock());
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }
}
