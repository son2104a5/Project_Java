package ra.edu.business.service.invoice_detail;

import ra.edu.business.dao.invoice_detail.InvoiceDetailDAO;
import ra.edu.business.dao.invoice_detail.InvoiceDetailDAOImp;
import ra.edu.business.model.InvoiceDetail;

import java.util.List;

public class InvoiceDetailServiceImp implements InvoiceDetailService{
    private final InvoiceDetailDAO invoiceDetailDAO;

    public InvoiceDetailServiceImp() {
        invoiceDetailDAO = new InvoiceDetailDAOImp();
    }

    @Override
    public List<InvoiceDetail> findAll() {
        return List.of();
    }

    @Override
    public boolean save(InvoiceDetail invoiceDetail) {
        return invoiceDetailDAO.save(invoiceDetail);
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
