package Nilemarmvc.example.Nilemart.repository;

import jakarta.annotation.PostConstruct;
import Nilemarmvc.example.Nilemart.model.Product;
import Nilemarmvc.example.Nilemart.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> myProducts = new HashMap<>();

    @PostConstruct
    void init() {

        myProducts.put("P002", Product.builder()
                .sku("P002")
                .name("Desk Lamp")
                .price(new BigDecimal("500"))
                .stockQuantity(10)
                .category("lighting")
                .build());

        myProducts.put("P003", Product.builder()
                .sku("P003")
                .name("ASUS TUF")
                .price(new BigDecimal("24000"))
                .stockQuantity(4)
                .category("laptops")
                .brandSlug("asus")
                .build());

        myProducts.put("PH-A55-128", Product.builder()
                .sku("PH-A55-128")
                .name("Samsung Galaxy A55 128GB")
                .price(new BigDecimal("18500.00"))
                .stockQuantity(7)
                .category("mobile-phones")
                .brandSlug("samsung")
                .build());
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(myProducts.values());
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(myProducts.get(sku));
    }

    @Override
    public void save(Product product) {
        myProducts.put(product.getSku(), product);
    }
}