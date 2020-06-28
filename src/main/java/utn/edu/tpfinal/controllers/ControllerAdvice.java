package utn.edu.tpfinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.edu.tpfinal.Exceptions.*;
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

    // 409-
    // The request could not be completed due to a conflict with the current state of the resource.
    // This code is only allowed in situations where it is expected that the user might be able to
    // resolve the conflict and resubmit the request.
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ErrorResponseDTO handle(ResourceAlreadyExistException e) {
        return new ErrorResponseDTO(4, e.getMessage());
    }

    // 204: no content for the target resource.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoDataAvailable.class)
    public ErrorResponseDTO handle(NoDataAvailable e) {
        return new ErrorResponseDTO(5, e.getMessage());
    }
}
