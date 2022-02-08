package problem1.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import problem1.exceptions.InvalidFilePathException;

/**
 * Class FileReader reads a given file.
 */

public class FileReader {

  /**
   * Opens a file for reading and returns a String representing the file contents.
   *
   * @param filePath - the file path provided.
   * @param fileType - the type of file to be validated.
   * @return a String representing the file
   */
  public static String readFile(String filePath, String fileType) {
    File file = new File(filePath);
    String data = "";
    try {
      data = FileUtils.readFileToString(file, "UTF-8");
    } catch (IOException e) {
      throw new InvalidFilePathException(fileType, filePath);
    }
    return data;
  }
}
