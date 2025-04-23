package ra.edu.business.dao.invoice;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImp implements InvoiceDAO {

    @Override
    public List<InvoiceDetail> findInvoiceById(int id) {
        Connection conn = null;
        CallableStatement cs = null;
        List<InvoiceDetail> invoiceDetails = null;
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call display_invoice_detail_by_invoice_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            invoiceDetails = new ArrayList<>();
            while (rs.next()) {
                InvoiceDetail invoiceDetail = new InvoiceDetail();
                invoiceDetail.setId(rs.getInt("id"));
                invoiceDetail.setInvoiceId(rs.getInt("invoice_id"));
                invoiceDetail.setProductId(rs.getInt("product_id"));
                invoiceDetail.setQuantity(rs.getInt("quantity"));
                invoiceDetail.setUnitPrice(rs.getFloat("unit_price"));
                invoiceDetails.add(invoiceDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoiceDetails;
    }

    @Override
    public List<Invoice> findInvoiceByCustomerName(String name) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call search_invoice_by_customer_name(?)}");
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoices;
    }

    @Override
    public List<Invoice> findInvoiceByDateCreated(LocalDate higherDate, LocalDate lowerDate) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call search_invoice_by_date(?, ?)}");
            cs.setDate(1, Date.valueOf(String.valueOf(higherDate)));
            cs.setDate(2, Date.valueOf(String.valueOf(lowerDate)));
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findAll() {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            if (conn == null) {
                System.err.println("Kết nối cơ sở dữ liệu thất bại");
                return invoices;
            }
            cs = conn.prepareCall("{call display_all_invoice()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoices.add(invoice);
            }
            System.out.println("Số hóa đơn tìm thấy: " + invoices.size());
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lấy danh sách hóa đơn", e);
        } catch (Exception e) {
            System.err.println("Lỗi khác: " + e.getMessage());
            throw new RuntimeException("Lỗi không xác định", e);
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoices;
    }

    @Override
    public boolean save(Invoice invoice) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(true);
            cs = conn.prepareCall("{call add_invoice(?,?)}");
            cs.setInt(1, invoice.getCustomerId());
            cs.setDouble(2, 0);
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
    public boolean update(Invoice invoice) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(true);
            cs = conn.prepareCall("{call update_total_amount(?,?)}");
            cs.setInt(1, invoice.getId());
            cs.setDouble(2, invoice.getTotalAmount());
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
    public boolean delete(Invoice invoice) {
        return false;
    }
}
