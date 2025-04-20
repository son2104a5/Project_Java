package ra.edu.utils;

import ra.edu.business.model.Product;

import java.util.List;

public class TableProductUtil {
    public static void printProductTableHeader(List<Product> products, String title) {
        if (products == null || products.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int nameWidth = "Tên sản phẩm".length();
        int brandWidth = "Nhãn hàng".length();
        int priceWidth = "Giá".length();
        int stockWidth = "Tồn kho".length();

        for (Product product : products) {
            idWidth = Math.max(idWidth, String.valueOf(product.getId()).length());
            nameWidth = Math.max(nameWidth, product.getName().length());
            brandWidth = Math.max(brandWidth, product.getBrand().length());
            priceWidth = Math.max(priceWidth, String.format("%.2f", product.getPrice()).length());
            stockWidth = Math.max(stockWidth, String.valueOf(product.getStock()).length());
        }

        // Gán lại width cho Product
        Product.idWidth = idWidth;
        Product.nameWidth = nameWidth;
        Product.brandWidth = brandWidth;
        Product.priceWidth = priceWidth;
        Product.stockWidth = stockWidth;

        int totalWidth = idWidth + nameWidth + brandWidth + priceWidth + stockWidth + 15;

        // Bước 2: In tiêu đề
        System.out.println("+" + "-".repeat(((totalWidth - title.length()) / 2) - 2) + " " + title + " " + "-".repeat(((totalWidth - title.length()) - 2) / 2) + "+");

        // Bước 3: In tên cột
        System.out.printf("| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + brandWidth + "s | %"
                + priceWidth + "s | %-" + stockWidth + "s |\n", "ID", "Tên sản phẩm", "Nhãn hàng", "Giá", "Tồn kho");

        // Bước 4: In đường kẻ
        System.out.println("+" + "-".repeat(totalWidth - 1) + "+");
    }

    public static void printProductTableFooter() {
        int totalWidth = Product.idWidth + Product.nameWidth + Product.brandWidth + Product.priceWidth + Product.stockWidth + 15;
        System.out.println("+" + "-".repeat(totalWidth - 1) + "+");
    }
}
