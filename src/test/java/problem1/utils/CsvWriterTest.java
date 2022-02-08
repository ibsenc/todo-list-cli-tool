package problem1.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import problem1.Todo;
import problem1.TodoTest;
import problem1.exceptions.IllegalValueForTextException;

public class CsvWriterTest extends TodoTest {

  private static final String OUTPUT_DIRECTORY_PATH = "src/test/resources/todos.csv";
  private List<Todo> todoList1;

  @Before
  public void setUp() throws Exception, IllegalValueForTextException {
    super.setUp();
    todoList1 = new ArrayList<Todo>();
    todoList1.add(todo1);
    todoList1.add(sameTodo1);
    todoList1.add(todo2);
  }

  @Test
  public void writeToCsv() {
    CsvWriter.writeToCsv(todoList1, OUTPUT_DIRECTORY_PATH);
    File file = new File(OUTPUT_DIRECTORY_PATH);
    assertTrue(file.exists());
  }

  @Test
  public void writeToCsvException() {
    // Exceptions are not thrown from Professor's code
    // https://github.ccs.neu.edu/cs5004-spr20-sea/lecture-code/blob/master/evening_section/Lecture9/src/main/java/timing/ReadBuffered.java#L18
    CsvWriter.writeToCsv(todoList1, "/invalid/path");
  }
}