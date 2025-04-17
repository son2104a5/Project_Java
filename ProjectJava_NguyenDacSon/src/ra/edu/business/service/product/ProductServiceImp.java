package ra.edu.business.service.product;

import ra.edu.business.dao.product.ProductDAO;
import ra.edu.business.dao.product.ProductDAOImp;
import ra.edu.business.model.Product;

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
        return false;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }
}
