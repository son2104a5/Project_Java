package ra.edu.utils;

import ra.edu.business.model.Product;

import java.util.List;

public class TableProductUtil {
    public static void printProductTableHeader(List<Product> fullList, String title) {
        if (fullList == null || fullList.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        int idWidth = "ID".length();
        int nameWidth = "Tên sản phẩm".length();
        int brandWidth = "Nhãn hàng".length();
        int priceWidth = "Giá".length();
        int stockWidth = "Tồn kho".length();

        for (Product product : fullList) {
            idWidth = Math.max(idWidth, String.valueOf(product.getId()).length());
            nameWidth = Math.max(nameWidth, product.getName().length());
            brandWidth = Math.max(brandWidth, product.getBrand().length());
            priceWidth = Math.max(priceWidth, String.format("%.0f", product.getPrice()).length());
            stockWidth = Math.max(stockWidth, String.valueOf(product.getStock()).length());
        }

        Product.idWidth = idWidth;
        Product.nameWidth = nameWidth;
        Product.brandWidth = brandWidth;
        Product.priceWidth = priceWidth;
        Product.stockWidth = stockWidth;

        int totalWidth = idWidth + nameWidth + brandWidth + priceWidth + stockWidth + 16;
        totalWidth = Math.max(totalWidth, title.length() + 2);

        System.out.println(Color.BLUE + "+" + "-".repeat(((totalWidth - title.length()) / 2) - 2) + Color.CYAN + " " + title + " " + Color.BLUE + "-".repeat(((totalWidth - title.length()) - 2) / 2) + "+" + Color.RESET);
        System.out.printf(Color.YELLOW + "| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + brandWidth + "s | %"
                + priceWidth + "s | %-" + stockWidth + "s  |\n" + Color.RESET, "ID", "Tên sản phẩm", "Nhãn hàng", "Giá", "Tồn kho");
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }


    public static void printProductTableFooter() {
        int totalWidth = Product.idWidth + Product.nameWidth + Product.brandWidth + Product.priceWidth + Product.stockWidth + 16;
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }
}
