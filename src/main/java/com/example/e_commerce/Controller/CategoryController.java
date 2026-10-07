package com.example.e_commerce.Controller;

import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    final private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public Category addCategory(@RequestParam String name) {

        return categoryService.saveCategoryByName(name);
    }


}
