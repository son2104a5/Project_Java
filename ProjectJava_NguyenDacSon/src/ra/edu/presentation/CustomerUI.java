package ra.edu.presentation;

import ra.edu.MainApplication;
import ra.edu.business.model.Customer;
import ra.edu.business.model.Product;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableCustomerUtil;
import ra.edu.utils.TableProductUtil;
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
            System.out.println(Color.BLUE + "===========" + Color.PURPLE + " QUẢN LÍ KHÁCH HÀNG " + Color.BLUE + "===========\n" +
                    Color.CYAN + "1. Hiển thị danh sách khách hàng\n" +
                    "2. Thêm khách hàng mới\n" +
                    "3. Sửa thông tin khách hàng\n" +
                    "4. Xóa khách hàng\n" +
                    "0. Thoát menu\n" +
                    Color.BLUE + "==========================================" + Color.RESET);

            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1 -> customerUI.displayCustomers(scanner);
                case 2 -> customerUI.createCustomers(scanner);
                case 3 -> customerUI.updateCustomer(scanner);
                case 4 -> customerUI.deleteCustomer(scanner);
                case 0 -> {
                    System.out.println(Color.GREEN + "Thoát menu khách hàng....." + Color.RESET);
                    return;
                }
                default -> System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
            }
        } while (true);
    }

    public void displayCustomers(Scanner scanner) {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            System.out.println(Color.RED + "Không có khách hàng, vui lòng thêm khách hàng trước" + Color.RESET);
            return;
        }
        List<Customer> customerInPage = customerService.findPerPage(MainApplication.FIRST_PAGE);
        int currentPage = MainApplication.FIRST_PAGE;
        int totalCustomers = customers.size();
        int totalPage = (int) Math.ceil((double) totalCustomers / MainApplication.PAGE_SIZE);

        do {
            TableCustomerUtil.printCustomerTableHeader(customers, "DANH SÁCH CÁC KHÁCH HÀNG HIỆN CÓ");
            customerInPage.forEach(System.out::println);
            TableCustomerUtil.printCustomerTableFooter();
            System.out.println("Trang " + currentPage + "/" + totalPage);
            System.out.println(Color.YELLOW + "1. Prev \t 2. Chọn trang \t 3. Next \t 0. Thoát" + Color.RESET);
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1 -> {
                    if (currentPage > MainApplication.FIRST_PAGE) {
                        customerInPage = customerService.findPerPage(currentPage - 1);
                        currentPage--;
                    } else {
                        System.out.println(Color.RED + "Đây là trang đầu tiên, không thể chuyển." + Color.RESET);
                    }
                }
                case 2 -> {
                    do {
                        int page = InputValidator.validateInputValue(scanner, "Nhập trang muốn xem: ", Integer.class);
                        if (page >= 1 && page <= totalPage) {
                            customerInPage = customerService.findPerPage(page);
                            currentPage = page;
                            break;
                        } else {
                            System.out.println(Color.RED + "Số trang không hợp lệ, vui lòng nhập lại." + Color.RESET);
                        }
                    } while (true);
                }
                case 3 -> {
                    if (currentPage < totalPage) {
                        customerInPage = customerService.findPerPage(currentPage + 1);
                        currentPage++;
                    } else {
                        System.out.println(Color.RED + "Đây là trang cuối cùng, không thể chuyển" + Color.RESET);
                    }
                }
                case 0 -> {
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                }
                default -> System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
            }
        } while (true);
    }

    public void createCustomers(Scanner scanner) {
        int n = InputValidator.validateInputValue(scanner, "Nhập số khách hàng muốn thêm: ", Integer.class);
        List<Customer> customers = customerService.findAll();
        for (int i = 0; i < n; i++) {
            Customer customer = new Customer();
            customer.inputData(scanner, customers);
            boolean success = customerService.save(customer);

            if (success) {
                displayCustomers(scanner);
            }
        }
    }

    public void updateCustomer(Scanner scanner) {
        int id = InputValidator.validateInputValue(scanner, "Nhập ID khách hàng cần tìm: ", Integer.class);
        int choice;
        List<Customer> customers = customerService.findAll();
        Customer oldCustomer = customerService.findCustomerByID(id);

        if (oldCustomer != null) {
            do {
                System.out.println(Color.PURPLE + "Bạn muốn sửa thông tin nào:\n" +
                        Color.CYAN + "1. Tên khách hàng\n" +
                        "2. Số điện thoại\n" +
                        "3. Email\n" +
                        "4. Địa chỉ\n" +
                        "0. Thoát" + Color.RESET);
                choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);

                Customer updateCustomer = new Customer();
                updateCustomer.setId(id);

                switch (choice) {
                    case 1 -> {
                        String value = InputValidator.validateInputValue(scanner, "Nhập tên mới:", String.class);
                        updateCustomer.setName(value);
                    }
                    case 2 -> {
                        String value = CustomerValidator.validatePhone(scanner, "Nhập số điện thoại mới:");
                        updateCustomer.setPhone(value);
                    }
                    case 3 -> {
                        boolean flag;
                        do {
                            String value = CustomerValidator.validateEmail(scanner, "Nhập email mới:");
                            flag = CustomerValidator.validateHasExistEmail(value, customers);
                            if (!flag) {
                                updateCustomer.setEmail(value);
                            }
                        } while (flag);
                    }
                    case 4 -> {
                        String value = InputValidator.validateInputValue(scanner, "Nhập địa chỉ mới:", String.class);
                        updateCustomer.setAddress(value);
                    }
                    case 0 -> System.out.println(Color.GREEN + "Thoát menu cập nhật....." + Color.RESET);
                    default -> System.out.println(Color.RED + "Giá trị không hợp lệ, vui lòng nhập lại" + Color.RESET);
                }

                if (choice == 0) {
                    boolean success = customerService.update(updateCustomer);
                    if (success) {
                        displayCustomers(scanner);
                    }
                }

            } while (choice != 0);
        } else {
            System.out.println(Color.RED + "Không tìm thấy khách hàng có ID " + id + "." + Color.RESET);
        }
    }

    public void deleteCustomer(Scanner scanner) {
        int id = InputValidator.validateInputValue(scanner, "Nhập ID khách hàng cần xóa: ", Integer.class);

        if (customerService.findCustomerByID(id) != null) {
            do {
                int choice = InputValidator.validateInputValue(scanner, "Bạn thực sự muốn xóa khách hàng này? (1: Yes, 2: No): ", Integer.class);
                switch (choice) {
                    case 1:
                        Customer customer = new Customer();
                        customer.setId(id);
                        boolean success = customerService.delete(customer);
                        if (success) {
                            displayCustomers(scanner);
                        }
                        return;
                    case 2:
                        System.out.println(Color.GREEN + "Đã hủy xóa khách hàng có id " + id + "." + Color.RESET);
                        return;
                    default:
                        System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
                        break;
                }
            } while (true);
        } else {
            System.out.println(Color.RED + "Không tìm thấy khách hàng có ID " + id + "." + Color.RESET);
        }
    }
}
