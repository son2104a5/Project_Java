package ra.edu.presentation;

import ra.edu.business.model.Product;
import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;

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
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> productUI.displayProducts();
                case 2 -> productUI.createProducts(scanner);
                case 3 -> productUI.updateProduct(scanner);
                case 4 -> productUI.deleteProduct(scanner);
                case 5 -> productUI.findProductsByBrand(scanner);
                case 6 -> productUI.findProductsByPriceAmount(scanner);
                case 7 -> productUI.findProductsByStockRange(scanner);
                case 8 -> {
                    System.out.println("Thoát menu quản lí sản phẩm...");
                    return;
                }
                default -> {}
            }
        } while (true);
    }

    public void displayProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            System.err.println("Không tồn tại sản phẩm nào, vui lòng thêm sản phẩm");
            return;
        }
        System.out.println("======== DANH SÁCH SẢN PHẨM HIỆN CÓ ========");
        products.forEach(System.out::println);
        System.out.println("============================================");
    }

    public void createProducts(Scanner scanner) {
        System.out.print("Nhập số sản phẩm muốn thêm: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            Product product = new Product();
            product.inputData(scanner);
            boolean success = productService.save(product);
            if (success) {
                System.out.println("Thêm mới sản phẩm thành công.");
                System.out.println("=============================================");
            } else {
                System.err.println("Có lỗi xảy ra trong quá trình thêm mới!");
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
                System.out.println("Bạn muốn thay đổi thông tin nào:\n" +
                        "1. Tên sản phẩm\n" +
                        "2. Nhãn hiệu\n" +
                        "3. Giá sản phẩm\n" +
                        "4. Số lượng tồn kho\n" +
                        "0. Thoát cập nhật");
                System.out.print("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.println("Nhập tên mới:");
                        product.setName(scanner.nextLine());
                    }
                    case 2 -> {
                        System.out.println("Nhập nhãn hiệu mới:");
                        product.setBrand(scanner.nextLine());
                    }
                    case 3 -> {
                        System.out.println("Nhập giá mới:");
                        product.setPrice(Double.parseDouble(scanner.nextLine()));
                    }
                    case 4 -> {
                        System.out.println("Nhập số lượng tồn kho mới:");
                        product.setStock(Integer.parseInt(scanner.nextLine()));
                    }
                    case 0 -> {
                        System.out.println("Thoát chức năng cập nhật....");
                    }
                    default -> {
                        System.out.println("Chức năng không hợp lệ, vui lòng nhập lại");
                    }
                }
                boolean success = productService.save(product);
                if (success) {
                    System.out.println("Cập nhật thành công.");
                } else {
                    System.err.println("Có lỗi xảy ra trong quá trình cập nhật");
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
                System.out.println("Xóa sản phẩm thành công");
            } else {
                System.err.println("Có lỗi trong quá trình thực hiện");
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
            System.out.println("================== DANH SÁCH SẢN PHẨM CÓ BRAND = " + brand + " ==================");
            products.forEach(System.out::println);
            System.out.println("=================================================================================");
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
            System.out.println("====== DANH SÁCH SẢN PHẨM CÓ GIÁ TỪ " + min_price + " ĐẾN " + max_price + " ======");
            products.forEach(System.out::println);
            System.out.println("==================================================================================");
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
            System.out.println("==== DANH SÁCH SẢN PHẨM CÓ TỒN KHO TỪ " + min_stock + " ĐẾN " + max_stock + " ====");
            products.forEach(System.out::println);
            System.out.println("==================================================================================");
        }
    }
}
