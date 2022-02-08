package problem1.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import problem1.exceptions.CategoryValueNotFoundException;
import problem1.exceptions.CompleteTodoIdNotFoundException;
import problem1.exceptions.CsvFileFlagNotFoundException;
import problem1.exceptions.DisplayCommandNotFoundException;
import problem1.exceptions.DueDateNotFoundException;
import problem1.exceptions.MultipleSortCriteriaNotAllowedException;
import problem1.exceptions.NoArgumentsFoundException;
import problem1.exceptions.PriorityValueNotFoundException;
import problem1.exceptions.TodoTextNotFoundException;

public class ValidatorTest {

  private Map<String, String> allFlags;

  @Before
  public void setUp() {
    allFlags = new HashMap<>();
    allFlags.put("--csv-file", "src/resources/todos.csv");
    allFlags.put("--add-todo", null);
    allFlags.put("--todo-text", "A really complicated todo.");
    allFlags.put("--completed", null);
    allFlags.put("--due", "8/4/2021");
    allFlags.put("--priority", "2");
    allFlags.put("--category", "school");
    allFlags.put("--complete-todo", "2, 3");
    allFlags.put("--display", null);
    allFlags.put("--show-incomplete", null);
    allFlags.put("--show-category", "school");
    allFlags.put("--sort-by-date", null);
  }

  // VALIDATING REQUIRED FLAGS

  @Test (expected = NoArgumentsFoundException.class)
  public void validateRequiredFlagsExistTestEmptyThrowsNoArgumentsFoundException() {
    Map<String, String> emptyFlags = new HashMap<>();
    Validator.validateFlags(emptyFlags);
  }

  @Test (expected = NoArgumentsFoundException.class)
  public void validateRequiredFlagsExistTestNullThrowsNoArgumentsFoundException() {
    Validator.validateFlags(null);
  }

  @Test
  public void validateRequiredFlagsExistAllFlagsShouldSucceed() {
    Validator.validateFlags(allFlags);
  }

  @Test
  public void validateRequiredFlagsExistBareMinimumShouldSucceed() {
    Map<String, String> minimumFlags = new HashMap<>();
    minimumFlags.put("--csv-file", "src/resources/todos.csv");

    Validator.validateFlags(minimumFlags);
  }

  @Test
  public void validateRequiredFlagsExistMinimumDisplayShouldSucceed() {
    Map<String, String> minimumFlagsDisplay = new HashMap<>();
    minimumFlagsDisplay.put("--csv-file", "src/resources/todos.csv");
    minimumFlagsDisplay.put("--display", null);

    Validator.validateFlags(minimumFlagsDisplay);
  }

  @Test (expected = CsvFileFlagNotFoundException.class)
  public void validateRequiredFlagsExistNoCsvFileThrowsCsvFileFlagNotFoundException() {
    Map<String, String> everythingBesidesCsvFlag = new HashMap<>();
    everythingBesidesCsvFlag.put("--add-todo", null);
    everythingBesidesCsvFlag.put("--todo-text", "A really complicated todo.");
    everythingBesidesCsvFlag.put("--completed", null);
    everythingBesidesCsvFlag.put("--due", "8/4/2021");
    everythingBesidesCsvFlag.put("--priority", "2");
    everythingBesidesCsvFlag.put("--category", "school");
    everythingBesidesCsvFlag.put("--complete-todo", "2, 3");
    everythingBesidesCsvFlag.put("--display", null);
    everythingBesidesCsvFlag.put("--show-incomplete", null);
    everythingBesidesCsvFlag.put("--show-category", "school");
    everythingBesidesCsvFlag.put("--sort-by-date", null);

    Validator.validateFlags(everythingBesidesCsvFlag);
  }

  @Test (expected = TodoTextNotFoundException.class)
  public void validateRequiredFlagsExistAddTodoNoTextFlagThrowsTodoTextNotFoundException() {
    Map<String, String> addTodoNoText;
    addTodoNoText = new HashMap<>();
    addTodoNoText.put("--csv-file", "src/resources/todos.csv");
    addTodoNoText.put("--add-todo", null);

    Validator.validateFlags(addTodoNoText);
  }

  @Test (expected = DisplayCommandNotFoundException.class)
  public void validateRequiredFlagsExistShowIncompleteButNoDisplayCommand() {
    Map<String, String> noDisplayCommandShowIncomplete = new HashMap<>();
    noDisplayCommandShowIncomplete.put("--csv-file", "src/resources/todos.csv");
    noDisplayCommandShowIncomplete.put("--show-incomplete", null);

    Validator.validateFlags(noDisplayCommandShowIncomplete);
  }

  @Test (expected = DisplayCommandNotFoundException.class)
  public void validateRequiredFlagsExistShowCategoryButNoDisplayCommand() {
    Map<String, String> noDisplayCommandShowCategory = new HashMap<>();
    noDisplayCommandShowCategory.put("--csv-file", "src/resources/todos.csv");
    noDisplayCommandShowCategory.put("--show-category", "school");

    Validator.validateFlags(noDisplayCommandShowCategory);
  }

  @Test (expected = DisplayCommandNotFoundException.class)
  public void validateRequiredFlagsExistSortByDateButNoDisplayCommand() {
    Map<String, String> noDisplayCommandSortByDate = new HashMap<>();
    noDisplayCommandSortByDate.put("--csv-file", "src/resources/todos.csv");
    noDisplayCommandSortByDate.put("--sort-by-date", null);

    Validator.validateFlags(noDisplayCommandSortByDate);
  }

  @Test (expected = DisplayCommandNotFoundException.class)
  public void validateRequiredFlagsExistSortByPriorityButNoDisplayCommand() {
    Map<String, String> noDisplayCommandSortByPriority = new HashMap<>();
    noDisplayCommandSortByPriority.put("--csv-file", "src/resources/todos.csv");
    noDisplayCommandSortByPriority.put("--sort-by-priority", null);

    Validator.validateFlags(noDisplayCommandSortByPriority);
  }

  @Test (expected = MultipleSortCriteriaNotAllowedException.class)
  public void validateRequiredFlagsExistBothSortCommands() {
    Map<String, String> bothSortCommands = new HashMap<>();
    bothSortCommands.put("--csv-file", "src/resources/todos.csv");
    bothSortCommands.put("--display", null);
    bothSortCommands.put("--sort-by-priority", null);
    bothSortCommands.put("--sort-by-date", null);

    Validator.validateFlags(bothSortCommands);
  }

  // VALIDATING VALUES

  @Test (expected = TodoTextNotFoundException.class)
  public void validateValuesAddTodoTextValueIsNull() {
    Map<String, String> addTodoTextValueIsNull = new HashMap<>();
    addTodoTextValueIsNull.put("--csv-file", "src/resources/todos.csv");
    addTodoTextValueIsNull.put("--add-todo", null);
    addTodoTextValueIsNull.put("--todo-text", null);

    Validator.validateFlags(addTodoTextValueIsNull);
  }

  @Test (expected = TodoTextNotFoundException.class)
  public void validateValuesAddTodoTextValueIsEmpty() {
    Map<String, String> addTodoTextValueIsEmpty = new HashMap<>();
    addTodoTextValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    addTodoTextValueIsEmpty.put("--add-todo", null);
    addTodoTextValueIsEmpty.put("--todo-text", "");

    Validator.validateFlags(addTodoTextValueIsEmpty);
  }

  @Test (expected = DueDateNotFoundException.class)
  public void validateValuesAddTodoDueDateValueIsNull() {
    Map<String, String> addTodoDueDateValueIsNull = new HashMap<>();
    addTodoDueDateValueIsNull.put("--csv-file", "src/resources/todos.csv");
    addTodoDueDateValueIsNull.put("--add-todo", null);
    addTodoDueDateValueIsNull.put("--todo-text", "blah");
    addTodoDueDateValueIsNull.put("--due", null);

    Validator.validateFlags(addTodoDueDateValueIsNull);
  }

  @Test (expected = DueDateNotFoundException.class)
  public void validateValuesAddTodoDueDateValueIsEmpty() {
    Map<String, String> addTodoDueDateValueIsEmpty = new HashMap<>();
    addTodoDueDateValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    addTodoDueDateValueIsEmpty.put("--add-todo", null);
    addTodoDueDateValueIsEmpty.put("--todo-text", "blah");
    addTodoDueDateValueIsEmpty.put("--due", "");

    Validator.validateFlags(addTodoDueDateValueIsEmpty);
  }

  @Test (expected = PriorityValueNotFoundException.class)
  public void validateValuesAddTodoPriorityValueIsNull() {
    Map<String, String> addTodoPriorityValueIsNull = new HashMap<>();
    addTodoPriorityValueIsNull.put("--csv-file", "src/resources/todos.csv");
    addTodoPriorityValueIsNull.put("--add-todo", null);
    addTodoPriorityValueIsNull.put("--todo-text", "blah");
    addTodoPriorityValueIsNull.put("--priority", null);

    Validator.validateFlags(addTodoPriorityValueIsNull);
  }

  @Test (expected = PriorityValueNotFoundException.class)
  public void validateValuesAddTodoPriorityValueIsEmpty() {
    Map<String, String> addTodoPriorityValueIsEmpty = new HashMap<>();
    addTodoPriorityValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    addTodoPriorityValueIsEmpty.put("--add-todo", null);
    addTodoPriorityValueIsEmpty.put("--todo-text", "blah");
    addTodoPriorityValueIsEmpty.put("--priority", "");

    Validator.validateFlags(addTodoPriorityValueIsEmpty);
  }

  @Test (expected = CategoryValueNotFoundException.class)
  public void validateValuesAddTodoCategoryValueIsNull() {
    Map<String, String> addTodoCategoryValueIsNull = new HashMap<>();
    addTodoCategoryValueIsNull.put("--csv-file", "src/resources/todos.csv");
    addTodoCategoryValueIsNull.put("--add-todo", null);
    addTodoCategoryValueIsNull.put("--todo-text", "blah");
    addTodoCategoryValueIsNull.put("--category", null);

    Validator.validateFlags(addTodoCategoryValueIsNull);
  }

  @Test (expected = CategoryValueNotFoundException.class)
  public void validateValuesAddTodoCategoryValueIsEmpty() {
    Map<String, String> addTodoCategoryValueIsEmpty = new HashMap<>();
    addTodoCategoryValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    addTodoCategoryValueIsEmpty.put("--add-todo", null);
    addTodoCategoryValueIsEmpty.put("--todo-text", "blah");
    addTodoCategoryValueIsEmpty.put("--category", "");

    Validator.validateFlags(addTodoCategoryValueIsEmpty);
  }

  @Test (expected = CompleteTodoIdNotFoundException.class)
  public void validateValuesCompleteTodoIdValueIsNull() {
    Map<String, String> completeTodoIdValueIsNull = new HashMap<>();
    completeTodoIdValueIsNull.put("--csv-file", "src/resources/todos.csv");
    completeTodoIdValueIsNull.put("--complete-todo", null);

    Validator.validateFlags(completeTodoIdValueIsNull);
  }

  @Test (expected = CompleteTodoIdNotFoundException.class)
  public void validateValuesCompleteTodoIdValueIsEmpty() {
    Map<String, String> completeTodoIdValueIsEmpty = new HashMap<>();
    completeTodoIdValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    completeTodoIdValueIsEmpty.put("--complete-todo", "");

    Validator.validateFlags(completeTodoIdValueIsEmpty);
  }

  @Test (expected = CategoryValueNotFoundException.class)
  public void validateValuesDisplayTodosShowCategoryValueIsNull() {
    Map<String, String> displayTodosCategoryValueIsNull = new HashMap<>();
    displayTodosCategoryValueIsNull.put("--csv-file", "src/resources/todos.csv");
    displayTodosCategoryValueIsNull.put("--display", null);
    displayTodosCategoryValueIsNull.put("--show-category", null);

    Validator.validateFlags(displayTodosCategoryValueIsNull);
  }

  @Test (expected = CategoryValueNotFoundException.class)
  public void validateValuesDisplayTodosShowCategoryValueIsEmpty() {
    Map<String, String> displayTodosCategoryValueIsEmpty = new HashMap<>();
    displayTodosCategoryValueIsEmpty.put("--csv-file", "src/resources/todos.csv");
    displayTodosCategoryValueIsEmpty.put("--display", null);
    displayTodosCategoryValueIsEmpty.put("--show-category", "");

    Validator.validateFlags(displayTodosCategoryValueIsEmpty);
  }
}