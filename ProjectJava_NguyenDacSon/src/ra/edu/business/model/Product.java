package ra.edu.business.model;

import ra.edu.utils.Color;
import ra.edu.validate.objectValidator.InputValidator;
import ra.edu.validate.objectValidator.ProductValidator;

import java.util.List;
import java.util.Scanner;

import static ra.edu.MainApplication.df;

public class Product {
    private int id;
    private String name;
    private String brand;
    private Double price;
    private int stock;
    private boolean status;

    public static int idWidth = "ID".length();
    public static int nameWidth = "Tên sản phẩm".length();
    public static int brandWidth = "Nhãn hàng".length();
    public static int priceWidth = "Giá".length();
    public static int stockWidth = "Tồn kho".length();

    public Product() {
    }

    public Product(int id, String name, String brand, Double price, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData(Scanner scanner, List<Product> productList) {
        do {
            name = InputValidator.validateInputValue(scanner, "Nhập tên sản phẩm: ", String.class);
        } while (ProductValidator.validateHasExistValue(name, productList));
        brand = InputValidator.validateInputValue(scanner, "Nhập nhãn hiệu: ", String.class);
        do {
            price = InputValidator.validateInputValue(scanner, "Nhập giá sản phẩm: ", Double.class);
            if (ProductValidator.validateDataHasNotPositiveValue(price)) {
                System.out.println(Color.RED + "Giá sản phẩm phải là số dương" + Color.RESET);
            } else {
                break;
            }
        } while (true);
        do {
            stock = InputValidator.validateInputValue(scanner, "Nhập số lượng tồn kho: ", Integer.class);
            if (ProductValidator.validateDataHasNotPositiveValue(stock)) {
                System.out.println(Color.RED + "Giá sản phẩm phải là số dương" + Color.RESET);
            } else {
                break;
            }
        } while (true);
    }

    @Override
    public String toString() {
        return String.format("| %-" + idWidth + "d | %-" + nameWidth + "s | %-" + brandWidth + "s | %" + priceWidth + "s | %-" + stockWidth + "d  |",
                id, name, brand, df.format(price), stock);
    }
}
