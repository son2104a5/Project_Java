package ra.edu.business.dao.statistic;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Invoice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAOImp implements StatisticDAO{
    @Override
    public List<Invoice> totalDateRevenue(LocalDate date) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_date_revenue(?)}");
            cs.setDate(1, Date.valueOf(date));
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> totalMonthRevenue(int month, int year) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices =  new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_month_revenue(?,?)}");
            cs.setInt(1, month);
            cs.setInt(2, year);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
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
    public List<Invoice> totalYearRevenue(int year) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices =  new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_year_revenue(?)}");
            cs.setInt(1, year);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
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
}
