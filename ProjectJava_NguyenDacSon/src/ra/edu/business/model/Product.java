package ra.edu.business.model;

import ra.edu.validate.objectValidator.ProductValidator;

import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private String brand;
    private Double price;
    private int stock;

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

    public void inputData(Scanner scanner) {
        name = ProductValidator.validateInputValue(scanner, "Nhập tên sản phẩm:", String.class);
        brand = ProductValidator.validateInputValue(scanner, "Nhập nhãn hiệu: ", String.class);
        price = ProductValidator.validateInputValue(scanner, "Nhập giá sản phẩm:", Double.class);
        stock = ProductValidator.validateInputValue(scanner, "Nhập tồn kho: ", Integer.class);
    }

    @Override
    public String toString() {
        return "|" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
