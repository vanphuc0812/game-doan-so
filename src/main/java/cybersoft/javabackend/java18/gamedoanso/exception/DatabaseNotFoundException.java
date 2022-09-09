package cybersoft.javabackend.java18.gamedoanso.exception;

public class DatabaseNotFoundException extends RuntimeException {
    public DatabaseNotFoundException(String message) {
        super(message);
    }

    public DatabaseNotFoundException() {
        super();
    }
}
