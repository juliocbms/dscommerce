package com.devjulio.dscommerce.repositories;


import com.devjulio.dscommerce.entities.Order;
import com.devjulio.dscommerce.entities.OrderItem;
import com.devjulio.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {




}
