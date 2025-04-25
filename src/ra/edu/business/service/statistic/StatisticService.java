package ra.edu.business.service.statistic;

import ra.edu.business.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {
    List<Invoice> totalDateRevenue(LocalDate date_in, LocalDate date_out);
    List<Invoice> totalMonthRevenue(int month_in, int year_in, int month_out, int year_out);
    List<Invoice> totalYearRevenue(int year_in, int year_out);
}
