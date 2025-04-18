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
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();

            if (product.getName() != null) {
                cs = conn.prepareCall("{call update_product_name(?, ?)}");
                cs.setInt(1, product.getId());
                cs.setString(2, product.getName());
            } else if (product.getBrand() != null) {
                cs = conn.prepareCall("{call update_product_brand(?, ?)}");
                cs.setInt(1, product.getId());
                cs.setString(2, product.getBrand());
            } else if (product.getPrice() != null) {
                cs = conn.prepareCall("{call update_product_price(?, ?)}");
                cs.setInt(1, product.getId());
                cs.setDouble(2, product.getPrice());
            } else if (product.getStock() > 0) {
                cs = conn.prepareCall("{call update_product_stock(?, ?)}");
                cs.setInt(1, product.getId());
                cs.setInt(2, product.getStock());
            } else {
                return false;
            }
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call delete_product(?)}");
            cs.setInt(1, product.getId());
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return false;
    }

    @Override
    public Product findProductById(int id) {
        Connection conn = null;
        CallableStatement cs = null;
        Product product = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call find_product_by_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return null;
    }

    @Override
    public List<Product> findProductByBrand(String value) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Product> products = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call find_product_by_brand(?)}");
            cs.setString(1, value);
            ResultSet rs = cs.executeQuery();
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
    public List<Product> findProductByPriceAmount(Double value1, Double value2) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Product> products = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call find_product_by_price_amount(?, ?)}");
            cs.setDouble(1, value1);
            cs.setDouble(2, value2);
            ResultSet rs = cs.executeQuery();
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
    public List<Product> findProductByStockRange(int value1, int value2) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Product> products = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call find_product_by_stock_amount(?, ?)}");
            cs.setInt(1, value1);
            cs.setInt(2, value2);
            ResultSet rs = cs.executeQuery();
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
}
