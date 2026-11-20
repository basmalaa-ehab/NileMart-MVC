package Nilemarmvc.example.Nilemart.repository;

import jakarta.annotation.PostConstruct;
import Nilemarmvc.example.Nilemart.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryBrandRepository implements BrandRepository {

    private final List<Brand> brands = new ArrayList<>();

    @PostConstruct
    void init() {
        brands.add(Brand.builder()
                .name("Samsung")
                .slug("samsung")
                .active(true)
                .build());

        brands.add(Brand.builder()
                .name("Asus")
                .slug("asus")
                .active(true)
                .build());

        brands.add(Brand.builder()
                .name("Anker")
                .slug("anker")
                .active(true)
                .build());

        brands.add(Brand.builder()
                .name("Tefal")
                .slug("tefal")
                .active(true)
                .build());

        brands.add(Brand.builder()
                .name("Old Brand")
                .slug("old-brand")
                .active(false)
                .build());
    }

    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(brands);
    }

    @Override
    public Optional<Brand> findBySlug(String slug) {
        return brands.stream()
                .filter(brand -> brand.getSlug().equals(slug))
                .findFirst();
    }

    @Override
    public void save(Brand brand) {
        brands.add(brand);
    }
}