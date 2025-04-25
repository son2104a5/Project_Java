package ra.edu.utils;

import ra.edu.business.model.Customer;
import ra.edu.business.model.Invoice;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;

import java.util.List;

public class TableInvoiceUtil {
    private final CustomerService customerService = new CustomerServiceImp();

    public static void printInvoiceTableHeader(List<Invoice> invoices, String title) {
        CustomerService customerService = new CustomerServiceImp();
        if (invoices == null || invoices.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int customerNameWidth = "Tên khách hàng".length();
        int createdAtWidth = "Ngày tạo đơn".length();
        int totalAmountWidth = "Tổng tiền".length();

        for (Invoice invoice : invoices) {
            Customer customer = customerService.findCustomerByID(invoice.getCustomerId());
            idWidth = Math.max(idWidth, String.valueOf(invoice.getId()).length());
            customerNameWidth = Math.max(customerNameWidth, customer.getName().length());
            createdAtWidth = Math.max(createdAtWidth, invoice.getCreatedAt() != null ? invoice.getCreatedAt().toString().length() : 0);
            totalAmountWidth = Math.max(totalAmountWidth, invoice.getTotalAmount() != null ? String.format("%.2f", invoice.getTotalAmount()).length() : 0);
        }

        // Gán lại width cho Invoice
        Invoice.idWidth = idWidth;
        Invoice.customerNameWidth = customerNameWidth;
        Invoice.createdAtWidth = createdAtWidth;
        Invoice.totalAmountWidth = totalAmountWidth;

        // Bước 2: Tính toán tổng độ rộng
        int totalWidth = idWidth + customerNameWidth + createdAtWidth + totalAmountWidth + 15;
        // Đảm bảo totalWidth đủ lớn để chứa title
        totalWidth = Math.max(totalWidth, title.length() + 2); // +4 cho "+ ", " +"

        // Bước 3: In tiêu đề
        int padding = (totalWidth - title.length() - 6) / 2; // -4 cho "+ ", " +"
        String leftBorder = "-".repeat(Math.max(0, padding));
        String rightBorder = "-".repeat(Math.max(0, padding + (totalWidth - title.length() - 4) % 2)); // Xử lý trường hợp lẻ
        System.out.println(Color.BLUE + "+" + leftBorder  + Color.CYAN + " " + title + " " + Color.BLUE + rightBorder + "+" + Color.RESET);

        // Bước 4: In tên cột
        System.out.printf(Color.YELLOW + "| %-" + idWidth + "s | %-" + customerNameWidth + "s | %-" + createdAtWidth + "s | %-" + totalAmountWidth + "s |\n" + Color.RESET, "ID" , "Tên khách hàng", "Ngày tạo đơn", "Tổng tiền");

        // Bước 5: In đường kẻ
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 4) + "+" + Color.RESET);
    }

    public static void printInvoiceTableFooter() {
        int totalWidth = Invoice.idWidth + Invoice.customerNameWidth + Invoice.createdAtWidth + Invoice.totalAmountWidth + 15;
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 4) + "+" + Color.RESET);
    }
}