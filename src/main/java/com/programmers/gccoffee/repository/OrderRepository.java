package com.programmers.gccoffee.repository;

import com.programmers.gccoffee.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
