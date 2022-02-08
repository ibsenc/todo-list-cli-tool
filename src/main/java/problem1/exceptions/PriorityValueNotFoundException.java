package problem1.exceptions;

/**
 * An exception indicating that a priority value must be provided when the priority command is used.
 * Can be thrown when using the priority command but not providing a value.
 */

public class PriorityValueNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message and token.
   */
  public PriorityValueNotFoundException(String token) {
    super("A priority must be provided when using the priority command: " + token);
  }
}
