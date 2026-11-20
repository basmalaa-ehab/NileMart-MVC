package Nilemarmvc.example.Nilemart.repository;

import Nilemarmvc.example.Nilemart.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    List<Brand> findAll();

    Optional<Brand> findBySlug(String slug);

    void save(Brand brand);
}