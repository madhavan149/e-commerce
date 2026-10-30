package com.example.e_commerce.Controller;


import com.example.e_commerce.DTO.ProductRequest;
import com.example.e_commerce.DTO.ProductResponse;
import com.example.e_commerce.DTO.ProuductUpdateRequest;
import com.example.e_commerce.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/page")
    public Page<ProductResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {


        return productService.getProducts(page, size, sortBy, direction);

    }


    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse response = productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,@Valid @RequestBody ProuductUpdateRequest request){
        ProductResponse product = productService.updateProduct(id, request);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }



}
