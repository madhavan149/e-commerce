package com.example.e_commerce.Service;


import com.example.e_commerce.DTO.ProductRequest;
import com.example.e_commerce.DTO.ProductResponse;
import com.example.e_commerce.DTO.ProuductUpdateRequest;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Repository.CategoryRepository;
import com.example.e_commerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    final private ProductRepository productRepository;
    final private CategoryRepository categoryReposiitory;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryReposiitory) {
        this.productRepository = productRepository;
        this.categoryReposiitory = categoryReposiitory;
    }

    public ProductResponse addProduct(ProductRequest productRequest) {

        Category category = categoryReposiitory.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock_quantity(productRequest.getStock_quantity())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return toResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {

            responses.add(toResponse(product));
        }

        return responses;
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return toResponse(product);

    }

    public ProductResponse updateProduct(Long id, ProuductUpdateRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cannot found this id"));

        if(request.getName() != null){
            existingProduct.setName(request.getName());
        }

        if(request.getPrice() != null){
            existingProduct.setPrice(request.getPrice());
        }

        if(request.getStock_quantity() != null){
            existingProduct.setStock_quantity(request.getStock_quantity());
        }

        if(request.getDescrpition() != null){
            existingProduct.setDescription(request.getDescrpition());
        }

        if(request.getCategory_id() != null){
            Category newCategory = categoryReposiitory.findById(request.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(newCategory);
        }

        productRepository.save(existingProduct);

        return toResponse(existingProduct);
    }


    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);

    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStock_quantity());
        response.setCategoryName(
                product.getCategory() != null ? product.getCategory().getName() : null
        );
        return response;
    }
}
