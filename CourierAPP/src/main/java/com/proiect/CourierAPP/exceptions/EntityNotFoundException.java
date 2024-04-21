package com.proiect.CourierAPP.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Order not found")
public class EntityNotFoundException extends RuntimeException {
}
