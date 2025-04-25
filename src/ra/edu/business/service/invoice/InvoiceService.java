package ra.edu.business.service.invoice;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;
import ra.edu.business.service.AppService;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService extends AppService<Invoice> {
    List<InvoiceDetail> findInvoiceById(int id);
    List<Invoice> findInvoiceByCustomerName(String name);
    List<Invoice> findInvoiceByDateCreated(LocalDate higherDate, LocalDate lowerDate);
}
