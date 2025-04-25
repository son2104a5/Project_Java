package ra.edu.business.service.statistic;

import ra.edu.business.dao.statistic.StatisticDAO;
import ra.edu.business.dao.statistic.StatisticDAOImp;
import ra.edu.business.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public class StatisticServiceImp implements StatisticService {
    private final StatisticDAO statisticDAO;

    public StatisticServiceImp() {
        statisticDAO = new StatisticDAOImp();
    }

    @Override
    public List<Invoice> totalDateRevenue(LocalDate date_in, LocalDate date_out) {
        return statisticDAO.totalDateRevenue(date_in, date_out);
    }

    @Override
    public List<Invoice> totalMonthRevenue(int month_in, int year_in, int month_out, int year_out) {
        return statisticDAO.totalMonthRevenue(month_in, year_in, month_out, year_out);
    }

    @Override
    public List<Invoice> totalYearRevenue(int year_in, int year_out) {
        return statisticDAO.totalYearRevenue(year_in, year_out);
    }
}
