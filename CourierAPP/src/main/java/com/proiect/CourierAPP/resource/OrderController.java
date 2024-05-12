package com.proiect.CourierAPP.resource;

import com.proiect.CourierAPP.dtos.AddOrderDto;
import com.proiect.CourierAPP.dtos.GetOrdersDto;
import com.proiect.CourierAPP.model.Order;
import com.proiect.CourierAPP.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userName}")
    public ResponseEntity<Set<GetOrdersDto>> getAllOrders(@PathVariable String userName) {
        Set<GetOrdersDto> orders = orderService.getAllOrders(userName);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{userName}/{id}")
    public ResponseEntity<GetOrdersDto> getOrderById(@PathVariable String userName, @PathVariable UUID id) {
        GetOrdersDto order = orderService.getOrderById(userName, id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<GetOrdersDto> updateOrder(@PathVariable UUID id, @RequestBody AddOrderDto updatedOrder, @PathVariable String userName) {
        GetOrdersDto order = orderService.updateOrder(id, updatedOrder, userName);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

