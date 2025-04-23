package ra.edu.presentation;

import ra.edu.business.model.Product;
import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableProductUtil;
import ra.edu.validate.objectValidator.InputValidator;
import ra.edu.validate.objectValidator.ProductValidator;

import java.util.List;
import java.util.Scanner;

public class ProductUI {
    private final ProductService productService;

    public ProductUI() {
        productService = new ProductServiceImp();
    }

    public static void display(Scanner scanner) {
        ProductUI productUI = new ProductUI();
        do {

            System.out.println("========= MENU QUẢN LÍ SẢN PHẨM =========\n" +
                    "1. Hiển thị danh sách sản phẩm\n" +
                    "2. Thêm sản phẩm mới\n" +
                    "3. Cập nhật thông tin sản phẩm\n" +
                    "4. Xóa sản phẩm theo ID\n" +
                    "5. Tìm kiếm theo Brand\n" +
                    "6. Tìm kiếm theo khoảng giá\n" +
                    "7. Tìm kiếm theo tồn kho\n" +
                    "8. Quay lại menu chính\n" +
                    "=========================================");
            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1 -> productUI.displayProducts();
                case 2 -> productUI.createProducts(scanner);
                case 3 -> productUI.updateProduct(scanner);
                case 4 -> productUI.deleteProduct(scanner);
                case 5 -> productUI.findProductsByBrand(scanner);
                case 6 -> productUI.findProductsByPriceAmount(scanner);
                case 7 -> productUI.findProductsByStockRange(scanner);
                case 8 -> {
                    System.out.println(Color.GREEN + "Thoát menu quản lí sản phẩm..." + Color.RESET);
                    return;
                }
                default -> {
                    System.err.println("Lựa chọn của bạn không hợp lê, vui lòng nhập lại");
                }
            }
        } while (true);
    }

    public void displayProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            System.err.println("Không tồn tại sản phẩm nào, vui lòng thêm sản phẩm");
            return;
        }

        TableProductUtil.printProductTableHeader(products, "DANH SÁCH TẤT CẢ SẢN PHẨM");
        products.forEach(System.out::println);
        TableProductUtil.printProductTableFooter();
    }


    public void createProducts(Scanner scanner) {
        System.out.print("Nhập số sản phẩm muốn thêm: ");
        int n = Integer.parseInt(scanner.nextLine());
        List<Product> products = productService.findAll();
        for (int i = 0; i < n; i++) {
            Product product = new Product();
            product.inputData(scanner, products);
            boolean success = productService.save(product);
            if (success) {
                displayProducts();
            } else {
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
            }
        }
    }

    public void updateProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm muốn cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());
        int choice;
        Product product = productService.findProductById(id);
        if (product != null) {
            do {
                List<Product> products = productService.findAll();
                System.out.println("Bạn muốn thay đổi thông tin nào:\n" +
                        "1. Tên sản phẩm\n" +
                        "2. Nhãn hiệu\n" +
                        "3. Giá sản phẩm\n" +
                        "4. Số lượng tồn kho\n" +
                        "0. Thoát cập nhật");
                choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                switch (choice) {
                    case 1 -> {
                        boolean flag;
                        do {
                            String value = InputValidator.validateInputValue(scanner, "Nhập tên mới:", String.class);
                            flag = ProductValidator.validateHasExistValue(value, products);
                            if (!flag) {
                                product.setName(value);
                            }
                        } while (flag);
                    }
                    case 2 -> {
                        String value = InputValidator.validateInputValue(scanner, "Nhập nhãn hiệu mới:", String.class);
                        product.setBrand(value);
                    }
                    case 3 -> {
                        boolean flag;
                        do {
                            double value = InputValidator.validateInputValue(scanner, "Nhập giá mới:", Double.class);
                            flag = ProductValidator.validateDataHasNotPositiveValue(value);
                            if (!flag) {
                                product.setPrice(value);
                            } else
                                System.err.println("Giá trị nhập vào phải lớn hơn 0");
                        } while (!flag);
                    }
                    case 4 -> {
                        boolean flag;
                        do {
                            int value = InputValidator.validateInputValue(scanner, "Nhập số lượng tồn kho mới:", Integer.class);
                            flag = ProductValidator.validateDataHasNotPositiveValue(value);
                            if (!flag) {
                                product.setStock(value);
                            } else
                                System.err.println("Giá trị nhập vào phải lớn hơn 0");
                        } while (flag);
                    }
                    case 0 -> System.out.println("Thoát chức năng cập nhật....");
                    default -> System.out.println("Chức năng không hợp lệ, vui lòng nhập lại");
                }
                if (choice != 0){
                    boolean success = productService.update(product);
                    if (success) {
                        displayProducts();
                    } else {
                        System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
                    }
                }
            } while (choice != 0);
        } else {
            System.err.println("Không tìm thấy sản phẩm có ID " + id + ".");
        }
    }

    public void deleteProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm muốn xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (productService.findProductById(id) != null) {
            Product product = new Product();
            product.setId(id);
            boolean success = productService.delete(product);
            if (success) {
                displayProducts();
            } else {
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
            }
        } else {
            System.err.println("Không tìm thấy sản phẩm có ID: " + id + ".");
        }
    }

    public void findProductsByBrand(Scanner scanner) {
        System.out.print("Nhập nhãn hiệu của sản phẩm muốn tìm: ");
        String brand = scanner.nextLine();
        List<Product> products = productService.findProductByBrand(brand);
        if (products != null) {
            TableProductUtil.printProductTableHeader(products, "DANH SÁCH SẢN PHẨM CÓ BRAND = " + brand);
            products.forEach(System.out::println);
            TableProductUtil.printProductTableFooter();
        } else {
            System.err.println("Không tìm thấy sản phẩm có brand: " + brand + ".");
        }
    }

    public void findProductsByPriceAmount(Scanner scanner) {
        System.out.print("Nhập giá đầu: ");
        Double min_price = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập giá cuối: ");
        Double max_price = Double.parseDouble(scanner.nextLine());

        List<Product> products = productService.findProductByPriceAmount(min_price, max_price);
        if (products != null) {
            TableProductUtil.printProductTableHeader(products, "DANH SÁCH SẢN PHẨM CÓ GIÁ TỪ " + min_price + "-" + max_price);
            products.forEach(System.out::println);
            TableProductUtil.printProductTableFooter();
        } else {
            System.err.println("Không tìm thấy sản phẩm có giá từ " + min_price + " đến " + max_price + ".");
        }
    }

    public void findProductsByStockRange(Scanner scanner) {
        System.out.print("Nhập khoảng tồn kho đầu: ");
        int min_stock = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập khoảng tồn kho cuối: ");
        int max_stock = Integer.parseInt(scanner.nextLine());

        List<Product> products = productService.findProductByStockRange(min_stock, max_stock);
        if (products != null) {
            TableProductUtil.printProductTableHeader(products, "DANH SÁCH SẢN PHẨM CÓ TỒN KHO TƯ " + min_stock + "-" + max_stock);
            products.forEach(System.out::println);
            TableProductUtil.printProductTableFooter();
        }
    }
}
