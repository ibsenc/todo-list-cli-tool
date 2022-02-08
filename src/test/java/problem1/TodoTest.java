package problem1;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.PriorityOutOfRangeException;

public class TodoTest extends TodoManagerTest{

  protected Todo todo1;
  protected Todo sameTodo1;
  protected Todo todo2;

  private Integer expectedID4 = 4;
  private String expectedText4 = "Feed the baby";
  private Boolean expectedIsCompleted4 = false;
  private LocalDate expectedDue4 = LocalDate.of(2022, 3, 18);
  private Integer expectedPriority4 = 2;
  private String expectedCategory4 = "?";

  @Before
  public void setUp() throws Exception, IllegalValueForTextException {
    super.setUp();
    todo1 = new Todo(testFields);
    sameTodo1 = new Todo(testFields);
    testFields = new HashMap<String, String>();
    testFields.put(Todo.ID, expectedID4.toString());
    testFields.put(Todo.TEXT, expectedText4);
    testFields.put(Todo.COMPLETED, expectedIsCompleted4.toString());
    testFields.put(Todo.DUE, expectedDue4.toString());
    testFields.put(Todo.PRIORITY, expectedPriority4.toString());
    testFields.put(Todo.CATEGORY, expectedCategory4);
    todo2 = new Todo(testFields);
  }

  @Test
  public void getId() {
    assertTrue(todo1.getId().equals(sameTodo1.getId()));
  }

  @Test
  public void getText() {
    assertTrue(todo1.getText().equals(sameTodo1.getText()));
  }

  @Test
  public void getCompleted() {
    assertTrue(todo1.getCompleted().equals(sameTodo1.getCompleted()));
  }

  @Test
  public void getDue() {
    assertTrue(todo1.getDue().equals(sameTodo1.getDue()));
  }

  @Test
  public void getPriority() {
    assertTrue(todo1.getPriority().equals(sameTodo1.getPriority()));
  }

  @Test(expected = PriorityOutOfRangeException.class)
  public void setPriorityException()
      throws PriorityOutOfRangeException, IllegalValueForTextException {
    Integer expectedID5 = 5;
    String expectedText5 = "Go to the store";
    Boolean expectedIsCompleted5 = false;
    LocalDate expectedDue5 = LocalDate.of(2022, 3, 18);
    Integer expectedPriority5 = 0;
    String expectedCategory5 = "?";
    testFields = new HashMap<String, String>();
    testFields.put(Todo.ID, expectedID5.toString());
    testFields.put(Todo.TEXT, expectedText5);
    testFields.put(Todo.COMPLETED, expectedIsCompleted5.toString());
    testFields.put(Todo.DUE, expectedDue5.toString());
    testFields.put(Todo.PRIORITY, expectedPriority5.toString());
    testFields.put(Todo.CATEGORY, expectedCategory5);
    todo2 = new Todo(testFields);
  }

  @Test
  public void getCategory() {
    assertTrue(todo1.getCategory().equals(sameTodo1.getCategory()));

  }

  @Test
  public void getFields() {
    assertTrue(todo1.getFields().equals(sameTodo1.getFields()));

  }

  @Test
  public void setCompleted() {
    todo1.setCompleted(true);
    sameTodo1.setCompleted(true);
    assertTrue(todo1.getCompleted().equals(sameTodo1.getCompleted()));
  }

  @Test
  public void testEquals() {
    assertTrue(todo1.equals(sameTodo1));
    assertFalse(todo1.equals(todo2));
    assertTrue(todo1.equals(todo1));
    assertFalse(todo1.equals(new Object()));
  }

  @Test
  public void testHashCode() {
    assertTrue(todo1.hashCode() == sameTodo1.hashCode());
  }

  @Test
  public void testToString() {
    assertTrue(todo1.toString().equals(sameTodo1.toString()));
  }
}