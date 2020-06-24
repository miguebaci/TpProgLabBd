package utn.edu.tpfinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.edu.tpfinal.Exceptions.LogOutException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.ErrorResponseDTO;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    // 401 - The request has not been applied because it lacks valid authentication credentials for the target resource.
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDTO handleValidationException(ValidationException e) {
        return new ErrorResponseDTO(1, e.getMessage());
    }

    // 404 - The origin server did not find a current representation for the target resource
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotExistException.class)
    public ErrorResponseDTO handleUserNotExists(ResourceNotExistException e) {
        return new ErrorResponseDTO(2, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LogOutException.class)
    public ErrorResponseDTO handleUserNotExists(LogOutException e) {
        return new ErrorResponseDTO(3, e.getMessage());
    }

}
