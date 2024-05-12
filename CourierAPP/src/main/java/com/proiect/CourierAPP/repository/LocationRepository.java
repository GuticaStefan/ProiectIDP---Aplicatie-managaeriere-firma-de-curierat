package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Optional<Location> findLocationByName(String name);
    Optional<List<Location>> findAllByCountry(String country);
    Optional<List<Location>> findAllByCity(String city);
}