package com.gabrielmsantos.ordermap.service.impl;

import com.gabrielmsantos.ordermap.domain.model.Order;
import com.gabrielmsantos.ordermap.domain.repository.OrderRepository;
import com.gabrielmsantos.ordermap.service.OrderService;
import com.gabrielmsantos.ordermap.service.exception.BusinessException;
import com.gabrielmsantos.ordermap.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return this.orderRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Order create(Order OrderToCreate) {
        ofNullable(OrderToCreate).orElseThrow(() -> new BusinessException("Order to create must not be null."));

        return this.orderRepository.save(OrderToCreate);
    }

    @Transactional
    public Order update(Long id, Order orderToUpdate) {
        Order dbOrder = this.findById(id);
        if (!dbOrder.getId().equals(orderToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbOrder.setDt(orderToUpdate.getDt());
        dbOrder.setDescription(orderToUpdate.getDescription());
        dbOrder.setClient(orderToUpdate.getClient());
        dbOrder.setItem(orderToUpdate.getItem());
        dbOrder.setActive(orderToUpdate.getActive());

        return this.orderRepository.save(dbOrder);
    }

    @Transactional
    public void delete(Long id) {
        Order dbOrder = this.findById(id);
        this.orderRepository.delete(dbOrder);
    }
}
