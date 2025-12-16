package com.example.order_management.controller;

import com.example.order_management.model.Order;
import com.example.order_management.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order saved = service.create(order);
        return ResponseEntity.created(URI.create("/api/orders/" + saved.getOrderId()))
                .body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Order order = service.getById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // GET all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        List<Order> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order newData) {
        Order existing = service.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setTotalAmount(newData.getTotalAmount());
        existing.setPaymentStatus(newData.getPaymentStatus());
        existing.setPaymentType(newData.getPaymentType());
        existing.setUserId(newData.getUserId());

        return ResponseEntity.ok(service.update(existing));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Order patched = service.patch(id, updates);
        return patched != null ? ResponseEntity.ok(patched) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

}
