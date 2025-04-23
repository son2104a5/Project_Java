package ra.edu.presentation;

import ra.edu.business.model.Customer;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableCustomerUtil;
import ra.edu.validate.objectValidator.CustomerValidator;
import ra.edu.validate.objectValidator.InputValidator;

import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerUI {
    private final CustomerService customerService;

    public CustomerUI() {
        customerService = new CustomerServiceImp();
    }

    public static void display(Scanner scanner) {
        CustomerUI customerUI = new CustomerUI();
        do {
            System.out.println("=========== QUẢN LÍ KHÁCH HÀNG ===========\n" +
                    "1. Hiển thị danh sách khách hàng\n" +
                    "2. Thêm khách hàng mới\n" +
                    "3. Sửa thông tin khách hàng\n" +
                    "4. Xóa khách hàng\n" +
                    "5. Thoát menu\n" +
                    "==========================================");

            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1 -> customerUI.displayCustomers();
                case 2 -> customerUI.createCustomers(scanner);
                case 3 -> customerUI.updateCustomer(scanner);
                case 4 -> customerUI.deleteCustomer(scanner);
                case 5 -> {
                    System.out.println(Color.GREEN + "Thoát menu khách hàng....." + Color.RESET);
                    return;
                }
                default -> System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng nhập lại");
            }
        } while (true);
    }

    public void displayCustomers() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            System.err.println("Không có khách hàng, vui lòng thêm khách hàng trước");
            return;
        }

        TableCustomerUtil.printCustomerTableHeader(customers, "DANH SÁCH CÁC KHÁCH HÀNG HIỆN CÓ");
        customers.forEach(System.out::println);
        TableCustomerUtil.printCustomerTableFooter();
    }

    public void createCustomers(Scanner scanner) {
        int n = InputValidator.validateInputValue(scanner, "Nhập số khách hàng muốn thêm: ", Integer.class);
        List<Customer> customers = customerService.findAll();
        for (int i = 0; i < n; i++) {
            Customer customer = new Customer();
            customer.inputData(scanner, customers);
            boolean success = customerService.save(customer);

            if (success) {
                displayCustomers();
            } else System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
        }
    }

    public void updateCustomer(Scanner scanner) {
        int id = InputValidator.validateInputValue(scanner, "Nhập ID khách hàng cần tìm: ", Integer.class);
        int choice;
        List<Customer> customers = customerService.findAll();
        Customer oldCustomer = customerService.findCustomerByID(id);

        if (oldCustomer != null) {
            do {
                System.out.println("Bạn muốn sửa thông tin nào:\n" +
                        "1. Tên khách hàng\n" +
                        "2. Số điện thoại\n" +
                        "3. Email\n" +
                        "4. Địa chỉ\n" +
                        "0. Thoát");
                choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);

                Customer updateCustomer = new Customer();
                updateCustomer.setId(id);
                boolean updated = false;

                switch (choice) {
                    case 1 -> {
                        String value = InputValidator.validateInputValue(scanner, "Nhập tên mới:", String.class);
                        updateCustomer.setName(value);
                        updated = true;
                    }
                    case 2 -> {
                        String value = CustomerValidator.validatePhone(scanner, "Nhập số điện thoại mới:");
                        updateCustomer.setPhone(value);
                        updated = true;
                    }
                    case 3 -> {
                        boolean flag;
                        do {
                            String value = CustomerValidator.validateEmail(scanner, "Nhập email mới:");
                            flag = CustomerValidator.validateHasExistEmail(value, customers);
                            if (!flag) {
                                updateCustomer.setEmail(value);
                                updated = true;
                            }
                        } while (flag);
                    }
                    case 4 -> {
                        String value = InputValidator.validateInputValue(scanner, "Nhập địa chỉ mới:", String.class);
                        updateCustomer.setAddress(value);
                        updated = true;
                    }
                    case 0 -> System.out.println(Color.GREEN + "Thoát menu cập nhật....." + Color.RESET);
                    default -> System.err.println("Giá trị không hợp lệ, vui lòng nhập lại");
                }

                if (updated) {
                    customerService.update(updateCustomer);
                    displayCustomers();
                }

            } while (choice != 0);
        } else {
            System.err.println("Không tìm thấy khách hàng có ID " + id + ".");
        }
    }

    public void deleteCustomer(Scanner scanner) {
        int id = InputValidator.validateInputValue(scanner, "Nhập ID khách hàng cần xóa: ", Integer.class);

        if (customerService.findCustomerByID(id) != null) {
            Customer customer = new Customer();
            customer.setId(id);
            boolean success = customerService.delete(customer);
            if (success) {
                displayCustomers();
            } else {
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
            }
        } else {
            System.err.println("Không tìm thấy khách hàng có ID " + id + ".");
        }
    }
}
