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
    public List<Invoice> totalDateRevenue(LocalDate date) {
        return statisticDAO.totalDateRevenue(date);
    }

    @Override
    public List<Invoice> totalMonthRevenue(int month, int year) {
        return statisticDAO.totalMonthRevenue(month, year);
    }

    @Override
    public List<Invoice> totalYearRevenue(int year) {
        return statisticDAO.totalYearRevenue(year);
    }
}
