package problem1.exceptions;

/**
 * An exception indicating that a category must be provided when any category commands are used. Can
 * be thrown when using the category info command or the show category command but a category is not
 * provided.
 */

public class CategoryValueNotFoundException extends ValidationException {

  /**
   * Constructs a new exception with the specified message.
   */
  public CategoryValueNotFoundException(String message) {
    super(message);
  }
}
