package com.proiect.CourierAPP.service;

import com.proiect.CourierAPP.dtos.CourierEmployeeDto;
import com.proiect.CourierAPP.dtos.UserDto;
import com.proiect.CourierAPP.enums.EmployeeRole;
import com.proiect.CourierAPP.exceptions.EmployeeNotFoundException;
import com.proiect.CourierAPP.exceptions.UnauthorizedUserException;
import com.proiect.CourierAPP.exceptions.UserAlreadyExistsException;
import com.proiect.CourierAPP.model.CourierEmployee;
import com.proiect.CourierAPP.model.Location;
import com.proiect.CourierAPP.repository.CourierEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourierEmployeeService {

    private final TokenService tokenService;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final ModelMapper modelMapper;

    public CourierEmployeeDto addAEmployee(CourierEmployeeDto courierEmployeeDto) {
        if (tokenService.isAdmin()) {
            var preExistingEmployee = courierEmployeeRepository.findCourierEmployeeByEmail(courierEmployeeDto.getEmail());

            if (preExistingEmployee.isPresent()) {
                throw new UserAlreadyExistsException();
            }

            var employee = CourierEmployee.builder()
                    .firstName(courierEmployeeDto.getFirstName())
                    .lastName(courierEmployeeDto.getLastName())
                    .email(courierEmployeeDto.getEmail())
                    .position(courierEmployeeDto.getPosition())
                    .phoneNumber(courierEmployeeDto.getPhoneNumber())
                    .badgeNumber(courierEmployeeDto.getBadgeNumber())
                    .build();

            courierEmployeeRepository.save(employee);
            return modelMapper.map(employee, CourierEmployeeDto.class);

        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<CourierEmployeeDto> getEmployees() {
        if (tokenService.isAdmin()) {
            List<CourierEmployee> employees = courierEmployeeRepository.findAll();
            return employees.stream()
                    .map(e -> modelMapper.map(e, CourierEmployeeDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public CourierEmployeeDto updateEmployee(Long badgeNumber, CourierEmployeeDto courierEmployeeDto) {
        if (tokenService.isAdmin()) {
            var employee = courierEmployeeRepository.findCourierEmployeeByBadgeNumber(badgeNumber);

            if (employee.isEmpty()) {
                throw new EmployeeNotFoundException();
            }

            return modelMapper.map(employee.map(e -> {
                if (courierEmployeeDto.getFirstName() != null) {
                    e.setFirstName(courierEmployeeDto.getFirstName());
                }
                if (courierEmployeeDto.getLastName() != null) {
                    e.setLastName(courierEmployeeDto.getLastName());
                }
                if (courierEmployeeDto.getEmail() != null) {
                    e.setEmail(courierEmployeeDto.getEmail());
                }
                if (courierEmployeeDto.getPhoneNumber() != null) {
                    e.setPhoneNumber(courierEmployeeDto.getEmail());
                }
                if (courierEmployeeDto.getPosition() != null) {
                    e.setPosition(courierEmployeeDto.getPosition());
                }
                return courierEmployeeRepository.save(e);
            }), CourierEmployeeDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void deleteEmployee(Long badgeNumber) {
        if (tokenService.isAdmin()) {
            var employee = courierEmployeeRepository.findCourierEmployeeByBadgeNumber(badgeNumber).orElseThrow(EmployeeNotFoundException::new);
            courierEmployeeRepository.delete(employee);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public Set<CourierEmployee> getAllEmployeesByLocationId(UUID locationId) {
        var locationEmployees = courierEmployeeRepository.findCourierEmployeesByLocationId(locationId);
        return locationEmployees;
    }

    public List<CourierEmployeeDto> getEmployeesByFunction(String function) {
        if (tokenService.isAdmin()) {
            Optional<List<CourierEmployee>> employees = courierEmployeeRepository.findAllByPosition(function);
            return employees.stream()
                    .map(e -> modelMapper.map(e, CourierEmployeeDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }
}
