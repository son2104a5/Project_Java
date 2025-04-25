package ra.edu.business.dao.customer;

import ra.edu.MainApplication;
import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Customer;
import ra.edu.utils.Color;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImp implements CustomerDAO {
    @Override
    public Customer findCustomerById(int id) {
        Connection conn = null;
        CallableStatement cs = null;
        Customer customer = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call find_customer_by_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        Connection conn = null;
        CallableStatement cs = null;
        List<Customer> customers = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call display_all_customer()}");
            ResultSet rs = cs.executeQuery();
            customers = new ArrayList<Customer>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return customers;
    }

    @Override
    public List<Customer> findPerPage(int page) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Customer> customers = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call display_all_customer_per_page(?)}");
            cs.setInt(1, page == 1 ? 0 : (page - 1) * MainApplication.PAGE_SIZE);
            ResultSet rs = cs.executeQuery();
            customers = new ArrayList<Customer>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return customers;
    }

    @Override
    public boolean save(Customer customer) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call add_customer(?, ?, ?, ?)}");
            cs.setString(1, customer.getName());
            cs.setString(2, customer.getPhone());
            cs.setString(3, customer.getEmail());
            cs.setString(4, customer.getAddress());
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(true);

            if (customer.getName() != null) {
                cs = conn.prepareCall("{call update_customer_name(?, ?)}");
                cs.setInt(1, customer.getId());
                cs.setString(2, customer.getName());
                cs.executeUpdate();
            }
            if (customer.getPhone() != null) {
                cs = conn.prepareCall("{call update_customer_phone(?, ?)}");
                cs.setInt(1, customer.getId());
                cs.setString(2, customer.getPhone());
                cs.executeUpdate();
            }
            if (customer.getEmail() != null) {
                cs = conn.prepareCall("{call update_customer_email(?, ?)}");
                cs.setInt(1, customer.getId());
                cs.setString(2, customer.getEmail());
                cs.executeUpdate();
            }
            if (customer.getAddress() != null) {
                cs = conn.prepareCall("{call update_customer_address(?, ?)}");
                cs.setInt(1, customer.getId());
                cs.setString(2, customer.getAddress());
                cs.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return false;
    }

    @Override
    public boolean delete(Customer customer) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call delete_customer(?)}");
            cs.setInt(1, customer.getId());
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Không thể xóa do khách hàng này đã từng mua sản phẩm");
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return false;
    }
}
