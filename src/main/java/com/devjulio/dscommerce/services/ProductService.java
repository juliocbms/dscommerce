package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.entities.Product;
import com.devjulio.dscommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)// locking de leitura apenas para não travar o banco
    public ProductDTO findById(Long id){

        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);

    }
}
