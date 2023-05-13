package dev.riko.golftourplanner.exceptions;

/**
 * This exception is thrown when a place is not found in the world.
 */
public class UnknownPlaceException extends Exception {
    /**
     * Constructs a new UnknownPlaceException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UnknownPlaceException(String message) {
        super(message);
    }
}
