package ra.edu.utils;

import ra.edu.business.model.InvoiceDetail;

import java.util.List;

public class TableInvoiceDetailUtil {
    public static void printInvoiceDetailTableHeader(List<InvoiceDetail> invoiceDetails, String title) {
        if (invoiceDetails == null || invoiceDetails.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int invoiceIdWidth = "Mã hóa đơn".length();
        int productIdWidth = "Mã sản phẩm".length();
        int quantityWidth = "Số lượng".length();
        int unitPriceWidth = "Thành tiền".length();

        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            idWidth = Math.max(idWidth, String.valueOf(invoiceDetail.getId()).length());
            InvoiceDetail.invoiceIdWidth = Math.max(InvoiceDetail.invoiceIdWidth, String.format(String.valueOf(invoiceDetail.getInvoiceId())).length());
            InvoiceDetail.productIdWidth = Math.max(InvoiceDetail.productIdWidth, String.format(String.valueOf(invoiceDetail.getProductId())).length());
            InvoiceDetail.quantityWidth = Math.max(InvoiceDetail.quantityWidth, String.format(String.valueOf(invoiceDetail.getQuantity())).length());
            InvoiceDetail.unitPriceWidth = Math.max(InvoiceDetail.unitPriceWidth, String.format(".2f", invoiceDetail.getUnitPrice()).length());
        }

        // Gán lại width cho InvoiceDetail
        InvoiceDetail.idWidth = idWidth;
        InvoiceDetail.invoiceIdWidth = invoiceIdWidth;
        InvoiceDetail.productIdWidth = productIdWidth;
        InvoiceDetail.quantityWidth = quantityWidth;
        InvoiceDetail.unitPriceWidth = unitPriceWidth;

        int totalWidth = idWidth + InvoiceDetail.invoiceIdWidth + InvoiceDetail.productIdWidth + InvoiceDetail.quantityWidth + InvoiceDetail.unitPriceWidth + 15;

        // Bước 2: In tiêu đề
        System.out.println("+" + "-".repeat(((totalWidth - title.length()) / 2) - 2) + " " + title + " " + "-".repeat(((totalWidth - title.length()) - 2) / 2) + "+");

        // Bước 3: In tên cột
        System.out.printf("| %-" + idWidth + "s | %-" + InvoiceDetail.invoiceIdWidth + "s | %-" + InvoiceDetail.productIdWidth + "s | %"
                + InvoiceDetail.quantityWidth + "s | %-" + InvoiceDetail.unitPriceWidth + "s |\n", "ID", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Thành tiền");

        // Bước 4: In đường kẻ
        System.out.println("+" + "-".repeat(totalWidth - 1) + "+");
    }

    public static void printInvoiceDetailTableFooter() {
        int totalWidth = InvoiceDetail.idWidth + InvoiceDetail.invoiceIdWidth + InvoiceDetail.productIdWidth + InvoiceDetail.quantityWidth + InvoiceDetail.unitPriceWidth + 15;
        System.out.println("+" + "-".repeat(totalWidth - 1) + "+");
    }
}
