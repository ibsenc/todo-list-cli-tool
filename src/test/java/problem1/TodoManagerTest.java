package problem1;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem1.exceptions.DuplicateIDException;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.ListIsEmptyException;

public class TodoManagerTest {
  private String testCsvPath;
  private TodoManager testTodoManager;
  protected Map<String, String> testFields;
  private Integer expectedID;
  private String expectedText;
  private Boolean expectedIsCompleted;
  private LocalDate expectedDue;
  private Integer expectedPriority;
  private String expectedCategory;
  private Integer expectedID2;
  private String expectedText2;
  private Boolean expectedIsCompleted2;
  private LocalDate expectedDue2;
  private Integer expectedPriority2;
  private String expectedCategory2;
  private Integer expectedID3;
  private String expectedText3;
  private Boolean expectedIsCompleted3;
  private LocalDate expectedDue3;
  private Integer expectedPriority3;
  private String expectedCategory3;
  private Todo expectedTodo;
  private Todo expectedTodo2;
  private Todo expectedTodo3;
  private final PrintStream standardOut = System.out;
  private ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


  @Before
  public void setUp() throws Exception, IllegalValueForTextException {
    expectedID = 1;
    expectedText = "Finish HW9";
    expectedIsCompleted = false;
    expectedDue = LocalDate.of(2022, 8,2);
    expectedPriority = 3;
    expectedCategory = "School";
    expectedID2 = 2;
    expectedText2 = "Mail passport";
    expectedIsCompleted2 = false;
    expectedDue2 = LocalDate.of(2021, 8,28);
    expectedPriority2 = 1;
    expectedCategory2 = "?";
    expectedID3 = 3;
    expectedText3 = "Study for Finals";
    expectedIsCompleted3 = false;
    expectedDue3 = LocalDate.of(2021, 8,15);
    expectedPriority3 = 2;
    expectedCategory3 = "Office";
    testTodoManager = new TodoManager();
    testFields = new HashMap<String, String>();
    testFields.put(Todo.ID, expectedID.toString());
    testFields.put(Todo.TEXT, expectedText);
    testFields.put(Todo.COMPLETED, expectedIsCompleted.toString());
    testFields.put(Todo.DUE, expectedDue.toString());
    testFields.put(Todo.PRIORITY, expectedPriority.toString());
    testFields.put(Todo.CATEGORY, expectedCategory);
    expectedTodo = new Todo(testFields);
    testFields = new HashMap<String, String>();
    testFields.put(Todo.ID, expectedID2.toString());
    testFields.put(Todo.TEXT, expectedText2);
    testFields.put(Todo.COMPLETED, expectedIsCompleted2.toString());
    testFields.put(Todo.DUE, expectedDue2.toString());
    testFields.put(Todo.PRIORITY, expectedPriority2.toString());
    testFields.put(Todo.CATEGORY, expectedCategory2);
    expectedTodo2 = new Todo(testFields);
    testFields = new HashMap<String, String>();
    testFields.put(Todo.ID, expectedID3.toString());
    testFields.put(Todo.TEXT, expectedText3);
    testFields.put(Todo.COMPLETED, expectedIsCompleted3.toString());
    testFields.put(Todo.DUE, expectedDue3.toString());
    testFields.put(Todo.PRIORITY, expectedPriority3.toString());
    testFields.put(Todo.CATEGORY, expectedCategory3);
    expectedTodo3 = new Todo(testFields);
    testCsvPath = "../a9/src/test/resources/todos.csv";
    System.setOut(new PrintStream(outputStreamCaptor));

  }

  @Test
  public void setCsvPath() throws DuplicateIDException {
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.setCsvPath(testCsvPath);
    testTodoManager.writeToCsv();
    File file = new File(testCsvPath);
    assertTrue(file.exists());
  }

  @Test
  public void addTodo() throws DuplicateIDException{
    testTodoManager.addTodo(expectedTodo);
    assertTrue(testTodoManager.getListSize().equals(1));
    testTodoManager.addTodo(expectedTodo2);
    assertTrue(testTodoManager.getListSize().equals(2));
  }

  @Test
  public void completeTodo() throws DuplicateIDException, ListIsEmptyException {
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.addTodo(expectedTodo2);
    testTodoManager.completeTodo(expectedID);
    testTodoManager.displayTodos(null, true, null);
    assertEquals("ToDo{id=2, text='Mail passport', completed=false, due=2021-08-28, "
        + "priority=1, category='null'}", outputStreamCaptor.toString()
        .trim());

  }

  @Test
  public void getListSize() throws DuplicateIDException{
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.addTodo(expectedTodo2);
    assertTrue(testTodoManager.getListSize().equals(2));
  }

  @Test
  public void isEmpty() throws DuplicateIDException{
    assertTrue(testTodoManager.isEmpty());
    testTodoManager.addTodo(expectedTodo);
    assertFalse(testTodoManager.isEmpty());

  }

  @Test
  public void displayTodos() throws DuplicateIDException, ListIsEmptyException {
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.displayTodos("PRIORITY", false, null);
    assertEquals("ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
        + "priority=3, category='School'}", outputStreamCaptor.toString().trim());
    testTodoManager.displayTodos(null, true, "school");
    assertEquals("ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
        + "priority=3, category='School'}", outputStreamCaptor.toString().trim());
    testTodoManager.displayTodos(null, true, "");
    assertEquals("ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
        + "priority=3, category='School'}", outputStreamCaptor.toString().trim());
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
    testTodoManager.addTodo(expectedTodo3);
    testTodoManager.displayTodos(null, false,"School");
    assertEquals("ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
        + "priority=3, category='School'}", outputStreamCaptor.toString().trim());
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
    testTodoManager.addTodo(expectedTodo2);
    testTodoManager.displayTodos("PRIORITY", false, null);
    assertEquals(  "ToDo{id=2, text='Mail passport', completed=false, due=2021-08-28, "
            + "priority=1, category='null'}"+ "\n" +"ToDo{id=3, text='Study for Finals', "
            + "completed=false, due=2021-08-15, priority=2, category='Office'}" +
            "\n" +"ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
            + "priority=3, category='School'}"
        , outputStreamCaptor.toString().trim());
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
    testTodoManager.displayTodos("DATE", false,null);
    assertEquals("ToDo{id=3, text='Study for Finals', "
            + "completed=false, due=2021-08-15, priority=2, category='Office'}" + "\n"
            + "ToDo{id=2, text='Mail passport', completed=false, due=2021-08-28, "
            + "priority=1, category='null'}" + "\n" +
        "ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, priority=3,"
        + " category='School'}", outputStreamCaptor.toString().trim());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullCsvPath() throws IllegalArgumentException {
    testTodoManager.writeToCsv();
  }
  @Test (expected = ListIsEmptyException.class)
  public void testEmptyList() throws ListIsEmptyException {
    testTodoManager.displayTodos(null, true, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullAdd() throws IllegalArgumentException, DuplicateIDException {
    testTodoManager.addTodo(null);
  }

  @Test (expected = DuplicateIDException.class)
  public void testDuplicateIDTodo() throws DuplicateIDException {
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.addTodo(expectedTodo);
  }


  @Test
  public void testEquals() throws DuplicateIDException{
    assertTrue(testTodoManager.equals(testTodoManager));
    assertFalse(testTodoManager.equals(expectedTodo));
    TodoManager testTodoManager2 = new TodoManager();
    assertEquals(testTodoManager, testTodoManager2);
    testTodoManager2.addTodo(expectedTodo);
    assertFalse(testTodoManager.equals(testTodoManager2));

  }

 // Cannot test because no getter for todoList
//  @Test
//  public void testHashCode() {
//    int hash = Objects.hash(todoList,testCsvPath);
//    assertEquals(hash, testTodoManager.hashCode());
//  }

  @Test
  public void testToString() throws DuplicateIDException{
    testTodoManager.addTodo(expectedTodo);
    testTodoManager.setCsvPath(testCsvPath);
    String expectedToString = "TodoManager{" +
        "todoList=" + "ToDo{id=1, text='Finish HW9', completed=false, due=2022-08-02, "
        + "priority=3, category='School'} "  +
        ", csvPath='" + testCsvPath + '\'' +
        '}';
    assertEquals(expectedToString, testTodoManager.toString());
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(standardOut);
  }
}