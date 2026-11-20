package Nilemarmvc.example.Nilemart.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import Nilemarmvc.example.Nilemart.model.Product;
import Nilemarmvc.example.Nilemart.service.ProductService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Getter
public class ShoppingCart {
    private  int cartId;
    private Customer customer;
    @Getter
    private Map<String, Integer> items = new HashMap<>();
    private final ProductService productService;

    public void initialize(int cartId, Customer customer) {
        this.cartId = cartId;
        this.customer = customer;
    }
    public void addItem(String sku , int quantity ) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        items.merge(sku, quantity, Integer::sum);
    }

    public void removeItem(String sku){
        items.remove(sku);
    }
    public BigDecimal subtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String sku = entry.getKey();
            Integer quantity = entry.getValue();
            Product product = productService.findBySku(sku);
            subtotal = subtotal.add(
                    product.getPrice().multiply(BigDecimal.valueOf(quantity))
            );
        }
        return subtotal;
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
    public void clear() {
        items.clear();
    }

}
