package ra.edu.business.service.product;

import ra.edu.business.dao.product.ProductDAO;
import ra.edu.business.dao.product.ProductDAOImp;
import ra.edu.business.model.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

public class ProductServiceImp implements ProductService {
    private final ProductDAO productDAO;

    public ProductServiceImp() {
        productDAO = new ProductDAOImp();
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public boolean save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public boolean update(Product product) {
        return productDAO.update(product);
    }

    @Override
    public boolean delete(Product product) {
        return productDAO.delete(product);
    }

    @Override
    public Product findProductById(int id) {
        return productDAO.findProductById(id);
    }

    @Override
    public List<Product> findProductByBrand(String value) {
        return productDAO.findProductByBrand(value);
    }

    @Override
    public List<Product> findProductByPriceAmount(Double value1, Double value2) {
        return productDAO.findProductByPriceAmount(value1, value2);
    }

    @Override
    public List<Product> findProductByStockRange(int value1, int value2) {
        return productDAO.findProductByStockRange(value1, value2);
    }
}
