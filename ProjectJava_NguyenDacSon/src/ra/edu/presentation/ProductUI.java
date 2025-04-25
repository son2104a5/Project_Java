package ra.edu.presentation;

import ra.edu.MainApplication;
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

            System.out.println(Color.BLUE + "=========" + Color.PURPLE + " MENU QUẢN LÍ SẢN PHẨM " + Color.BLUE + "=========\n" +
                    Color.CYAN + "1. Hiển thị danh sách sản phẩm\n" +
                    "2. Thêm sản phẩm mới\n" +
                    "3. Cập nhật thông tin sản phẩm\n" +
                    "4. Xóa sản phẩm theo ID\n" +
                    "5. Tìm kiếm theo Brand\n" +
                    "6. Tìm kiếm theo khoảng giá\n" +
                    "7. Tìm kiếm theo tồn kho\n" +
                    "0. Quay lại menu chính\n" +
                    Color.BLUE + "=========================================" + Color.RESET);
            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1 -> productUI.displayProducts(scanner);
                case 2 -> productUI.createProducts(scanner);
                case 3 -> productUI.updateProduct(scanner);
                case 4 -> productUI.deleteProduct(scanner);
                case 5 -> productUI.findProductsByBrand(scanner);
                case 6 -> productUI.findProductsByPriceAmount(scanner);
                case 7 -> productUI.findProductsByStockRange(scanner);
                case 0 -> {
                    System.out.println(Color.GREEN + "Thoát menu quản lí sản phẩm..." + Color.RESET);
                    return;
                }
                default ->
                        System.out.println(Color.RED + "Lựa chọn của bạn không hợp lê, vui lòng nhập lại" + Color.RESET);
            }
        } while (true);
    }

    public void displayProducts(Scanner scanner) {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            System.out.println(Color.RED + "Không tồn tại sản phẩm nào, vui lòng thêm sản phẩm" + Color.RESET);
            return;
        }
        displayProductInPage(scanner, products, "DANH SÁCH TẤT CẢ SẢN PHẨM");
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
                displayProducts(scanner);
            } else {
                System.out.println(Color.RED + "Có lỗi xảy ra trong quá trình thực hiện" + Color.RESET);
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
                System.out.println(Color.PURPLE + "Bạn muốn thay đổi thông tin nào:\n" +
                        Color.CYAN + "1. Tên sản phẩm\n" +
                        "2. Nhãn hiệu\n" +
                        "3. Giá sản phẩm\n" +
                        "4. Số lượng tồn kho\n" +
                        "0. Thoát cập nhật" + Color.RESET);
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
                                System.out.println(Color.RED + "Giá trị nhập vào phải lớn hơn 0" + Color.RESET);
                        } while (flag);
                    }
                    case 4 -> {
                        boolean flag;
                        do {
                            int value = InputValidator.validateInputValue(scanner, "Nhập số lượng tồn kho mới:", Integer.class);
                            flag = ProductValidator.validateDataHasNotPositiveValue(value);
                            if (!flag) {
                                product.setStock(value);
                            } else
                                System.out.println(Color.RED + "Giá trị nhập vào phải lớn hơn 0" + Color.RESET);
                        } while (flag);
                    }
                    case 0 -> System.out.println(Color.GREEN + "Thoát chức năng cập nhật...." + Color.RESET);
                    default ->
                            System.out.println(Color.RED + "Chức năng không hợp lệ, vui lòng nhập lại" + Color.RESET);
                }
                if (choice == 0) {
                    boolean success = productService.update(product);
                    if (success) {
                        displayProducts(scanner);
                    } else {
                        System.out.println(Color.RED + "Có lỗi xảy ra trong quá trình thực hiện" + Color.RESET);
                    }
                }
            } while (choice != 0);
        } else {
            System.out.println(Color.RED + "Không tìm thấy sản phẩm có ID " + id + "." + Color.RESET);
        }
    }

    public void deleteProduct(Scanner scanner) {
        int id = InputValidator.validateInputValue(scanner, "Nhập ID sản phẩm muốn xóa: ", Integer.class);
        Product product = productService.findProductById(id);
        if (product != null && product.isStatus()) {
            do {
                int choice = InputValidator.validateInputValue(scanner, "Bạn thực sự muốn xóa sản phẩm này? (1: Yes, 2: No): ", Integer.class);
                switch (choice) {
                    case 1:
                        product = new Product();
                        product.setId(id);
                        boolean success = productService.delete(product);
                        if (success) {
                            displayProducts(scanner);
                        } else {
                            System.out.println(Color.RED + "Có lỗi xảy ra trong quá trình thực hiện" + Color.RESET);
                        }
                        return;
                    case 2:
                        System.out.println(Color.GREEN + "Đã hủy xóa sản phẩm có id " + id + "." + Color.RESET);
                        return;
                    default:
                        System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
                        break;
                }
            } while (true);
        } else {
            System.out.println(Color.RED + "Không tìm thấy sản phẩm có ID: " + id + "." + Color.RESET);
        }
    }

    public void findProductsByBrand(Scanner scanner) {
        String brand = InputValidator.validateInputValue(scanner, "Nhập nhãn hiệu sản phẩm muốn tìm: ", String.class);
        List<Product> products = productService.findProductByBrand(brand);
        if (products != null) {
            displayProductInPage(scanner, products, "DANH SÁCH SẢN PHẨM CÓ BRAND = " + brand);
        } else {
            System.out.println(Color.RED + "Không tìm thấy sản phẩm có brand: " + brand + "." + Color.RESET);
        }
    }

    public void findProductsByPriceAmount(Scanner scanner) {
        Double min_price = InputValidator.validateInputValue(scanner, "Nhập giá bắt đầu: ", Double.class);
        Double max_price = InputValidator.validateInputValue(scanner, "Nhập giá kết thúc: ", Double.class);
        if (max_price < min_price) {
            System.out.println(Color.RED + "Giá kết thúc phải lớn hơn giá bắt đầu");
            return;
        }
        List<Product> products = productService.findProductByPriceAmount(min_price, max_price);
        if (products != null) {
            displayProductInPage(scanner, products, "DANH SÁCH SẢN PHẨM CÓ GIÁ TỪ " + min_price + "-" + max_price);
        } else {
            System.out.println(Color.RED + "Không tìm thấy sản phẩm có giá từ " + min_price + " đến " + max_price + "." + Color.RESET);
        }
    }

    public void findProductsByStockRange(Scanner scanner) {
        System.out.print("Nhập khoảng tồn kho đầu: ");
        int min_stock = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập khoảng tồn kho cuối: ");
        int max_stock = Integer.parseInt(scanner.nextLine());

        List<Product> products = productService.findProductByStockRange(min_stock, max_stock);
        if (products != null) {
            displayProductInPage(scanner, products, "DANH SÁCH SẢN PHẨM CÓ TỒN KHO TƯ " + min_stock + "-" + max_stock);
        }
    }

    public void displayProductInPage(Scanner scanner, List<Product> products, String title) {
        List<Product> productInPage = productService.findPerPage(MainApplication.FIRST_PAGE);
        int currentPage = MainApplication.FIRST_PAGE;
        int totalProducts = products.size();
        int totalPage = (int) Math.ceil((double) totalProducts / MainApplication.PAGE_SIZE);

        do {
            TableProductUtil.printProductTableHeader(products, title);
            productInPage.forEach(System.out::println);
            TableProductUtil.printProductTableFooter();
            System.out.println("Trang " + currentPage + "/" + totalPage);
            System.out.println(Color.YELLOW + "1. Prev \t 2. Chọn trang \t 3. Next \t 0. Thoát" + Color.RESET);
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1 -> {
                    if (currentPage > MainApplication.FIRST_PAGE) {
                        productInPage = productService.findPerPage(currentPage - 1);
                        currentPage--;
                    } else {
                        System.out.println(Color.RED + "Đây là trang đầu tiên, không thể chuyển." + Color.RESET);
                    }
                }
                case 2 -> {
                    do {
                        int page = InputValidator.validateInputValue(scanner, "Nhập trang muốn xem: ", Integer.class);
                        if (page >= 1 && page <= totalPage) {
                            productInPage = productService.findPerPage(page);
                            currentPage = page;
                            break;
                        } else {
                            System.out.println(Color.RED + "Số trang không hợp lệ, vui lòng nhập lại." + Color.RESET);
                        }
                    } while (true);
                }
                case 3 -> {
                    if (currentPage < totalPage) {
                        productInPage = productService.findPerPage(currentPage + 1);
                        currentPage++;
                    } else {
                        System.out.println(Color.RED + "Đây là trang cuối cùng, không thể chuyển" + Color.RESET);
                    }
                }
                case 0 -> {
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                }
                default ->
                        System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
            }
        } while (true);
    }
}
