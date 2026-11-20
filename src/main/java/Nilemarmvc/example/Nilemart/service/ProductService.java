package Nilemarmvc.example.Nilemart.service;

import lombok.RequiredArgsConstructor;
import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Brand;
import Nilemarmvc.example.Nilemart.model.Category;
import Nilemarmvc.example.Nilemart.model.Product;
import Nilemarmvc.example.Nilemart.repository.ProductRepository;
import Nilemarmvc.example.Nilemart.service.BrandService;
import Nilemarmvc.example.Nilemart.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() ->
                        new StoreException("Product not found: " + sku));
    }

    public void reduceStock(String sku, int qty) {
        Product product = findBySku(sku);
        if (qty <= 0) {
            throw new StoreException("Quantity must be positive");
        }

        if (product.getStockQuantity() < qty) {
            throw new StoreException("Insufficient stock for: " + sku);
        }

        product.setStockQuantity(product.getStockQuantity() - qty);
    }

    public List<Product> getAvailableProducts() {
        return findAll().stream()
                .filter(p -> p.getStockQuantity() > 0)
                .toList();
    }

    public void saveProduct(Product product) {
        Category category = categoryService.findBySlug(
                product.getCategory()
        ).orElseThrow(() ->
                new StoreException("Category not found.")
        );
        if (category.getParentSlug() == null ||
                category.getParentSlug().isBlank()) {
            throw new StoreException(
                    "Product must belong to a subcategory."
            );
        }
        String brandSlug = product.getBrandSlug();

        if (brandSlug != null && !brandSlug.isBlank()) {

            Brand brand = brandService.findBySlug(brandSlug)
                    .orElseThrow(() ->
                            new StoreException("Brand not found.")
                    );

            if (!brand.isActive()) {
                throw new StoreException("Brand must be active.");
            }
        }
        productRepository.save(product);
    }
    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku).isPresent();
    }
}