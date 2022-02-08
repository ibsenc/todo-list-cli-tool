package problem1.exceptions;

/**
 * An exception indicating that a due date must be provided when the command is used. Can be thrown
 * when using the due date command but not providing a date.
 */

public class DueDateNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message and token.
   */
  public DueDateNotFoundException(String token) {
    super("A due date must be provided in the form MM/DD/YYYY when using the due date command: "
        + token);
  }
}
