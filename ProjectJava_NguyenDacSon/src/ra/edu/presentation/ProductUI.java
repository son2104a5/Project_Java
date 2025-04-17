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
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {}
            case 7 -> {}
            case 8 -> {
                return;
            }
            default -> {}
        }
    }

    public void displayProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            System.err.println("Không tồn tại sản phẩm nào, vui lòng thêm sản phẩm");
            return;
        }
        products.forEach(System.out::println);
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
}
