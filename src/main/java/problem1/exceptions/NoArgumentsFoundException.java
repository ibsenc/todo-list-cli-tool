package problem1.exceptions;

/**
 * An exception indicating that no command line arguments were found. Can be thrown when no
 * arguments were passed into the command line upon starting the program.
 */

public class NoArgumentsFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message, the exception message.
   */
  public NoArgumentsFoundException(String message) {
    super(message);
  }
}
