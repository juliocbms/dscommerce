package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.entities.Product;
import com.devjulio.dscommerce.repositories.ProductRepository;
import com.devjulio.dscommerce.services.exceptions.DatabaseException;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
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
        try {
            Product entity = productRepository.getReferenceById(id);

            copyDtoToEntity(dto, entity);

            entity = productRepository.save(entity);

            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)// so executa a transação se este metodo estiver no contexto de outra transação
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
