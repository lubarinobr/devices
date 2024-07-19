package com.devices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException() {
        super();
    }
    public DeviceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public DeviceNotFoundException(String message) {
        super(message);
    }
    public DeviceNotFoundException(Throwable cause) {
        super(cause);
    }
}
