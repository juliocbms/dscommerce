package com.devjulio.dscommerce.controllers;

import com.devjulio.dscommerce.DTO.CustomError;
import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.DTO.ProductMinDTO;
import com.devjulio.dscommerce.services.ProductService;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
            ProductDTO dto = productService.findById(id);
            return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductMinDTO>> findAll(@Valid @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
        Page<ProductMinDTO> dto = productService.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
    dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
    return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateById(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
         dto = productService.update(id,dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
