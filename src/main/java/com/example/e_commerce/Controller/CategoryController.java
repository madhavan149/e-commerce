package com.example.e_commerce.Controller;

import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    final private CategoryService categoryService;

    public record CategoryRequest(String name) {}

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestParam String name) {

        if(name == null || name.isBlank()){
            return ResponseEntity.badRequest().build();
        }

        Category category = categoryService.saveCategoryByName(name);

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public List<Category> AllCategories(){
        return categoryService.AllCategories();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> UpdateName(@PathVariable Long id, @RequestBody CategoryRequest newName){

        if(newName.name() == null || newName.name().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        Category updateCategory = categoryService.updateName(id, newName.name());

        return ResponseEntity.ok(updateCategory);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


}
