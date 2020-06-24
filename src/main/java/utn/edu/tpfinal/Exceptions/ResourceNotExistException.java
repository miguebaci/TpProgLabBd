package utn.edu.tpfinal.Exceptions;

public class ResourceNotExistException extends Exception {
    public ResourceNotExistException(String message) {
        super(message);
    }

    public ResourceNotExistException() {
    }
}