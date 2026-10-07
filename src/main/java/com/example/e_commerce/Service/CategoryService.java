package com.example.e_commerce.Service;
import com.example.e_commerce.Exception.ResourceAlreadyExistsException;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Repository.CategoryRepository;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryReposiitory) {
        this.categoryRepository = categoryReposiitory;
    }

    public Category saveCategoryByName(String name) {

        if(categoryRepository.existsByName(name)){
            throw new ResourceAlreadyExistsException("The category '" + name + "' already exists.");
        }

        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }
}
