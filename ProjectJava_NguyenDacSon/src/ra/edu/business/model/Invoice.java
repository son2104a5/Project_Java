package ra.edu.business.model;

import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;
import ra.edu.validate.objectValidator.InputValidator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDateTime createdAt;
    private Double totalAmount;

    public Invoice() {
    }

    public Invoice(int customerId, Double totalAmount) {
        this.customerId = customerId;
        this.createdAt = LocalDateTime.now();
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static int idWidth = "ID".length();
    public static int customerIdWidth = "Mã khách hàng".length();
    public static int createdAtWidth = "Ngày tạo".length();
    public static int totalAmountWidth = "Tổng tiền".length();

    public void inputData(Scanner scanner, int latestInvoiceId) {
        id = latestInvoiceId;
        do {
            CustomerService customerService = new CustomerServiceImp();
            customerId = InputValidator.validateInputValue(scanner, "Nhập ID khách hàng: ", Integer.class);
            if (customerService.findCustomerByID(customerId) == null) {
                System.err.println("Khách hàng có id" + customerId + " không tồn tại");
            } else {
                break;
            }
        } while (true);
        createdAt = LocalDateTime.now();
        totalAmount = (double) 0;
    }

    @Override
    public String toString() {
        return String.format("| %-" + idWidth + "d | %-" + customerIdWidth + "d | %-" + createdAtWidth + "s | %" + totalAmountWidth + ".2f |",
                id, customerId, createdAt, totalAmount);
    }
}
