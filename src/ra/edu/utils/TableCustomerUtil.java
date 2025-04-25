package ra.edu.utils;

import ra.edu.business.model.Customer;

import java.util.List;

public class TableCustomerUtil {
    public static void printCustomerTableHeader(List<Customer> customers, String title) {
        if (customers == null || customers.isEmpty()) {
            System.err.println("Không có dữ liệu để in tiêu đề.");
            return;
        }

        // Bước 1: Tính toán độ rộng cột
        int idWidth = "ID".length();
        int nameWidth = "Tên khách hàng".length();
        int phoneWidth = "Số điện thoại".length();
        int emailWidth = "Email".length();
        int addressWidth = "Địa chỉ".length();

        for (Customer customer: customers) {
            idWidth = Math.max(idWidth, String.valueOf(customer.getId()).length());
            nameWidth = Math.max(nameWidth, customer.getName().length());
            phoneWidth = Math.max(phoneWidth, customer.getPhone().length());
            emailWidth = Math.max(emailWidth, customer.getEmail().length());
            addressWidth = Math.max(addressWidth, customer.getAddress().length());
        }

        // Gán lại width cho Customer
        Customer.idWidth = idWidth;
        Customer.nameWidth = nameWidth;
        Customer.phoneWidth = phoneWidth;
        Customer.emailWidth = emailWidth;
        Customer.addressWidth = addressWidth;

        int totalWidth = idWidth + nameWidth + phoneWidth + emailWidth + addressWidth + 15;

        // Bước 2: In tiêu đề
        System.out.println(Color.BLUE + "+" + "-".repeat(((totalWidth - title.length()) / 2) - 2) + Color.CYAN +  " " + title + " " + Color.BLUE + "-".repeat(((totalWidth - title.length()) - 2) / 2) + "+" + Color.RESET);

        // Bước 3: In tên cột
        System.out.printf(Color.YELLOW + "| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + phoneWidth + "s | %"
                + emailWidth + "s | %-" + addressWidth + "s |\n" + Color.RESET,
                "ID", "Tên khách hàng", "Số điện thoại", "Email", "Địa chỉ");

        // Bước 4: In đường kẻ
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }

    public static void printCustomerTableFooter() {
        int totalWidth = Customer.idWidth + Customer.nameWidth + Customer.phoneWidth + Customer.emailWidth + Customer.addressWidth + 15;
        System.out.println(Color.BLUE + "+" + "-".repeat(totalWidth - 1) + "+" + Color.RESET);
    }
}
