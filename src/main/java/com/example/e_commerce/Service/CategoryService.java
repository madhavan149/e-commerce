package com.example.e_commerce.Service;
import com.example.e_commerce.Exception.ResourceAlreadyExistsException;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Repository.CategoryRepository;
import com.example.e_commerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryReposiitory, ProductRepository productRepository) {
        this.categoryRepository = categoryReposiitory;
        this.productRepository = productRepository;
    }

    public Category saveCategoryByName(String name) {

        if(categoryRepository.existsByName(name)){
            throw new ResourceAlreadyExistsException("The category '" + name + "' already exists.");
        }

        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    public List<Category> AllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateName(Long id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if(newName.equals(category.getName())){
            return category;
        }
        category.setName(newName);

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cannot found"));

        List<Product> products = productRepository.findByCategory(category);

        for(Product product: products){
            product.setCategory(null);
        }

        productRepository.saveAll(products);

        categoryRepository.delete(category);

    }


}
