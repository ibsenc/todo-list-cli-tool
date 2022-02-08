package problem1.exceptions;

/**
 * An exception indicating that a display command was not given. Can be thrown when a sort or filter
 * command is given but no display command.
 */

public class DisplayCommandNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message.
   */
  public DisplayCommandNotFoundException(String token) {
    super("Display command " + token + " must be provided when a sort or filter command is given.");
  }
}
