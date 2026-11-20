package Nilemarmvc.example.Nilemart.repository;

import jakarta.annotation.PostConstruct;
import Nilemarmvc.example.Nilemart.model.Category;
import Nilemarmvc.example.Nilemart.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();

    @PostConstruct
    void init() {

        categories.add(Category.builder()
                .name("Electronics")
                .slug("electronics")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Mobile Phones")
                .slug("mobile-phones")
                .parentSlug("electronics")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Laptops")
                .slug("laptops")
                .parentSlug("electronics")
                .displayOrder(2)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Accessories")
                .slug("electronics-accessories")
                .parentSlug("electronics")
                .displayOrder(3)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Home & Kitchen")
                .slug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Cookware")
                .slug("cookware")
                .parentSlug("home-kitchen")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Lighting")
                .slug("lighting")
                .parentSlug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Fashion")
                .slug("fashion")
                .displayOrder(3)
                .active(false)
                .build());

        categories.add(Category.builder()
                .name("Men's Clothing")
                .slug("mens-clothing")
                .parentSlug("fashion")
                .displayOrder(1)
                .active(true)
                .build());
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return categories.stream()
                .filter(category -> category.getSlug().equals(slug))
                .findFirst();
    }

    @Override
    public void save(Category category) {
        categories.add(category);
    }
}