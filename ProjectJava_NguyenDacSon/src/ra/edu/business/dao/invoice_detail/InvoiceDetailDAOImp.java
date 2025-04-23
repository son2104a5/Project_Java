package ra.edu.business.dao.invoice_detail;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.InvoiceDetail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InvoiceDetailDAOImp implements InvoiceDetailDAO {
    @Override
    public List<InvoiceDetail> findAll() {
        return List.of();
    }

    @Override
    public boolean save(InvoiceDetail invoiceDetail) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(true);
            cs = conn.prepareCall("{call add_invoice_detail(?,?,?,?)}");
            cs.setInt(1, invoiceDetail.getInvoiceId());
            cs.setInt(2, invoiceDetail.getProductId());
            cs.setInt(3, invoiceDetail.getQuantity());
            cs.setDouble(4, invoiceDetail.getUnitPrice());
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
    public boolean update(InvoiceDetail invoiceDetail) {
        return false;
    }

    @Override
    public boolean delete(InvoiceDetail invoiceDetail) {
        return false;
    }
}
