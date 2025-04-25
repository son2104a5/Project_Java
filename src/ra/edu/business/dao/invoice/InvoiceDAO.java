package ra.edu.business.dao.invoice;

import ra.edu.business.dao.AppDAO;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDAO extends AppDAO<Invoice> {
    List<InvoiceDetail> findInvoiceById(int id);
    List<Invoice> findInvoiceByCustomerName(String name);
    List<Invoice> findInvoiceByDateCreated(LocalDate higherDate, LocalDate lowerDate);
}
