package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.enums.EmployeeRole;
import com.proiect.CourierAPP.model.CourierEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CourierEmployeeRepository extends JpaRepository<CourierEmployee, UUID> {
    Optional<CourierEmployee> findCourierEmployeeByEmail(String email);

    Optional<CourierEmployee> findCourierEmployeeByBadgeNumber(Long badgeNumber);

    Optional<List<CourierEmployee>> findAllByPosition(String position);

    Set<CourierEmployee> findCourierEmployeesByLocationId(UUID locationId);
}
