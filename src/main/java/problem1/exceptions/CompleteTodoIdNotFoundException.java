package problem1.exceptions;

/**
 * An exception indicating that a todo ID number must be provided when the complete todo command is
 * used. Can be thrown when using the complete todo command but not providing an id.
 */

public class CompleteTodoIdNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message and token.
   */
  public CompleteTodoIdNotFoundException(String token) {
    super("A todo ID must be provided when using the complete todo command: " + token);
  }
}
