package Nilemarmvc.example.Nilemart.controller;

import jakarta.validation.Valid;
import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Brand;
import Nilemarmvc.example.Nilemart.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    @GetMapping("/brands")
    public String getBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brands";
    }
    @GetMapping("/brands/new")
    public String showForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "brandForm";
    }
    @PostMapping("/brands")
    public String addBrand( @Valid @ModelAttribute("brand") Brand brand, BindingResult result) {
        if (result.hasErrors()) {
            return "brandForm";
        }
        try {
            brandService.addBrand(brand);
        } catch (StoreException ex) {

            if (ex.getMessage().equals("Brand name already exists")) {
                result.rejectValue("name", "duplicate", ex.getMessage());
            } else if (ex.getMessage().equals("Slug already exists")) {
                result.rejectValue("slug", "duplicate", ex.getMessage());
            }
            return "brandForm";
        }
        return "redirect:/brands";
    }
}
