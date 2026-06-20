package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.CategoryDTO;
import com.devjulio.dscommerce.entities.Category;
import com.devjulio.dscommerce.repositories.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }



    @Transactional(readOnly = true)// locking de leitura apenas para não travar o banco
    public List<CategoryDTO> findAll(){

        List<Category> result = categoryRepository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();

    }

}
