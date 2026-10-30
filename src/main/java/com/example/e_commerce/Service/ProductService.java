package com.example.e_commerce.Service;


import com.example.e_commerce.DTO.ProductRequest;
import com.example.e_commerce.DTO.ProductResponse;
import com.example.e_commerce.DTO.ProuductUpdateRequest;
import com.example.e_commerce.Exception.ResourceNotFoundException;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Repository.CategoryRepository;
import com.example.e_commerce.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    final private ProductRepository productRepository;
    final private CategoryRepository categoryReposiitory;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryReposiitory) {
        this.productRepository = productRepository;
        this.categoryReposiitory = categoryReposiitory;
    }

    public ProductResponse addProduct(ProductRequest productRequest) {

        Category category = categoryReposiitory.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
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

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "price", "stockQuantity");

    public Page<ProductResponse> getProducts(int page, int size, String sortBy, String direction) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable).map(this::toResponse);

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

        if(request.getStockQuantity() != null){
            existingProduct.setStockQuantity(request.getStockQuantity());
        }

        if(request.getDescription() != null){
            existingProduct.setDescription(request.getDescription());
        }

        if(request.getCategoryId() != null){
            Category newCategory = categoryReposiitory.findById(request.getCategoryId())
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
        response.setStockQuantity(product.getStockQuantity());
        response.setCategoryName(
                product.getCategory() != null ? product.getCategory().getName() : null
        );
        return response;
    }
}
