package com.example.e_commerce.Service;


import com.example.e_commerce.DTO.ProductDTO;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Repository.CategoryRepository;
import com.example.e_commerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    final private ProductRepository productRepository;
    final private CategoryRepository categoryReposiitory;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryReposiitory) {
        this.productRepository = productRepository;
        this.categoryReposiitory = categoryReposiitory;
    }

    public Product addItem(ProductDTO productRequest) {

        Category category = categoryReposiitory.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .descrpition(productRequest.getDescrpition())
                .price(productRequest.getPrice())
                .stock_quantity(productRequest.getStock_quantity())
                .category(category)
                .build();

        productRepository.save(product);

        return product;
    }
}
