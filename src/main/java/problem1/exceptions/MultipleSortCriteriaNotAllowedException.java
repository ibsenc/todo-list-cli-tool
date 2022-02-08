package problem1.exceptions;

/**
 * An exception indicating that only one sort criterion can be given to display the todos. Can be
 * thrown when both sort commands are provided as arguments.
 */

public class MultipleSortCriteriaNotAllowedException extends ValidationException {

  /**
   * Constructs a new exception with the specified message and tokens.
   */
  public MultipleSortCriteriaNotAllowedException(String token1, String token2) {
    super("Only one of the following sort commands can be provided: " + token1 + ", " + token2);
  }
}
