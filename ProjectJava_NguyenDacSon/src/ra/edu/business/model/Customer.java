package ra.edu.business.model;

import ra.edu.validate.objectValidator.CustomerValidator;
import ra.edu.validate.objectValidator.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public static int idWidth = "ID".length();
    public static int nameWidth = "Tên khách hàng".length();
    public static int phoneWidth = "Số điện thoại".length();
    public static int emailWidth = "Email".length();
    public static int addressWidth = "Địa chỉ".length();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void inputData(Scanner scanner, List<Customer> customerList) {
        name = InputValidator.validateInputValue(scanner, "Nhập tên khách hàng:", String.class);
        phone = CustomerValidator.validatePhone(scanner, "Nhập số điện thoại:");
        do {
            email = CustomerValidator.validateEmail(scanner, "Nhập email khách hàng:");
        } while (CustomerValidator.validateHasExistEmail(email, customerList));
        address = InputValidator.validateInputValue(scanner, "Nhập địa chỉ:", String.class);
    }

    @Override
    public String toString() {
        return String.format("| %-" + idWidth + "d | %-" + nameWidth + "s | %-" + phoneWidth + "s | %" + emailWidth + "s | %-" + addressWidth + "s |",
                id, name, phone, email, address);
    }
}
