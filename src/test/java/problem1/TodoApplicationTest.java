package problem1;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import problem1.exceptions.DuplicateIDException;
import problem1.exceptions.FlagsNotSetException;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.InvalidTokenException;
import problem1.exceptions.ListIsEmptyException;
import problem1.exceptions.NoArgumentsFoundException;
import problem1.exceptions.PriorityOutOfRangeException;

@RunWith(MockitoJUnitRunner.class)
public class TodoApplicationTest {

  /*
   This test is using mockito to allow me to isolate the testing of the TodoApplication without
   depending on the implementation of dependencies.

   Reference:
   https://mincong.io/2019/09/13/init-mock/
   */

  @Mock
  private TodoManager mockTodoManager;
  private TodoApplication todoApplication;
  private String[] expectedArgs;
  Map<String, String> expectedFlags;

  @Before
  public void setUp() {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--add-todo",
        "--todo-text", "A really complicated todo.", "--completed", "--due", "8/4/2021",
        "--priority", "2", "--category", "school", "--complete-todo", "3", "--display",
        "--show-incomplete", "--show-category", "school", "--sort-by-date"};

    expectedFlags = new HashMap<>();
    expectedFlags.put("--csv-file", "src/resources/todos_for_testing.csv");
    expectedFlags.put("--add-todo", null);
    expectedFlags.put("--todo-text", "A really complicated todo.");
    expectedFlags.put("--completed", null);
    expectedFlags.put("--due", "8/4/2021");
    expectedFlags.put("--priority", "2");
    expectedFlags.put("--category", "school");
    expectedFlags.put("--complete-todo", "3");
    expectedFlags.put("--display", null);
    expectedFlags.put("--show-incomplete", null);
    expectedFlags.put("--show-category", "school");
    expectedFlags.put("--sort-by-date", null);

    todoApplication = new TodoApplication(mockTodoManager);
  }

  @Test
  public void testParseArgsAllArgsShouldSucceed() {
    expectedFlags = new HashMap<>();
    expectedFlags.put("--csv-file", "src/resources/todos_for_testing.csv");
    expectedFlags.put("--add-todo", null);
    expectedFlags.put("--todo-text", "A really complicated todo.");
    expectedFlags.put("--completed", null);
    expectedFlags.put("--due", "8/4/2021");
    expectedFlags.put("--priority", "2");
    expectedFlags.put("--category", "school");
    expectedFlags.put("--complete-todo", "3");

    expectedFlags.put("--display", null);
    expectedFlags.put("--show-incomplete", null);
    expectedFlags.put("--show-category", "school");
    expectedFlags.put("--sort-by-date", null);

    todoApplication.setFlags(expectedArgs);
    assertEquals(expectedFlags, todoApplication.getFlags());
  }

  @Test
  public void setFlagsParseArgsMultipleCompleteTodoCommandsShouldSucceed() {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--complete-todo", "2",
        "--add-todo", "--todo-text", "A really complicated todo.", "--completed", "--due",
        "8/4/2021", "--priority", "2", "--category", "school", "--complete-todo", "3", "--display",
        "--show-incomplete", "--show-category", "school", "--sort-by-date"};

    expectedFlags = new HashMap<>();
    expectedFlags.put("--csv-file", "src/resources/todos_for_testing.csv");
    expectedFlags.put("--add-todo", null);
    expectedFlags.put("--todo-text", "A really complicated todo.");
    expectedFlags.put("--completed", null);
    expectedFlags.put("--due", "8/4/2021");
    expectedFlags.put("--priority", "2");
    expectedFlags.put("--category", "school");
    expectedFlags.put("--complete-todo", "2, 3");
    expectedFlags.put("--display", null);
    expectedFlags.put("--show-incomplete", null);
    expectedFlags.put("--show-category", "school");
    expectedFlags.put("--sort-by-date", null);

    todoApplication.setFlags(expectedArgs);
    assertEquals(expectedFlags, todoApplication.getFlags());
  }

  @Test(expected = InvalidTokenException.class)
  public void setFlagsParseArgsFirstArgNotAFlagThrowsInvalidTokenException() {
    todoApplication.setFlags(new String[]{"blah", "--whatever"});
  }

  @Test(expected = InvalidTokenException.class)
  public void setFlagsParseArgsArgsEmptyStringThrowsInvalidTokenException() {
    todoApplication.setFlags(new String[]{""});
  }

  @Test(expected = NoArgumentsFoundException.class)
  public void setFlagsParseArgsEmptyArgsThrowsNoArgumentsFoundException() {
    todoApplication.setFlags(new String[]{});
  }

  @Test(expected = NoArgumentsFoundException.class)
  public void setFlagsParseArgsNullArgsThrowsNoArgumentsFoundException() {
    todoApplication.setFlags(null);
  }

  @Test
  public void setFlagsSucceeds() {
    todoApplication.setFlags(expectedArgs);
    assertEquals(expectedFlags, todoApplication.getFlags());
  }

  @Test(expected = FlagsNotSetException.class)
  public void processFlagsNotSetThrowsFlagsNotSetException()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    todoApplication.processFlags();
  }

  @Test
  public void processFlagsTestLoadTodos()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).setCsvPath("src/resources/todos_for_testing.csv");
    Mockito.verify(mockTodoManager, Mockito.times(0)).addTodo(Mockito.any(Todo.class));
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(5)).addTodo(Mockito.any(Todo.class));
    Mockito.verify(mockTodoManager, Mockito.times(0)).writeToCsv();
  }

  @Test
  public void processFlagsTestAddTodo()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--add-todo",
        "--todo-text", "A really complicated todo."};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).addTodo(Mockito.any(Todo.class));
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).getListSize();
    Mockito.verify(mockTodoManager, Mockito.times(6)).addTodo(Mockito.any(Todo.class));
    Mockito.verify(mockTodoManager, Mockito.times(1)).writeToCsv();
  }

  @Test
  public void processFlagsTestAddTodoWithNoOptionalFields()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv",
        "--add-todo", "--todo-text", "A really complicated todo."};

    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).addTodo(Mockito.any(Todo.class));
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(6)).addTodo(Mockito.any(Todo.class));
    Mockito.verify(mockTodoManager, Mockito.times(1)).writeToCsv();
  }

  @Test
  public void processFlagsTestCompleteTodo()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv",
        "--complete-todo", "3"};

    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).addTodo(Mockito.any(Todo.class));
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(5)).addTodo(Mockito.any(Todo.class));
    Mockito.verify(mockTodoManager, Mockito.times(1)).writeToCsv();
  }

  @Test
  public void processFlagsTestCompleteMultipleTodos()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv",
        "--complete-todo", "3", "--complete-todo", "4", "--complete-todo", "1"};

    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).completeTodo(Mockito.any(Integer.class));
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(3)).completeTodo(Mockito.any(Integer.class));
    Mockito.verify(mockTodoManager, Mockito.times(1)).writeToCsv();
  }

  @Test
  public void processFlagsTestDisplayTodosAll()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos(null, false, null);
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos(null, false, null);
    Mockito.verify(mockTodoManager, Mockito.times(0)).writeToCsv();
  }

  @Test
  public void processFlagsTestDisplayTodosSortByPriority()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--sort-by-priority"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos("priority", false, null);
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos("priority", false, null);
  }

  @Test
  public void processFlagsTestDisplayTodosSortByDate()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--sort-by-date"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos("date", false, null);
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos("date", false, null);
  }

  @Test
  public void processFlagsTestDisplayTodosShowIncomplete()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--show-incomplete"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos(null, true, null);
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos(null, true, null);
  }

  @Test
  public void processFlagsTestDisplayTodosShowCategory()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--show-category", "work"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos(null, false, "work");
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos(null, false, "work");
  }

  @Test
  public void processFlagsTestDisplayTodosShowIncompleteAndShowCategory()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--show-incomplete", "--show-category", "work"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos(null, true, "work");
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos(null, true, "work");
  }

  @Test
  public void processFlagsTestDisplayTodosSortByDateShowIncompleteAndShowCategory()
      throws PriorityOutOfRangeException, ListIsEmptyException, DuplicateIDException, IllegalValueForTextException {
    expectedArgs = new String[]{"--csv-file", "src/resources/todos_for_testing.csv", "--display", "--sort-by-date", "--show-incomplete", "--show-category", "work"};
    todoApplication.setFlags(expectedArgs);

    Mockito.verify(mockTodoManager, Mockito.times(0)).displayTodos("date", true, "work");
    todoApplication.processFlags();
    Mockito.verify(mockTodoManager, Mockito.times(1)).displayTodos("date", true, "work");
  }

  // {"--csv-file", "src/resources/todos_for_testing.csv", "--display",
  //        "--show-incomplete", "--show-category", "school", "--sort-by-date"};
}