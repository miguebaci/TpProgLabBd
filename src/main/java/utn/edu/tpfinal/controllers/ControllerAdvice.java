package utn.edu.tpfinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.edu.tpfinal.Exceptions.UserNotexistException;
import utn.edu.tpfinal.dto.ErrorResponseDTO;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotexistException.class)
    public ErrorResponseDTO handleUserNotExists() {
        return new ErrorResponseDTO(1, "User not exists");
    }
}
