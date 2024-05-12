package com.proiect.CourierAPP.resource;

import com.proiect.CourierAPP.dtos.*;
import com.proiect.CourierAPP.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationDto addLocation(@RequestBody LocationDto locationDto) {
        return locationService.addALocation(locationDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping("/{country}")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getLocationsByCountry(@PathVariable String country) {
        return locationService.getLocationsByCountry(country);
    }

    @GetMapping("/country/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getLocationsByCity(@PathVariable String city) {
        return locationService.getLocationsByCity(city);
    }

    @PatchMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto updateLocationName(@PathVariable String name, @RequestBody UpdateLocationDto updateLocationDto) {
        return locationService.updateLocation(name, updateLocationDto);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable String name) {
        locationService.deleteLocation(name);
    }

    @PostMapping("/{name}/employee")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto addEmployeeToLocation(@PathVariable String name, @RequestBody @Valid CourierEmployeeDto courierEmployeeDto) {
        return locationService.addEmployeeToLocation(name, courierEmployeeDto);
    }
}
