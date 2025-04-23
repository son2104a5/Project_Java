package ra.edu.presentation;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;
import ra.edu.business.service.invoice.InvoiceService;
import ra.edu.business.service.invoice.InvoiceServiceImp;
import ra.edu.business.service.invoice_detail.InvoiceDetailService;
import ra.edu.business.service.invoice_detail.InvoiceDetailServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableInvoiceDetailUtil;
import ra.edu.utils.TableInvoiceUtil;
import ra.edu.validate.objectValidator.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class InvoiceUI {
    private final InvoiceService invoiceService;

    private final InvoiceDetailService invoiceDetailService;

    public InvoiceUI() {
        invoiceService = new InvoiceServiceImp();
        invoiceDetailService = new InvoiceDetailServiceImp();
    }

    public static void display(Scanner scanner) {
        InvoiceUI invoiceUI = new InvoiceUI();
        do {
            System.out.println("========== QUẢN LÍ ĐƠN HÀNG =========\n" +
                    "1. Hiển thị danh sách hóa đơn\n" +
                    "2. Thêm mới hóa đơn\n" +
                    "3. Tìm kiếm hóa đơn\n" +
                    "4. Quay lại menu chính\n" +
                    "=========================================");
            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1:
                    invoiceUI.displayInvoices(scanner);
                    break;
                case 2:
                    invoiceUI.addInvoice(scanner);
                    break;
                case 3:
                    invoiceUI.menuFindInvoice(scanner);
                    break;
                case 4:
                    System.out.println(Color.GREEN + "Thoát menu hóa đơn..." + Color.RESET);
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }

    public void displayInvoices(Scanner scanner) {
        List<Invoice> invoices = invoiceService.findAll();
        if (invoices.isEmpty()) {
            System.err.println("Không có hóa đơn, vui lòng thêm hóa đơn trước!");
            return;
        }
        do {
            TableInvoiceUtil.printInvoiceTableHeader(invoices, "DANH SÁCH CÁC ĐƠN HÀNG HIỆN CÓ");
            invoices.forEach(System.out::println);
            TableInvoiceUtil.printInvoiceTableFooter();

            System.out.println("Bạn muốn làm gì:\n" +
                    "1. Xem chi tiết hóa đơn\n" +
                    "2. Thoát");
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1:
                    displayInvoiceDetail(scanner, invoices);
                    break;
                case 2:
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }

    public void displayInvoiceDetail(Scanner scanner, List<Invoice> invoices) {
        int choice = 0;
        do {
            boolean flag = false;
            int id = InputValidator.validateInputValue(scanner, "Nhập ID của hóa đơn muốn xem: ", Integer.class);
            for (Invoice invoice: invoices) {
                if (invoice.getId() == id) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                List<InvoiceDetail> invoiceDetails = invoiceService.findInvoiceById(id);

                TableInvoiceDetailUtil.printInvoiceDetailTableHeader(invoiceDetails, "DANH SÁCH SẢN PHẨM CÓ TRONG ĐƠN HÀNG CÓ ID " + id);
                invoiceDetails.forEach(System.out::println);
                TableInvoiceDetailUtil.printInvoiceDetailTableFooter();

                System.out.println("Bạn muốn làm gì tiếp:\n" +
                        "1. Xem chi tiết hóa đơn khác\n" +
                        "2. Thoát");
                choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        System.out.println(Color.GREEN + "Thoát xem chi tiết..." + Color.RESET);
                        break;
                    default:
                        System.err.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                        break;
                }
            }
        } while (choice != 2);
    }

    public void addInvoice(Scanner scanner) {
        int n = InputValidator.validateInputValue(scanner, "Nhập số hóa đơn muốn thêm: ", Integer.class);

        for (int i = 0; i < n; i++) {
            Invoice invoice = new Invoice();
            int size = invoiceService.findAll().size() + 1;
            invoice.inputData(scanner, size);
            boolean success = invoiceService.save(invoice);
            if (success) {
                int choice;
                do {
                    System.out.println("Bạn có muốn thêm sản phẩm cho hóa đơn này không:\n" +
                            "1. Có\n" +
                            "2. Không");
                    choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                    switch (choice) {
                        case 1:
                            InvoiceDetail invoiceDetail = new InvoiceDetail();
                            invoiceDetail.inputData(scanner, invoice);
                            boolean flag = invoiceDetailService.save(invoiceDetail);
                            if (flag) {
                                System.out.println("Thêm thành công sản phẩm có mã hóa đơn " + invoiceDetail.getProductId() + " vào hóa đơn " + invoiceDetail.getInvoiceId());
                                invoiceService.update(invoice);
                            } else {
                                System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
                            }
                            break;
                        case 2:
                            System.out.println("========================================================");
                            break;
                        default:
                            System.err.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                            break;
                    }
                } while (choice != 2);
            } else {
                System.err.println("Có lỗi xảy ra trong quá trình thực hiện");
                return;
            }
        }
    }

    public void menuFindInvoice(Scanner scanner) {
        System.out.println("Bạn muốn tìm kiếm hóa đơn theo phương thức nào:\n" +
                "1. Theo tên khách hàng\n" +
                "2. Theo ngày/tháng/năm\n" +
                "3. Quay lại menu hóa đơn");
        int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
        switch (choice) {
            case 1:
                findInvoiceByName(scanner);
                break;
            case 2:
                findInvoiceByDateAmount(scanner);
                break;
            case 3:
                System.out.println(Color.GREEN + "Thoát menu tìm kiếm..." + Color.RESET);
                return;
            default:
                System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng nhập lại");
        }
    }

    public void findInvoiceByName(Scanner scanner) {
        String name = InputValidator.validateInputValue(scanner, "Nhập tên khách hàng: ", String.class);

        List<Invoice> invoices = invoiceService.findInvoiceByCustomerName(name);
        if (!invoices.isEmpty()) {
            do {
                TableInvoiceUtil.printInvoiceTableHeader(invoices, "DANH SÁCH HÓA ĐƠN CỦA KHÁCH HÀNG " + name.toUpperCase());
                invoices.forEach(System.out::println);
                TableInvoiceUtil.printInvoiceTableFooter();

                System.out.println("Bạn muốn làm gì tiếp:\n" +
                        "1. Xem chi tiết hóa đơn\n" +
                        "2. Thoát");
                int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                switch (choice) {
                    case 1:
                        displayInvoiceDetail(scanner, invoices);
                        break;
                    case 2:
                        System.out.println(Color.GREEN + "Đang thoát..." + Color.RESET);
                        return;
                    default:
                        System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng chọn lại");
                        break;
                }
            } while (true);
        } else {
            System.err.println("Không có khách hàng có tên " + name);
        }
    }

    public void findInvoiceByDateAmount(Scanner scanner) {
        LocalDate higherDate = InputValidator.validateInputValue(scanner, "Nhập khoảng ngày tháng dưới: ", LocalDate.class);
        LocalDate lowerDate = InputValidator.validateInputValue(scanner, "Nhập khoảng ngày tháng trên: ", LocalDate.class);

        List<Invoice> invoices = invoiceService.findInvoiceByDateCreated(higherDate, lowerDate);
        if (!invoices.isEmpty()) {
            TableInvoiceUtil.printInvoiceTableHeader(invoices, "DANH SÁCH HÓA ĐƠN TỪ NGÀY " + higherDate + "ĐẾN NGÀY " + lowerDate);
            invoices.forEach(System.out::println);
            TableInvoiceUtil.printInvoiceTableFooter();

            System.out.println("Bạn muốn làm gì tiếp:\n" +
                    "1. Xem chi tiết hóa đơn\n" +
                    "2. Thoát");
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1:
                    displayInvoiceDetail(scanner, invoices);
                    break;
                case 2:
                    System.out.println(Color.GREEN + "Đang thoát..." + Color.RESET);
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lệ, vui lòng chọn lại");
                    break;
            }
        } else {
            System.err.println("Không có hóa đơn nằm trong khoảng từ ngày " + higherDate + " đến " + lowerDate);
        }
    }
}
