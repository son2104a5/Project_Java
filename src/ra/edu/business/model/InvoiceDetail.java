package ra.edu.business.model;

import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;
import ra.edu.utils.Color;
import ra.edu.validate.objectValidator.InputValidator;
import ra.edu.validate.objectValidator.ProductValidator;

import java.util.Scanner;

public class InvoiceDetail {
    private final ProductService productService = new ProductServiceImp();

    private int id;
    private int invoiceId;
    private int productId;
    private int quantity;
    private double unitPrice;

    public InvoiceDetail() {
    }

    public InvoiceDetail(int invoiceId, int productId, int quantity, double unitPrice) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean inputData(Scanner scanner, Invoice invoice) {
        ProductService productService = new ProductServiceImp();
        Product product = null;
        invoiceId = invoice.getId();
        do {
            productId = InputValidator.validateInputValue(scanner, "Nhập ID sản phẩm: ", Integer.class);
            product = productService.findProductById(productId);
            if (product == null) {
                System.out.println(Color.RED + "Không tồn tại sản phẩm có ID = " + productId + ", vui lòng nhập lại" + Color.RESET);
                return false;
            } else if (product.getStock() == 0) {
                System.out.println(Color.RED + "Sản phẩm đã hết hàng" + Color.RESET);
                return false;
            } else {
                break;
            }
        } while (true);
        do {
            quantity = InputValidator.validateInputValue(scanner, "Nhập số lượng: ", Integer.class);
            if (ProductValidator.validateDataHasNotPositiveValue(quantity)) {
                System.out.println(Color.RED + "Số lượng không hợp lệ, vui lòng nhập lại" + Color.RESET);
            } else if (product.getStock() < quantity) {
                System.out.println(Color.RED + "Số lượng tồn kho của sản phẩm không đủ" + Color.RESET);
                return false;
            } else {
                product.setStock(product.getStock() - quantity);
                productService.save(product);
                break;
            }
        } while (true);
        unitPrice = product.getPrice() * quantity;
        invoice.setTotalAmount(invoice.getTotalAmount() + unitPrice);
        return true;
    }

    public static int idWidth = "ID".length();
    public static int invoiceIdWidth = "Mã hóa đơn".length();
    public static int productNameWidth = "Tên sản phẩm".length();
    public static int quantityWidth = "Số lượng".length();
    public static int unitPriceWidth = "Thành tiền".length();

    @Override
    public String toString() {
        Product product = productService.findProductById(productId);
        return String.format("| %-" + idWidth + "d | %-" + invoiceIdWidth + "d | %-" + productNameWidth + "s | %" + quantityWidth + "d | %-" + unitPriceWidth + ".2f |",
                id, invoiceId, product.getName(), quantity, unitPrice);
    }
}
