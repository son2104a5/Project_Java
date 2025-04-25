package ra.edu.validate.objectValidator;

import ra.edu.business.model.Product;
import ra.edu.utils.Color;

import java.util.List;

public class ProductValidator {
    public static boolean validateHasExistValue(String value, List<Product> products) {
        String trimmedValue = value.trim().replaceAll("\\s+", " ");
        for (Product product : products) {
            String normalizedProductName = product.getName().trim().replaceAll("\\s+", " ");
            if (normalizedProductName.equalsIgnoreCase(trimmedValue) && !product.getName().equals(trimmedValue)) {
                System.out.println(Color.RED + "Tên sản phẩm đã tồn tại, vui lòng nhập lại." + Color.RESET);
                return true;
            }
        }
        return false;
    }

    public static <T> boolean validateDataHasNotPositiveValue(T value) {
        if (value instanceof Integer) {
            return ((Integer) value) < 0;
        }
        if (value instanceof Double) {
            return ((Double) value) < 0;
        }
        return false;
    }
}
