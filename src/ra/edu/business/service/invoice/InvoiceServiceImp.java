package ra.edu.business.service.invoice;

import ra.edu.business.dao.invoice.InvoiceDAO;
import ra.edu.business.dao.invoice.InvoiceDAOImp;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;

public class InvoiceServiceImp implements InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceServiceImp() {
        invoiceDAO = new InvoiceDAOImp();
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDAO.findAll();
    }

    @Override
    public List<Invoice> findPerPage(int page) {
        return invoiceDAO.findPerPage(page);
    }

    @Override
    public boolean save(Invoice invoice) {
        return invoiceDAO.save(invoice);
    }

    @Override
    public boolean update(Invoice invoice) {
        return invoiceDAO.update(invoice);
    }

    @Override
    public boolean delete(Invoice invoice) {
        return false;
    }

    @Override
    public List<InvoiceDetail> findInvoiceById(int id) {
        return invoiceDAO.findInvoiceById(id);
    }

    @Override
    public List<Invoice> findInvoiceByCustomerName(String name) {
        return invoiceDAO.findInvoiceByCustomerName(name);
    }

    @Override
    public List<Invoice> findInvoiceByDateCreated(LocalDate higherDate, LocalDate lowerDate) {
        return invoiceDAO.findInvoiceByDateCreated(higherDate, lowerDate);
    }
}
