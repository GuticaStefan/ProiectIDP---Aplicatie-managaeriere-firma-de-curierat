package com.proiect.CourierAPP.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Location not found")
public class LocationsNotFoundException extends RuntimeException {
}
