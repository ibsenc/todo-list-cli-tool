package problem1.exceptions;

/**
 * An exception indicating that a CSV file flag was not provided. Can be thrown when the given token
 * was not supplied as a command line argument.
 */

public class CsvFileFlagNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified detail message and token.
   *
   * @param token the CSV file token.
   */
  public CsvFileFlagNotFoundException(String token) {
    super("CSV file flag is required: " + token);
  }
}
