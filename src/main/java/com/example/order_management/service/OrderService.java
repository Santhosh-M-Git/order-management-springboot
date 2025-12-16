package com.example.order_management.service;

import com.example.order_management.model.Order;
import com.example.order_management.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public Order create(Order order) {
        return repo.save(order);
    }

    public Order getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Order> getAll() {
        return repo.findAll();
    }

    public Order update(Order order) {
        return repo.save(order);
    }
    
    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }


    public Order patch(Long id, Map<String, Object> updates) {
        Order order = repo.findById(id).orElse(null);
        if (order == null) return null;

        updates.forEach((key, value) -> {
            switch (key) {
                case "totalAmount" -> order.setTotalAmount(new java.math.BigDecimal(value.toString()));
                case "paymentStatus" -> order.setPaymentStatus(value.toString());
                case "paymentType" -> order.setPaymentType(value.toString());
                case "userId" -> order.setUserId(Long.valueOf(value.toString()));
            }
        });

        return repo.save(order);
    }
}
