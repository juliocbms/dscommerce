package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.DTO.OrderDTO;
import com.devjulio.dscommerce.DTO.OrderItemDTO;
import com.devjulio.dscommerce.DTO.ProductDTO;
import com.devjulio.dscommerce.entities.*;
import com.devjulio.dscommerce.repositories.OrderItemRepository;
import com.devjulio.dscommerce.repositories.OrderRepository;
import com.devjulio.dscommerce.repositories.ProductRepository;
import com.devjulio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;


    @Transactional(readOnly = true)// locking de leitura apenas para não travar o banco
    public OrderDTO findById(Long id){

        Order order =repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSefOrAdmin(order.getClient().getId());
        return new OrderDTO(order);

    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
       Order order = new Order();

       order.setMoment(Instant.now());
       order.setStatus(OrderStatus.WAITING_PAYMENT);
       User user = userService.authenticated();
       order.setClient(user);

       for (OrderItemDTO itemDTO : dto.getItems()){
           Product product = productRepository.getReferenceById(itemDTO.getProductId());
           OrderItem item = new OrderItem(order,product, itemDTO.getQuantity(), product.getPrice());
           order.getItems().add(item);
       }
       repository.save(order);
       orderItemRepository.saveAll(order.getItems());

       return new OrderDTO(order);
    }
}
