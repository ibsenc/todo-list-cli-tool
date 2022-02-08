package problem1.exceptions;

/**
 * An exception indicating that an invalid token was provided. Can be thrown when passed an argument
 * from the command line that is not a valid token.
 */

public class InvalidTokenException extends RuntimeException {

  /**
   * Constructs a new exception with the specified detail message and token.
   *
   * @param token the invalid token.
   */
  public InvalidTokenException(String token) {
    super("An invalid token was supplied: " + token);
  }
}