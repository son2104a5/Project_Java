package ra.edu.utils;

import ra.edu.business.model.InvoiceDetail;
import ra.edu.business.model.Product;
import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;

import java.util.List;

public class TableInvoiceDetailUtil {
    private final ProductService productService = new ProductServiceImp();

    public static void printInvoiceDetailTableHeader(List<InvoiceDetail> invoiceDetails, String title) {
        ProductService productService = new ProductServiceImp();
        if (invoiceDetails == null || invoiceDetails.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int invoiceIdWidth = "Mã hóa đơn".length();
        int productNameWidth = "Tên sản phẩm".length();
        int quantityWidth = "Số lượng".length();
        int unitPriceWidth = "Thành tiền".length();

        for (InvoiceDetail invoiceDetail: invoiceDetails) {
            Product product = productService.findProductById(invoiceDetail.getProductId());
            idWidth = Math.max(idWidth, String.valueOf(invoiceDetail.getId()).length());
            invoiceIdWidth = Math.max(invoiceIdWidth, String.format(String.valueOf(invoiceDetail.getInvoiceId())).length());
            productNameWidth = Math.max(productNameWidth, product.getName().length());
            quantityWidth = Math.max(quantityWidth, String.format(String.valueOf(invoiceDetail.getQuantity())).length());
            unitPriceWidth = Math.max(unitPriceWidth, String.format("%.2f", invoiceDetail.getUnitPrice()).length());
        }

        // Gán lại width cho InvoiceDetail
        InvoiceDetail.idWidth = idWidth;
        InvoiceDetail.invoiceIdWidth = invoiceIdWidth;
        InvoiceDetail.productNameWidth = productNameWidth;
        InvoiceDetail.quantityWidth = quantityWidth;
        InvoiceDetail.unitPriceWidth = unitPriceWidth;

        int totalWidth = idWidth + InvoiceDetail.invoiceIdWidth + InvoiceDetail.productNameWidth + InvoiceDetail.quantityWidth + InvoiceDetail.unitPriceWidth + 15;

        // Bước 2: In tiêu đề
        System.out.println(Color.BLUE + "+" + "-".repeat(((totalWidth - title.length()) / 2) - 2) + " " + Color.CYAN + title + " " + Color.BLUE + "-".repeat(((totalWidth - title.length()) - 2) / 2) + "+" + Color.RESET);

        // Bước 3: In tên cột
        System.out.printf(Color.YELLOW + "| %-" + idWidth + "s | %-" + InvoiceDetail.invoiceIdWidth + "s | %-" + InvoiceDetail.productNameWidth + "s | %"
                        + InvoiceDetail.quantityWidth + "s | %-" + InvoiceDetail.unitPriceWidth + "s |\n" + Color.RESET, "ID", "Mã hóa đơn","Tên sản phẩm", "Số lượng", "Thành tiền");

        // Bước 4: In đường kẻ
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }

    public static void printInvoiceDetailTableFooter() {
        int totalWidth = InvoiceDetail.idWidth + InvoiceDetail.invoiceIdWidth + InvoiceDetail.productNameWidth + InvoiceDetail.quantityWidth + InvoiceDetail.unitPriceWidth + 15;
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }
}
