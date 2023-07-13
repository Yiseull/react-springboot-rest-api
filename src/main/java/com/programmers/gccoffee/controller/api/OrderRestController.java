package com.programmers.gccoffee.controller.api;

import com.programmers.gccoffee.controller.CreateOrderRequest;
import com.programmers.gccoffee.model.Email;
import com.programmers.gccoffee.model.Order;
import com.programmers.gccoffee.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(
                new Email(request.email()),
                request.address(),
                request.postcode(),
                request.orderItems()
        );
    }
}
