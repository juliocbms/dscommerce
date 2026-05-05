package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.entities.Product;
import com.devjulio.dscommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)// locking de leitura apenas para não travar o banco
    public Page<ProductDTO> findAll(Pageable pageable){

        Page<Product> result = productRepository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));

    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();

        copyDtoToEntity(dto, entity);

        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO dto){
        Product entity = productRepository.getReferenceById(id);

        copyDtoToEntity(dto, entity);

        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
