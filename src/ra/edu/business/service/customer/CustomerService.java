package ra.edu.business.service.customer;

import ra.edu.business.model.Customer;
import ra.edu.business.service.AppService;

public interface CustomerService extends AppService<Customer> {
    Customer findCustomerByID(int id);
}
