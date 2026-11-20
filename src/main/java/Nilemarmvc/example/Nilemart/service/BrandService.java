package Nilemarmvc.example.Nilemart.service;

import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Brand;
import Nilemarmvc.example.Nilemart.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    // retrieve all brands
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    // retrieve active brands only
    public List<Brand> findAllActive() {
        return brandRepository.findAll().stream()
                .filter(brand -> brand.isActive())
                .toList();
    }
    // find a brand by its slug
    public Optional<Brand> findBySlug(String slug) {
        return brandRepository.findBySlug(slug);
    }

    // add a new brand
    public void addBrand(Brand brand) {

        // check duplicate brand name (case-insensitive)
        boolean nameExists = brandRepository.findAll().stream()
                .anyMatch(existing ->
                        existing.getName().equalsIgnoreCase(brand.getName()));
        if (nameExists) {
            throw new StoreException("Brand name already exists");
        }

        // check duplicate slug
        if (brandRepository.findBySlug(brand.getSlug()).isPresent()) {
            throw new StoreException("Slug already exists");
        }

        // save the brand if all checks pass
        brandRepository.save(brand);
    }
}
