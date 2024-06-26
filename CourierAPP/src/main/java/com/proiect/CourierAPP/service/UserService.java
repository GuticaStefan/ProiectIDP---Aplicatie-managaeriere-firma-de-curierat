package com.proiect.CourierAPP.service;
import com.proiect.CourierAPP.dtos.AddOrderDto;
import com.proiect.CourierAPP.dtos.PasswordDto;
import com.proiect.CourierAPP.dtos.UserDto;
import com.proiect.CourierAPP.dtos.UserUpdateDto;
import com.proiect.CourierAPP.enums.OrderStatus;
import com.proiect.CourierAPP.exceptions.IncorrectOldPasswordException;
import com.proiect.CourierAPP.exceptions.UnauthorizedUserException;
import com.proiect.CourierAPP.exceptions.UserNotFoundException;
import com.proiect.CourierAPP.model.Order;
import com.proiect.CourierAPP.model.User;
import com.proiect.CourierAPP.repository.OrderRepository;
import com.proiect.CourierAPP.repository.TokenRepository;
import com.proiect.CourierAPP.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserDto update(String userName, UserUpdateDto userUpdateDto) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var user = userRepository.findByUserName(userName);

            if (user.isEmpty()) {
                throw new UserNotFoundException();
            }

            return modelMapper.map(user.map(u -> {
                if (userUpdateDto.getFirstName() != null) {
                    u.setFirstName(userUpdateDto.getFirstName());
                }
                if (userUpdateDto.getLastName() != null) {
                    u.setLastName(userUpdateDto.getLastName());
                }
                if (userUpdateDto.getEmail() != null) {
                    u.setEmail(userUpdateDto.getEmail());
                }
                if (userUpdateDto.getPhoneNumber() != null) {
                    u.setPhoneNumber(userUpdateDto.getEmail());
                }
                return userRepository.save(u);
            }), UserDto.class);

        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void changePassword(String userName, PasswordDto passwordDto) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var user = userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
            if (passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new IncorrectOldPasswordException();
            }
        } else {
            throw new UnauthorizedUserException();
        }

    }

    public List<UserDto> findAll() {
        if (tokenService.isAdmin()) {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(e -> modelMapper.map(e, UserDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public Optional<UserDto> findByUserName(String userName) {

        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var dbEntity = userRepository.findByUserName(userName)
                    .orElseThrow(UserNotFoundException::new);

            return Optional.ofNullable(modelMapper.map(dbEntity, UserDto.class));
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<UserDto> findByFirstName(String firstName) {
        if (tokenService.isAdmin()) {
            var dbEntity = userRepository.findByFirstName(firstName);
            return dbEntity.stream()
                    .map(e -> modelMapper.map(e, UserDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }

    }

    public List<UserDto> findByLastName(String lastName) {
        if (tokenService.isAdmin()) {
            var dbEntity = userRepository.findByLastName(lastName);
            return dbEntity.stream()
                    .map(e -> modelMapper.map(e, UserDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void deleteByUserName(String userName) {
        if ((tokenService.isAuthenticatedUser(userName) && !tokenService.isAdmin()) ||
                tokenService.isAdmin() && !tokenService.isAuthenticatedUser(userName)) {
            var dbEntity = userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
            userRepository.deleteById(dbEntity.getId());
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public UserDto addOrderToUser(String userName, AddOrderDto addOrderDto) {
        if (tokenService.isAuthenticatedUser(userName) || tokenService.isAdmin()) {
            var user = userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
            var order = modelMapper.map(addOrderDto, Order.class);

            order.setOrderDate(LocalDate.now());
            order.setDeliveryDate(LocalDate.now().plusDays(4));
            order.setUser(user);
            order.setStatus(OrderStatus.PROCESSING);
            order.setDescription(addOrderDto.getDescription());

            orderRepository.save(order);

            var orders = orderService.getAllOrdersByUserId(user.getId());
            user.setUserOrders(orders);

            return modelMapper.map(user, UserDto.class);

        } else {
            throw new UnauthorizedUserException();
        }
    }

}
