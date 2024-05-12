package com.proiect.CourierAPP.resource;

import com.proiect.CourierAPP.dtos.CourierEmployeeDto;
import com.proiect.CourierAPP.enums.EmployeeRole;
import com.proiect.CourierAPP.model.CourierEmployee;
import com.proiect.CourierAPP.service.CourierEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class CourierEmployeeController {

    private final CourierEmployeeService courierEmployeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourierEmployeeDto> getEmployees() {
        return courierEmployeeService.getEmployees();
    }

    @GetMapping("/{function}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourierEmployeeDto> getEmployeesByFunction(@PathVariable String function) {
        return courierEmployeeService.getEmployeesByFunction(function);
    }

    @PatchMapping("/{badgeNumber}")
    @ResponseStatus(HttpStatus.OK)
    public CourierEmployeeDto updateEmployee(@PathVariable Long badgeNumber, @RequestBody CourierEmployeeDto courierEmployeeDto) {
        return courierEmployeeService.updateEmployee(badgeNumber, courierEmployeeDto);
    }

    @DeleteMapping("/{badgeNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long badgeNumber) {
        courierEmployeeService.deleteEmployee(badgeNumber);
    }

}
