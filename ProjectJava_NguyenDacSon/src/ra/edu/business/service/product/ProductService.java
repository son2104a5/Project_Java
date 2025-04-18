package ra.edu.business.service.product;

import ra.edu.business.model.Product;
import ra.edu.business.service.AppService;

import java.util.List;

public interface ProductService extends AppService<Product> {
    Product findProductById(int id);

    List<Product> findProductByBrand(String value);
    List<Product> findProductByPriceAmount(Double value1, Double value2);
    List<Product> findProductByStockRange(int value1, int value2);
}
