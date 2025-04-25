package ra.edu.business.dao.statistic;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Invoice;
import ra.edu.utils.Color;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAOImp implements StatisticDAO{
    @Override
    public List<Invoice> totalDateRevenue(LocalDate date_in, LocalDate date_out) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_date_revenue(?, ?)}");
            cs.setDate(1, Date.valueOf(date_in));
            cs.setDate(2, Date.valueOf(date_out));
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
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> totalMonthRevenue(int month_in, int year_in, int month_out, int year_out) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices =  new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_month_revenue(?,?,?,?)}");
            cs.setInt(1, month_in);
            cs.setInt(2, year_in);
            cs.setInt(3, month_out);
            cs.setInt(4, year_out);
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
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoices;
    }

    @Override
    public List<Invoice> totalYearRevenue(int year_in, int year_out) {
        Connection conn = null;
        CallableStatement cs = null;
        List<Invoice> invoices =  new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            cs = conn.prepareCall("{call total_year_revenue(?,?)}");
            cs.setInt(1, year_in);
            cs.setInt(2, year_out);
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
            System.out.println(Color.RED + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.close(conn, cs);
        }
        return invoices;
    }
}
