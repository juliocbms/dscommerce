package com.devjulio.dscommerce.controllers;

import com.devjulio.dscommerce.DTO.CustomError;
import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.services.ProductService;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
            ProductDTO dto = productService.findById(id);
            return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> dto = productService.findAll(pageable);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
    dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
    return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateById(@PathVariable Long id, @RequestBody ProductDTO dto){
         dto = productService.update(id,dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
