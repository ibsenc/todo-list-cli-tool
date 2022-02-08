package problem1.exceptions;

/**
 * An exception indicating that flags have not been set. Can be thrown when attempting to process
 * flags before flags are set in the TodoApplication.
 */

public class FlagsNotSetException extends RuntimeException {

  /**
   * Constructs a new exception with the specified message.
   */
  public FlagsNotSetException(String message) {
    super(message);
  }
}
