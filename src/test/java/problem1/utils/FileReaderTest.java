package problem1.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import problem1.exceptions.InvalidFilePathException;

public class FileReaderTest {

  private String goodFilePath1;
  private String badFilePath;
  private String notAFilePath;
  private String nonExistentFilePath;
  private String fileType;

  @Before
  public void setUp() {
    goodFilePath1 = "src/resources/todos.csv";
    badFilePath = "todos.csv";
    notAFilePath = "src/resources/";
    nonExistentFilePath = "~/blah.txt";

    fileType = "[Some file]";
  }

  @Test
  public void readFileShouldSucceedGoodPath1() {
    String actualDocument = FileReader.readFile(goodFilePath1, fileType);

    assertNotNull(actualDocument);
  }

  @Test (expected = InvalidFilePathException.class)
  public void readFileInvalidFilePathExceptionBadFilePath() {
    String actualDocument = FileReader.readFile(badFilePath, fileType);

    assertNull(actualDocument);
  }

  @Test (expected = InvalidFilePathException.class)
  public void readFileInvalidFilePathExceptionNotAFilePath() {
    String actualDocument = FileReader.readFile(notAFilePath, fileType);

    assertNull(actualDocument);
  }

  @Test (expected = InvalidFilePathException.class)
  public void readFileInvalidFilePathExceptionNonExistentFilePath() {
    String actualDocument = FileReader.readFile(nonExistentFilePath, fileType);

    assertNull(actualDocument);
  }
}