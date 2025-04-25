package ra.edu.presentation;

import ra.edu.MainApplication;
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
            System.out.println(Color.BLUE + "==========" + Color.PURPLE + " QUẢN LÍ ĐƠN HÀNG " + Color.BLUE + "=========\n" +
                    Color.CYAN + "1. Hiển thị danh sách hóa đơn\n" +
                    "2. Thêm mới hóa đơn\n" +
                    "3. Tìm kiếm hóa đơn\n" +
                    "0. Quay lại menu chính\n" +
                    Color.BLUE + "=========================================" + Color.RESET);
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
                case 0:
                    System.out.println(Color.GREEN + "Thoát menu hóa đơn..." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
                    break;
            }
        } while (true);
    }

    public void displayInvoices(Scanner scanner) {
        List<Invoice> invoices = invoiceService.findAll();
        if (invoices.isEmpty()) {
            System.out.println(Color.RED + "Không có hóa đơn, vui lòng thêm hóa đơn trước!" + Color.RESET);
            return;
        }
        List<Invoice> invoiceInPage = invoiceService.findPerPage(MainApplication.FIRST_PAGE);
        int currentPage = MainApplication.FIRST_PAGE;
        int totalInvoices = invoices.size();
        int totalPage = (int) Math.ceil((double) totalInvoices / MainApplication.PAGE_SIZE);

        do {
            TableInvoiceUtil.printInvoiceTableHeader(invoices, "DANH SÁCH CÁC ĐƠN HÀNG HIỆN CÓ");
            invoices.forEach(System.out::println);
            TableInvoiceUtil.printInvoiceTableFooter();
            System.out.println("Trang " + currentPage + "/" + totalPage);
            System.out.println(Color.YELLOW + "1. Prev \t 2. Chọn trang \t 3. Next \t 4. Xem chi tiết hóa đơn \t 0. Thoát" + Color.RESET);
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1 -> {
                    if (currentPage > MainApplication.FIRST_PAGE) {
                        invoiceInPage = invoiceService.findPerPage(currentPage - 1);
                        currentPage--;
                    } else {
                        System.out.println(Color.RED + "Đây là trang đầu tiên, không thể chuyển." + Color.RESET);
                    }
                }
                case 2 -> {
                    do {
                        int page = InputValidator.validateInputValue(scanner, "Nhập trang muốn xem: ", Integer.class);
                        if (page >= 1 && page <= totalPage) {
                            invoiceInPage = invoiceService.findPerPage(page);
                            currentPage = page;
                            break;
                        } else {
                            System.out.println(Color.RED + "Số trang không hợp lệ, vui lòng nhập lại." + Color.RESET);
                        }
                    } while (true);
                }
                case 3 -> {
                    if (currentPage < totalPage) {
                        invoiceInPage = invoiceService.findPerPage(currentPage + 1);
                        currentPage++;
                    } else {
                        System.out.println(Color.RED + "Đây là trang cuối cùng, không thể chuyển" + Color.RESET);
                    }
                }
                case 4 -> displayInvoiceDetail(scanner, invoices);
                case 0 -> {
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                }
                default -> System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
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

                System.out.println(Color.PURPLE + "Bạn muốn làm gì tiếp:\n" +
                        Color.CYAN + "1. Xem chi tiết hóa đơn khác\n" +
                        "0. Thoát" + Color.RESET);
                choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                switch (choice) {
                    case 1:
                        break;
                    case 0:
                        System.out.println(Color.GREEN + "Thoát xem chi tiết..." + Color.RESET);
                        return;
                    default:
                        System.out.println(Color.RED + "Lựa chọn không hợp lệ, vui lòng nhập lại" + Color.RESET);
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
                    System.out.println(Color.PURPLE + "Bạn có muốn thêm sản phẩm cho hóa đơn này không:\n" +
                            Color.CYAN + "1. Có\n" +
                            "0. Không" + Color.RESET);
                    choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                    switch (choice) {
                        case 1:
                            InvoiceDetail invoiceDetail = new InvoiceDetail();
                            boolean updated = invoiceDetail.inputData(scanner, invoice);
                            if (!updated) {
                                break;
                            }
                            boolean flag = invoiceDetailService.save(invoiceDetail);
                            if (flag) {
                                System.out.println(Color.GREEN + "Thêm thành công sản phẩm có mã hóa đơn " + invoiceDetail.getProductId() + " vào hóa đơn " + invoiceDetail.getInvoiceId() + Color.RESET);
                                invoiceService.update(invoice);
                            } else {
                                System.out.println(Color.RED + "Có lỗi xảy ra trong quá trình thực hiện" + Color.RESET);
                            }
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println(Color.RED + "Lựa chọn không hợp lệ, vui lòng nhập lại" + Color.RESET);
                            break;
                    }
                } while (choice != 0);
            } else {
                System.out.println(Color.RED + "Có lỗi xảy ra trong quá trình thực hiện" + Color.RESET);
                return;
            }
        }
    }

    public void menuFindInvoice(Scanner scanner) {
        System.out.println(Color.PURPLE + "Bạn muốn tìm kiếm hóa đơn theo phương thức nào:\n" +
                Color.CYAN + "1. Theo tên khách hàng\n" +
                "2. Theo ngày/tháng/năm\n" +
                "0. Quay lại menu hóa đơn" + Color.RESET);
        int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
        switch (choice) {
            case 1:
                findInvoiceByName(scanner);
                break;
            case 2:
                findInvoiceByDateAmount(scanner);
                break;
            case 0:
                System.out.println(Color.GREEN + "Thoát menu tìm kiếm..." + Color.RESET);
                return;
            default:
                System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng nhập lại" + Color.RESET);
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

                System.out.println(Color.PURPLE + "Bạn muốn làm gì tiếp:\n" +
                        Color.CYAN + "1. Xem chi tiết hóa đơn\n" +
                        "0. Thoát" + Color.RESET);
                int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
                switch (choice) {
                    case 1:
                        displayInvoiceDetail(scanner, invoices);
                        break;
                    case 0:
                        System.out.println(Color.GREEN + "Đang thoát..." + Color.RESET);
                        return;
                    default:
                        System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng chọn lại" + Color.RESET);
                        break;
                }
            } while (true);
        } else {
            System.out.println(Color.RED + "Không có hóa đơn của khách hàng có tên " + name + Color.RESET);
        }
    }

    public void findInvoiceByDateAmount(Scanner scanner) {
        LocalDate startDate = InputValidator.validateInputValue(scanner, "Nhập khoảng thời gian bắt đầu: ", LocalDate.class);
        LocalDate endDate = InputValidator.validateInputValue(scanner, "Nhập khoảng thời gian kết thúc: ", LocalDate.class);
        if (startDate.isAfter(endDate)) {
            System.out.println(Color.RED + "Khoảng kết thúc phải lớn hơn hoặc bằng khoảng bắt đầu" + Color.RESET);
            return;
        }
        List<Invoice> invoices = invoiceService.findInvoiceByDateCreated(startDate, endDate);
        if (!invoices.isEmpty()) {
            TableInvoiceUtil.printInvoiceTableHeader(invoices, "DANH SÁCH HÓA ĐƠN TỪ NGÀY " + startDate + "ĐẾN NGÀY " + endDate);
            invoices.forEach(System.out::println);
            TableInvoiceUtil.printInvoiceTableFooter();

            System.out.println(Color.PURPLE + "Bạn muốn làm gì tiếp:\n" +
                    Color.CYAN + "1. Xem chi tiết hóa đơn\n" +
                    "0. Thoát" + Color.RESET);
            int choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1:
                    displayInvoiceDetail(scanner, invoices);
                    break;
                case 0:
                    System.out.println(Color.GREEN + "Đang thoát..." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Lựa chọn của bạn không hợp lệ, vui lòng chọn lại" + Color.RESET);
                    break;
            }
        } else {
            System.out.println(Color.RED + "Không có hóa đơn nằm trong khoảng từ ngày " + startDate + " đến " + endDate + Color.RESET);
        }
    }
}
