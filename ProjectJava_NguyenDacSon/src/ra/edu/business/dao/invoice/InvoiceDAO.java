package ra.edu.business.dao.invoice;

import ra.edu.business.dao.AppDAO;
import ra.edu.business.model.Invoice;

public interface InvoiceDAO extends AppDAO<Invoice> {
    Invoice findInvoiceById(int id);
}
