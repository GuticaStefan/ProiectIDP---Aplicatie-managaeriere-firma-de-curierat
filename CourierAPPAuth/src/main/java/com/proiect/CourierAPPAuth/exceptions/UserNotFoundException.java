package com.proiect.CourierAPPAuth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User not found")
public class UserNotFoundException extends RuntimeException {
}
