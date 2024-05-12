package com.proiect.CourierAPP.service;

import com.proiect.CourierAPP.dtos.*;
import com.proiect.CourierAPP.exceptions.*;
import com.proiect.CourierAPP.model.CourierEmployee;
import com.proiect.CourierAPP.model.Location;
import com.proiect.CourierAPP.repository.CourierEmployeeRepository;
import com.proiect.CourierAPP.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final TokenService tokenService;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final CourierEmployeeService courierEmployeeService;
    public LocationDto addALocation(LocationDto locationDto) {
        if (tokenService.isAdmin()) {
            var preExistingLocation = locationRepository.findLocationByName(locationDto.getName());

            if (preExistingLocation.isPresent()) {
                throw new UserAlreadyExistsException();
            }

            var location = Location.builder()
                    .name(locationDto.getName())
                    .city(locationDto.getCity())
                    .country(locationDto.getCountry())
                    .county(locationDto.getCounty())
                    .build();

            locationRepository.save(location);
            return modelMapper.map(location, LocationDto.class);

        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<LocationDto> getLocations() {
        if (tokenService.isAdmin()) {
            List<Location> locations = locationRepository.findAll();
            return locations.stream()
                    .map(l -> modelMapper.map(l, LocationDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<LocationDto> getLocationsByCountry(String country) {
        if (tokenService.isAdmin()) {
            List<Location> locations = locationRepository.findAllByCountry(country).orElseThrow(LocationsNotFoundException::new);
            return locations.stream()
                    .map(l -> modelMapper.map(l, LocationDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<LocationDto> getLocationsByCity(String city) {
        if (tokenService.isAdmin()) {
            List<Location> locations = locationRepository.findAllByCity(city).orElseThrow(LocationsNotFoundException::new);
            return locations.stream()
                    .map(l -> modelMapper.map(l, LocationDto.class))
                    .toList();
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public LocationDto updateLocation(String name, UpdateLocationDto updateLocationDto) {
        if (tokenService.isAdmin()) {
            var location = locationRepository.findLocationByName(name);

            if (location.isEmpty()) {
                throw new LocationsNotFoundException();
            }

            return modelMapper.map(location.map(l -> {
                l.setName(updateLocationDto.getName());
                return locationRepository.save(l);
            }), LocationDto.class);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public void deleteLocation(String name) {
        if (tokenService.isAdmin()) {
            var location = locationRepository.findLocationByName(name).orElseThrow(LocationsNotFoundException::new);
            locationRepository.delete(location);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public LocationDto addEmployeeToLocation(String name, CourierEmployeeDto courierEmployeeDto) {
        if (tokenService.isAdmin()) {
            var location = locationRepository.findLocationByName(name).orElseThrow(LocationsNotFoundException::new);
            var employee = modelMapper.map(courierEmployeeDto, CourierEmployee.class);

            employee.setFirstName(courierEmployeeDto.getFirstName());
            employee.setLastName(courierEmployeeDto.getLastName());
            employee.setPosition(courierEmployeeDto.getPosition());
            employee.setEmail(courierEmployeeDto.getEmail());
            employee.setLocation(location);
            employee.setBadgeNumber(courierEmployeeDto.getBadgeNumber());
            employee.setPhoneNumber(courierEmployeeDto.getPhoneNumber());

            courierEmployeeRepository.save(employee);

            var employees = courierEmployeeService.getAllEmployeesByLocationId(location.getId());
            location.setLocationEmployee(employees);

            return modelMapper.map(location, LocationDto.class);

        } else {
            throw new UnauthorizedUserException();
        }
    }
}
