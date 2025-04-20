package ra.edu.business.service.customer;

import ra.edu.business.dao.customer.CustomerDAO;
import ra.edu.business.dao.customer.CustomerDAOImp;
import ra.edu.business.model.Customer;

import java.util.List;

public class CustomerServiceImp implements CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerServiceImp() {
        customerDAO = new CustomerDAOImp();
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public boolean save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    public boolean update(Customer customer) {
        return customerDAO.update(customer);
    }

    @Override
    public boolean delete(Customer customer) {
        return customerDAO.delete(customer);
    }

    @Override
    public Customer findCustomerByID(int id) {
        return customerDAO.findCustomerById(id);
    }
}
