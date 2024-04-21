package com.proiect.CourierAPP.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User not authorized")
public class UnauthorizedUserException extends RuntimeException {
}
