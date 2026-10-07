package com.example.e_commerce.Controller;


import com.example.e_commerce.DTO.ProductDTO;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/add")
    public Product addProduct(@RequestBody ProductDTO productRequest){
        return productService.addItem(productRequest);
    }


}
