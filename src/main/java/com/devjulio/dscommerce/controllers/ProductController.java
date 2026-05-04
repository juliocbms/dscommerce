package com.devjulio.dscommerce.controllers;

import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id){
        ProductDTO dto = productService.findById(id);
        return dto;
    }

    @GetMapping()
    public Page<ProductDTO> findAll(Pageable pageable){
        return productService.findAll(pageable);
    }
}
