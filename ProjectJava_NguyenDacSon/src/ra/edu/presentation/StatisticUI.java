package ra.edu.presentation;

import ra.edu.business.model.Invoice;
import ra.edu.business.service.statistic.StatisticService;
import ra.edu.business.service.statistic.StatisticServiceImp;
import ra.edu.utils.Color;
import ra.edu.utils.TableInvoiceUtil;
import ra.edu.validate.objectValidator.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static ra.edu.MainApplication.df;

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
            System.out.println(Color.BLUE + "=============" + Color.PURPLE + " THỐNG KÊ DOANH THU " + Color.BLUE + "=============\n" +
                    Color.CYAN + "1. Theo ngày\n" +
                    "2. Theo tháng\n" +
                    "3. Theo năm\n" +
                    "0. Quay lại menu chính\n" +
                    Color.BLUE + "==============================================" + Color.RESET);
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
                case 0:
                    System.out.println(Color.GREEN + "Thoát menu thống kê..." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Lựa chọn của bạn không hợp lê, vui lòng nhập lại" + Color.RESET);
                    break;
            }
        } while (true);
    }

    public void totalDateRevenue(Scanner scanner) {
        LocalDate date_in = InputValidator.validateInputValue(scanner, "Nhập ngày bắt đầu (định dạng dd/MM/yyyy): ", LocalDate.class);
        LocalDate date_out;
        do {
            date_out = InputValidator.validateInputValue(scanner, "Nhập ngày kết thúc (định dạng dd/MM/yyyy): ", LocalDate.class);
            if (date_in.isAfter(date_out)) {
                System.out.println(Color.RED + "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu, vui lòng nhập lại." + Color.RESET);
            } else {
                break;
            }
        } while (true);

        List<Invoice> invoiceList = statisticService.totalDateRevenue(date_in, date_out);
        if (invoiceList.isEmpty()) {
            System.out.println(Color.RED + "Không có hóa đơn trong thời gian bạn chọn" + Color.RESET);
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN NGÀY " + date_in + " -> " + date_out);
    }

    public void totalMonthRevenue(Scanner scanner) {
        int month_in = InputValidator.validateInputValue(scanner, "Nhập tháng bắt đầu: ", Integer.class);
        int year_in = InputValidator.validateInputValue(scanner, "Nhập năm bắt đầu: ", Integer.class);

        int month_out = InputValidator.validateInputValue(scanner, "Nhập tháng kết thúc: ", Integer.class);
        int year_out;
        do {
            year_out = InputValidator.validateInputValue(scanner, "Nhập năm kết thúc: ", Integer.class);
            if (year_out < year_in) {
                System.out.println(Color.RED + "Năm kết thúc phải lớn hơn năm bắt đầu, vui lòng nhập lại" + Color.RESET);
            } else {
                break;
            }
        } while (true);

        List<Invoice> invoiceList = statisticService.totalMonthRevenue(month_in, year_in, month_out, year_out);
        if (invoiceList.isEmpty()) {
            System.out.println(Color.RED + "Không có hóa đơn trong thời gian bạn chọn" + Color.RESET);
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN THÁNG " + month_in + "/" + year_in + " -> " + month_out + "/" + year_out);
    }

    public void totalYearRevenue(Scanner scanner) {
        int year_in = InputValidator.validateInputValue(scanner, "Nhập năm bắt đầu: ", Integer.class);
        int year_out;
        do {
            year_out = InputValidator.validateInputValue(scanner, "Nhập năm kết thúc: ", Integer.class);
            if (year_out < year_in) {
                System.out.println(Color.RED + "Năm kết thúc phải lớn hơn năm bắt đầu, vui lòng nhập lại" + Color.RESET);
            } else {
                break;
            }
        } while (true);

        List<Invoice> invoiceList = statisticService.totalYearRevenue(year_in, year_out);
        if (invoiceList.isEmpty()) {
            System.out.println(Color.RED + "Không có hóa đơn trong thời gian bạn chọn" + Color.RESET);
            return;
        }

        displayTable(scanner, invoiceList, "DANH SÁCH CÁC HÓA ĐƠN NĂM " + year_in + " -> " + year_out);
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
            System.out.println(Color.GREEN + "Tổng doanh thu: " + df.format(totalRevenue) + Color.RESET);

            System.out.println(Color.PURPLE + "Bạn muốn làm gì tiếp theo: \n" +
                    Color.CYAN + "1. Xem hóa đơn chi tiết\n" +
                    "0. Thoát" + Color.RESET);
            choice = InputValidator.validateInputValue(scanner, "Lựa chọn của bạn: ", Integer.class);
            switch (choice) {
                case 1:
                    invoiceUI.displayInvoiceDetail(scanner, invoiceList);
                    break;
                case 0:
                    System.out.println(Color.GREEN + "Thoát chức năng..." + Color.RESET);
                    return;
                default:
                    System.out.println(Color.RED + "Lựa chọn của bạn không hợp lê, vui lòng nhập lại" + Color.RESET);
                    break;
            }
        } while (true);
    }
}
