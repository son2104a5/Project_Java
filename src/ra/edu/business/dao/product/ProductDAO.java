package ra.edu.business.dao.product;

import ra.edu.business.dao.AppDAO;
import ra.edu.business.model.Product;

import java.util.List;

public interface ProductDAO extends AppDAO<Product> {
    Product findProductById(int id);
    List<Product> findProductByBrand(String value);
    List<Product> findProductByPriceAmount(Double value1, Double value2);
    List<Product> findProductByStockRange(int value1, int value2);
}
