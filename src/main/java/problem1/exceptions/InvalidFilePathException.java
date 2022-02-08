package problem1.exceptions;

/**
 * An exception indicating the provided file path is invalid. Can be thrown when the file could not
 * be found or could not be read.
 */

public class InvalidFilePathException extends RuntimeException {

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param fileType the file type.
   * @param filePath the file path provided.
   */
  public InvalidFilePathException(String fileType, String filePath) {
    super(String.format("%s file (%s) could not be opened for reading.", fileType, filePath));
  }
}
