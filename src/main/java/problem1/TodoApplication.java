package problem1;

import static problem1.model.Flag.ADD_TODO_COMMAND;
import static problem1.model.Flag.CATEGORY_INFO_COMMAND;
import static problem1.model.Flag.COMPLETED_INFO_COMMAND;
import static problem1.model.Flag.COMPLETE_TODO_COMMAND;
import static problem1.model.Flag.CSV_FILE_COMMAND;
import static problem1.model.Flag.DISPLAY_COMMAND;
import static problem1.model.Flag.DUE_INFO_COMMAND;
import static problem1.model.Flag.PRIORITY_INFO_COMMAND;
import static problem1.model.Flag.SHOW_CATEGORY_DISPLAY_COMMAND;
import static problem1.model.Flag.SHOW_INCOMPLETE_DISPLAY_COMMAND;
import static problem1.model.Flag.SORT_BY_DATE_DISPLAY_COMMAND;
import static problem1.model.Flag.SORT_BY_PRIORITY_DISPLAY_COMMAND;
import static problem1.model.Flag.TODO_TEXT_INFO_COMMAND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import problem1.exceptions.DuplicateIDException;
import problem1.exceptions.FlagsNotSetException;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.InvalidTokenException;
import problem1.exceptions.ListIsEmptyException;
import problem1.exceptions.NoArgumentsFoundException;
import problem1.exceptions.PriorityOutOfRangeException;
import problem1.utils.CsvParser;
import problem1.utils.Validator;

/**
 * Class TodoApplication is the main application. Instantiates a TodoManager and processes todolist
 * interaction commands based on input arguments passed in from Main.
 */

public class TodoApplication {

  private final TodoManager todoManager;
  private Map<String, String> flags;
  private static final String FLAG_PREFIX = "--";

  /**
   * Constructor for the TodoApplication.
   *
   * @param todoManager - TodoManager, manages the list of Todos.
   */
  public TodoApplication(TodoManager todoManager) {
    this.todoManager = todoManager;
    this.flags = new HashMap<>();
  }

  /**
   * Returns the TodoManager of TodoApplication.
   *
   * @return the TodoManager of TodoApplication.
   */
  public TodoManager getTodoManager() {
    return todoManager;
  }

  /**
   * Returns the flags of TodoApplication.
   *
   * @return the flags of TodoApplication.
   */
  public Map<String, String> getFlags() {
    return flags;
  }

  /**
   * Sets and validates the flags.
   *
   * @param args - String[], the command line arguments.
   */
  public void setFlags(String[] args) {
    this.flags = parseArgs(args);
    Validator.validateFlags(this.flags);
  }

  /**
   * Parses the arguments taken in from the command line and maps the flags to their corresponding
   * values if applicable.
   *
   * @param args - String[], the command line arguments.
   * @return a Map of each flag and their value.
   */
  private Map<String, String> parseArgs(String[] args) {
    Map<String, String> flags = new HashMap<>();

    if (args == null || args.length == 0) {
      throw new NoArgumentsFoundException(
          "Command line arguments must be provided to run this program.");
    }

    // Loop through args, collecting keys (flags) and values
    for (int i = 0; i < args.length; i++) {
      String key = args[i];

      // If we reach here it should be a flag
      if (!key.contains(FLAG_PREFIX)) {
        throw new InvalidTokenException(key);
      }

      // Value is null by default
      String value = null;

      // If there are multiple complete todo commands
      if (key.equals(COMPLETE_TODO_COMMAND.getToken()) && flags
          .containsKey(COMPLETE_TODO_COMMAND.getToken()) &&
          i + 1 < args.length && !args[i + 1].contains(FLAG_PREFIX)) {
        String prevValue = flags.get(COMPLETE_TODO_COMMAND.getToken());
        flags.put(key, prevValue + ", " + args[i + 1]);
        i++;
        continue;
      }

      int nextArgIndex = i + 1;

      // Collect values until we find another flag or run out of args
      while (nextArgIndex < args.length && !args[nextArgIndex].contains(FLAG_PREFIX)) {
        // Found a value. If values is null, set it, otherwise concatenate it
        value = value == null
            ? args[nextArgIndex]
            : value + " " + args[nextArgIndex];

        // Go to the next if we found a value
        i++;
        nextArgIndex = i + 1;
      }

      flags.put(key, value);
    }
    return flags;
  }

  /**
   * Processes the flags.
   */
  public void processFlags()
      throws PriorityOutOfRangeException, IllegalValueForTextException, ListIsEmptyException, DuplicateIDException {
    boolean writeToCsvNeeded = false;

    if (this.flags.isEmpty()) {
      throw new FlagsNotSetException("Flags need to be set before they can be processed.");
    }

    // Load todos
    this.loadTodos();

    // If there's a todo to add, add it
    if (this.flags.containsKey(ADD_TODO_COMMAND.getToken())) {
      writeToCsvNeeded = true;
      this.addTodo();
    }

    // If there's a todo to complete, complete it
    if (this.flags.containsKey(COMPLETE_TODO_COMMAND.getToken())) {
      writeToCsvNeeded = true;
      this.completeTodo(flags.get(COMPLETE_TODO_COMMAND.getToken()));
    }

    if (this.flags.containsKey(DISPLAY_COMMAND.getToken())) {
      this.displayTodos();
    }

    // If we added or completed todo, we need to write to csv
    if (writeToCsvNeeded) {
      todoManager.writeToCsv();
    }
  }

  /**
   * Generates todos and loads them into the TodoManager.
   */
  private void loadTodos() throws DuplicateIDException {
    // Parse csv
    List<Todo> todoList = CsvParser.parseTodosCsv(flags.get(CSV_FILE_COMMAND.getToken()));

    // Set csvPath
    todoManager.setCsvPath(flags.get(CSV_FILE_COMMAND.getToken()));

    // Add each todo
    for (Todo todo : todoList) {
      todoManager.addTodo(todo);
    }
  }

  /**
   * Adds a new Todo object to the TodoManager based on the command line args.
   */
  private void addTodo()
      throws PriorityOutOfRangeException, IllegalValueForTextException, DuplicateIDException {
    Map<String, String> newTodoFields = new HashMap<>();

    // Generate ID and put in map
    newTodoFields.put("id", String.valueOf(this.generateId()));

    // Generate fields map based on new todo
    String[][] remainingFieldNames = {
        {"text", TODO_TEXT_INFO_COMMAND.getToken()},
        {"completed", COMPLETED_INFO_COMMAND.getToken()},
        {"due", DUE_INFO_COMMAND.getToken()},
        {"priority", PRIORITY_INFO_COMMAND.getToken()},
        {"category", CATEGORY_INFO_COMMAND.getToken()}};

    for (String[] fieldName : remainingFieldNames) {
      mapArgToField(newTodoFields, fieldName[0], fieldName[1]);
    }

    // Create a todo object and add to todolist
    todoManager.addTodo(new Todo(newTodoFields));
  }

  /**
   * Maps an argument from the command line to a Todo field.
   */
  private void mapArgToField(Map<String, String> todoFieldsMap, String key, String token) {
    String defaultValue = "?";
    todoFieldsMap.put(key, flags.getOrDefault(token, defaultValue));
  }

  /**
   * Generates and returns a unique ID for a new Todo.
   *
   * @return Integer id, the id for the new Todo object
   */
  private Integer generateId() {
    return todoManager.getListSize() + 1;
  }

  /**
   * Completes a todo based on input from the command line.
   */
  private void completeTodo(String idString) {
    String[] idStrings = idString.split(", ");

    for (String id : idStrings) {
      // https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/
      Integer idAsInteger = Integer.parseInt(id);
      todoManager.completeTodo(idAsInteger);
    }
  }

  /**
   * Displays todos based on commands from the command line.
   */
  private void displayTodos() throws ListIsEmptyException {
    String sortType = null;
    boolean filterIncomplete = false;
    String filterCategory = null;

    if (flags.containsKey(SORT_BY_PRIORITY_DISPLAY_COMMAND.getToken())) {
      sortType = "priority";
    } else if (flags.containsKey(SORT_BY_DATE_DISPLAY_COMMAND.getToken())) {
      sortType = "date";
    }

    if (flags.containsKey(SHOW_INCOMPLETE_DISPLAY_COMMAND.getToken())) {
      filterIncomplete = true;
    }

    if (flags.containsKey(SHOW_CATEGORY_DISPLAY_COMMAND.getToken())) {
      filterCategory = flags.get(SHOW_CATEGORY_DISPLAY_COMMAND.getToken());
    }

    todoManager.displayTodos(sortType, filterIncomplete, filterCategory);
  }
}
