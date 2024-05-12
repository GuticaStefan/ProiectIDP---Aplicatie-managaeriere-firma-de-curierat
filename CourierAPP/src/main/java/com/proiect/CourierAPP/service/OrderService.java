package com.proiect.CourierAPP.service;

import com.proiect.CourierAPP.dtos.AddOrderDto;
import com.proiect.CourierAPP.dtos.GetOrdersDto;
import com.proiect.CourierAPP.exceptions.OrderNotFoundException;
import com.proiect.CourierAPP.exceptions.UnauthorizedUserException;
import com.proiect.CourierAPP.exceptions.UserNotFoundException;
import com.proiect.CourierAPP.model.Order;
import com.proiect.CourierAPP.repository.OrderRepository;
import com.proiect.CourierAPP.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public Set<GetOrdersDto> getAllOrders(String userName) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var user = userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
            var userOrders = orderRepository.findOrdersByUserId(user.getId());
            return userOrders.stream().map(order -> modelMapper.map(order, GetOrdersDto.class))
                    .collect(Collectors.toSet());
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public GetOrdersDto getOrderById(String userName, UUID id) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var order = orderRepository.findById(id);
            return modelMapper.map(order, GetOrdersDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public Set<Order> getAllOrdersByUserId(UUID userId) {
        var userOrders = orderRepository.findOrdersByUserId(userId);
        return userOrders;
    }

    public GetOrdersDto updateOrder(UUID id, AddOrderDto updatedOrder, String userName) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var order = orderRepository.findById(id);
            return modelMapper.map(order.map(o -> {
                o.setDescription(updatedOrder.getDescription());
                return orderRepository.save(o);
            }), GetOrdersDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void deleteOrder(UUID id) {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderRepository.delete(order);
    }
}
