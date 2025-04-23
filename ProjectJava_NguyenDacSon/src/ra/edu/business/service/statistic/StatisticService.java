package ra.edu.business.service.statistic;

import ra.edu.business.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {
    List<Invoice> totalDateRevenue(LocalDate date);
    List<Invoice> totalMonthRevenue(int month, int year);
    List<Invoice> totalYearRevenue(int year);
}
