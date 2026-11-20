package Nilemarmvc.example.Nilemart.controller;

import jakarta.validation.Valid;
import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Category;
import Nilemarmvc.example.Nilemart.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAllInDisplayOrder());        return "categories";
    }
    @GetMapping("/categories/new")
    public String showForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("parentCategories", categoryService.findAllParentCategories());
        return "categoryForm";
    }
    @PostMapping("/categories")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("parentCategories",
                    categoryService.findAllParentCategories());
            return "categoryForm";
        }
        try {
            categoryService.addCategory(category);
        } catch (StoreException ex) {
            result.reject("categoryError", ex.getMessage());
            model.addAttribute("parentCategories",
                    categoryService.findAllParentCategories());
            return "categoryForm";
        }
        return "redirect:/categories";
    }
}
