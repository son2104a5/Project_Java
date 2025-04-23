package ra.edu.business.dao.statistic;

import ra.edu.business.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface StatisticDAO {
    List<Invoice> totalDateRevenue(LocalDate date);
    List<Invoice> totalMonthRevenue(int month, int year);
    List<Invoice> totalYearRevenue(int year);
}
