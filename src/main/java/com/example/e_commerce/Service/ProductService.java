package com.example.e_commerce.Service;


import com.example.e_commerce.DTO.ProductRequest;
import com.example.e_commerce.DTO.ProductResponse;
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
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .descrpition(productRequest.getDescrpition())
                .price(productRequest.getPrice())
                .stock_quantity(productRequest.getStock_quantity())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescrpition());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStock_quantity());
        response.setCategoryName(savedProduct.getCategory().getName());

        return response;
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {

            ProductResponse response = new ProductResponse();

            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescrpition());
            response.setPrice(product.getPrice());
            response.setStockQuantity(product.getStock_quantity());
            response.setCategoryName(product.getCategory().getName());

            responses.add(response);
        }

        return responses;
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setCategoryName(product.getCategory().getName());
        response.setDescription(product.getDescrpition());
        response.setName(product.getName());
        response.setStockQuantity(product.getStock_quantity());
        response.setPrice(product.getPrice());

        return response;

    }

    public Product updateProduct(Long id, ProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cannot found this id"));

        if(existingProduct.getName() != null){
            existingProduct.setName(request.getName());
        }

        if(existingProduct.getPrice() != null){
            existingProduct.setPrice(request.getPrice());
        }

        if(existingProduct.getStock_quantity() != null){
            existingProduct.setStock_quantity(request.getStock_quantity());
        }

        if(existingProduct.getDescrpition() != null){
            existingProduct.setDescrpition(request.getDescrpition());
        }

        if(request.getCategory_id() != null){
            Category newCategory = categoryReposiitory.findById(request.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(newCategory);
        }

        return productRepository.save(existingProduct);
    }


    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);

    }
}
