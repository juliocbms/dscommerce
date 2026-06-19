package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.OrderDTO;
import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.entities.Order;
import com.devjulio.dscommerce.entities.Product;
import com.devjulio.dscommerce.repositories.OrderRepository;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;


    @Transactional(readOnly = true)// locking de leitura apenas para não travar o banco
    public OrderDTO findById(Long id){

        Order order =repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);

    }
}
