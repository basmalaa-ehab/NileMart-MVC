package Nilemarmvc.example.Nilemart.service;

import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Category;
import Nilemarmvc.example.Nilemart.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // retrieve all categories
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public List<Category> findAllInDisplayOrder() {

        List<Category> allCategories = categoryRepository.findAll();
        List<Category> orderedCategories = new ArrayList<>();

        List<Category> parents = allCategories.stream()
                .filter(category -> category.getParentSlug() == null)
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();

        for (Category parent : parents) {

            orderedCategories.add(parent);

            List<Category> children = allCategories.stream()
                    .filter(category ->
                            parent.getSlug().equals(category.getParentSlug()))
                    .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                    .toList();

            orderedCategories.addAll(children);
        }

        return orderedCategories;
    }
    // find a category by its slug
    public Optional<Category> findBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    // retrieve active categories only
    public List<Category> findAllActive() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.isActive())
                .toList();
    }

    // retrieve active parent categories
    public List<Category> findAllParentCategories() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParentSlug() == null)
                .filter(category -> category.isActive())
                .toList();
    }

    // retrieve active subcategories
    public List<Category> findAllSubcategories() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParentSlug() != null)
                .filter(category -> category.isActive())
                .toList();
    }

    // retrieve active subcategories belonging to a specific parent
    public List<Category> findSubcategoriesByParentSlug(String parentSlug) {
        return categoryRepository.findAll().stream()
                .filter(category -> parentSlug.equals(category.getParentSlug()))
                .filter(category -> category.isActive())
                .toList();
    }

    // add a new category
    public void addCategory(Category category) {

        // treat an empty parent slug as null
        if (category.getParentSlug() != null &&
                category.getParentSlug().isBlank()) {
            category.setParentSlug(null);
        }

        // check that the parent exists and is a top-level category
        String parentSlug = category.getParentSlug();

        if (parentSlug != null) {
            Optional<Category> parent = categoryRepository.findBySlug(parentSlug);

            if (parent.isEmpty()) {
                throw new StoreException("Parent category does not exist.");
            }

            if (parent.get().getParentSlug() != null) {
                throw new StoreException(
                        "A subcategory cannot have another subcategory."
                );
            }
        }

        // check duplicate name within the same parent
        boolean nameExists = categoryRepository.findAll().stream()
                .anyMatch(existing ->
                        existing.getName().equalsIgnoreCase(category.getName())
                                && Objects.equals(existing.getParentSlug(), parentSlug));

        if (nameExists) {
            String parentName = parentSlug == null
                    ? "top level"
                    : categoryRepository.findBySlug(parentSlug)
                    .orElseThrow()
                    .getName();

            throw new StoreException(
                    "A category with this name already exists under " + parentName
            );
        }

        // check duplicate slug globally
        if (categoryRepository.findBySlug(category.getSlug()).isPresent()) {
            throw new StoreException("Slug already exists");
        }

        // save category if all checks pass
        categoryRepository.save(category);
    }}