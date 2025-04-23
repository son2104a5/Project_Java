package ra.edu.utils;

import ra.edu.business.model.Invoice;

import java.util.List;

public class TableInvoiceUtil {
    public static void printInvoiceTableHeader(List<Invoice> invoices, String title) {
        if (invoices == null || invoices.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int customerIdWidth = "Mã khách hàng".length();
        int createdAtWidth = "Ngày tạo đơn".length();
        int totalAmountWidth = "Tổng tiền".length();

        for (Invoice invoice : invoices) {
            idWidth = Math.max(idWidth, String.valueOf(invoice.getId()).length());
            customerIdWidth = Math.max(customerIdWidth, String.valueOf(invoice.getCustomerId()).length()); // Sửa lỗi
            createdAtWidth = Math.max(createdAtWidth, invoice.getCreatedAt() != null ? invoice.getCreatedAt().toString().length() : 0);
            totalAmountWidth = Math.max(totalAmountWidth, invoice.getTotalAmount() != null ? String.format("%.2f", invoice.getTotalAmount()).length() : 0);
        }

        // Gán lại width cho Invoice
        Invoice.idWidth = idWidth;
        Invoice.customerIdWidth = customerIdWidth;
        Invoice.createdAtWidth = createdAtWidth;
        Invoice.totalAmountWidth = totalAmountWidth;

        // Bước 2: Tính toán tổng độ rộng
        int totalWidth = idWidth + customerIdWidth + createdAtWidth + totalAmountWidth + 15;
        // Đảm bảo totalWidth đủ lớn để chứa title
        totalWidth = Math.max(totalWidth, title.length() + 4); // +4 cho "+ ", " +"

        // Bước 3: In tiêu đề
        int padding = (totalWidth - title.length() - 4) / 2; // -4 cho "+ ", " +"
        String leftBorder = "-".repeat(Math.max(0, padding));
        String rightBorder = "-".repeat(Math.max(0, padding + (totalWidth - title.length() - 4) % 2)); // Xử lý trường hợp lẻ
        System.out.println("+" + leftBorder + " " + title + " " + rightBorder + "+");

        // Bước 4: In tên cột
        System.out.printf("| %-" + idWidth + "s | %-" + customerIdWidth + "s | %-" + createdAtWidth + "s | %-" + totalAmountWidth + "s |\n",
                "ID", "Mã khách hàng", "Ngày tạo đơn", "Tổng tiền");

        // Bước 5: In đường kẻ
        System.out.println("+" + "-".repeat(totalWidth - 2) + "+");
    }

    public static void printInvoiceTableFooter() {
        int totalWidth = Invoice.idWidth + Invoice.customerIdWidth + Invoice.createdAtWidth + Invoice.totalAmountWidth + 15;
        System.out.println("+" + "-".repeat(totalWidth - 2) + "+");
    }
}