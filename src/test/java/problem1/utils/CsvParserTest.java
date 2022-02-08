package problem1.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import problem1.exceptions.InvalidFilePathException;

public class CsvParserTest {

  String invalidCsvPath;

  @Before
  public void setUp() {
    invalidCsvPath = "~/blah.txt";
  }

  // All other branches have been covered already according to JaCoco,
  // that is why this testing class is so small.

  @Test (expected = InvalidFilePathException.class)
  public void parsePeopleCsvThrowsInvalidCsvFileException() {
    CsvParser.parseTodosCsv(invalidCsvPath);
  }
}