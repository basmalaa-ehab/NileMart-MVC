
package Nilemarmvc.example.Nilemart.controller;

import jakarta.validation.Valid;
import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Product;
import Nilemarmvc.example.Nilemart.service.BrandService;
import Nilemarmvc.example.Nilemart.service.CategoryService;
import Nilemarmvc.example.Nilemart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());

        return "products";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        addFormOptions(model);

        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            addFormOptions(model);
            return "productForm";
        }

        try {
            productService.saveProduct(product);
        } catch (StoreException ex) {

            if (ex.getMessage().contains("Category")) {
                result.rejectValue("category", "invalid", ex.getMessage());
            } else if (ex.getMessage().contains("subcategory")) {
                result.rejectValue("category", "invalid", ex.getMessage());
            } else {
                result.rejectValue("brandSlug", "invalid", ex.getMessage());
            }

            addFormOptions(model);
            return "productForm";
        }

        return "redirect:/products";
    }

    private void addFormOptions(Model model) {
        model.addAttribute(
                "categories",
                categoryService.findAllSubcategories()
        );

        model.addAttribute(
                "brands",
                brandService.findAllActive()
        );
    }
}
