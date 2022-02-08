package problem1.exceptions;

/**
 * An exception indicating that a todo description was not found. Can be thrown when an add todo
 * command was provided but no todo text description.
 */

public class TodoTextNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message.
   */
  public TodoTextNotFoundException(String message) {
    super(message);
  }
}
