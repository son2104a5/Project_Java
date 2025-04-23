package ra.edu.presentation;

import ra.edu.business.model.Invoice;
import ra.edu.business.service.invoice.InvoiceService;
import ra.edu.business.service.statistic.StatisticService;
import ra.edu.business.service.statistic.StatisticServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableInvoiceUtil;
import ra.edu.validate.objectValidator.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StatisticUI {
    private final StatisticService statisticService;
    private final InvoiceUI invoiceUI;

    public StatisticUI() {
        statisticService = new StatisticServiceImp();
        invoiceUI = new InvoiceUI();
    }

    public static void display(Scanner scanner) {
        StatisticUI statisticUI = new StatisticUI();
        do {
            System.out.println("============= THỐNG KÊ DOANH THU =============\n" +
                    "1. Theo ngày\n" +
                    "2. Theo tháng\n" +
                    "3. Theo năm\n" +
                    "4. Quay lại menu chính\n" +
                    "==============================================");
            int choice = InputValidator.validateInputValue(scanner, "Chọn chức năng: ", Integer.class);
            switch (choice) {
                case 1:
                    statisticUI.totalDateRevenue(scanner);
                    break;
                case 2:
                    statisticUI.totalMonthRevenue(scanner);
                    break;
                case 3:
                    statisticUI.totalYearRevenue(scanner);
                    break;
                case 4:
                    System.out.println(Color.GREEN + "Thoát menu thống kê..." + Color.RESET);
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }

    public void totalDateRevenue(Scanner scanner) {
        LocalDate date = InputValidator.validateInputValue(scanner, "Nhập ngày tháng năm ngày bạn muốn thống kê: ", LocalDate.class);

        List<Invoice> invoiceList = statisticService.totalDateRevenue(date);
        if (invoiceList.isEmpty()) {
            System.err.println("Không có hóa đơn trong ngày bạn chọn");
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN NGÀY " + date);
    }

    public void totalMonthRevenue(Scanner scanner) {
        int month = InputValidator.validateInputValue(scanner, "Nhập tháng: ", Integer.class);
        int year = InputValidator.validateInputValue(scanner, "Nhập năm: ", Integer.class);

        List<Invoice> invoiceList = statisticService.totalMonthRevenue(month, year);
        if (invoiceList.isEmpty()) {
            System.err.println("Không có hóa đơn trong ngày bạn chọn");
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN THÁNG " + month + " NĂM " + year);
    }

    public void totalYearRevenue(Scanner scanner) {
        int year = InputValidator.validateInputValue(scanner, "Nhập năm bạn muốn thống kê: ", Integer.class);

        List<Invoice> invoiceList = statisticService.totalYearRevenue(year);
        if (invoiceList.isEmpty()) {
            System.err.println("Không có hóa đơn trong ngày bạn chọn");
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN NĂM " + year);
    }

    public void displayTable(Scanner scanner, List<Invoice> invoiceList , String title) {
        do {
            TableInvoiceUtil.printInvoiceTableHeader(invoiceList, title);
            invoiceList.forEach(System.out::println);
            TableInvoiceUtil.printInvoiceTableFooter();

            int choice;
            Double totalRevenue = 0.0;
            for (Invoice invoice: invoiceList) {
                totalRevenue += invoice.getTotalAmount();
            }
            System.out.println("Tổng doanh thu: " + totalRevenue);

            System.out.println("Bạn muốn làm gì tiếp theo: \n" +
                    "1. Xem hóa đơn chi tiết\n" +
                    "2. Thoát");
            choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1:
                    invoiceUI.displayInvoiceDetail(scanner, invoiceList);
                    break;
                case 2:
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                default:
                    System.err.println("Lựa chọn của bạn không hợp lê, vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
}
