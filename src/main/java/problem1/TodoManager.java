package problem1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import problem1.exceptions.DuplicateIDException;
import problem1.exceptions.ListIsEmptyException;
import problem1.utils.CsvWriter;

/**
 * object TodoManager that creates a list of Todo items and designates the csv path
 */
public class TodoManager {

  private List<Todo> todoList;
  private String csvPath;

  /**
   * Constructor with no arguments
   */
  public TodoManager() {
    this.todoList = new ArrayList<>();
    this.csvPath = null;
  }

  /**
   * set's the csv path based on the string provided
   *
   * @param csvPath - String where the updated csv file should go
   */
  public void setCsvPath(String csvPath) {
    this.csvPath = csvPath;
  }

  /**
   * checks that the todo ID is not already in the list, if it is a unique todo item, adds it to the
   * list
   *
   * @param newTodo - takes in the new todo item then traverses the list to make sure the Id of
   *                newtodo is not already in the list.
   * @throws DuplicateIDException - throws exception if system attemps to add a todo item that is
   *                              already on the list
   */
  public void addTodo(Todo newTodo) throws DuplicateIDException {

    if (newTodo == null) {
      throw new IllegalArgumentException("To do item cannot be null.");
    }
    for (Todo todo : todoList) {
      if (todo.getId().equals(newTodo.getId())) {
        throw new DuplicateIDException("Cannot add duplicate to do ID.");
      }
    }
    todoList.add(newTodo);
  }


  /**
   * takes in the id of the todo to be completed. calls method set completed from Todo class and
   * sets it to true
   *
   * @param ID - Integer, representing the ID of the todo to be checked as completed.
   */
  public void completeTodo(Integer ID) {
    if (ID != null) {
      for (Todo todo : todoList) {
        if (todo.getId().equals(ID)) {
          todo.setCompleted(true);
        }
      }
    }
  }

  /**
   * gets the size of the todo list
   *
   * @return - Integer which represents the number of todos on the list.
   */
  public Integer getListSize() {
    return todoList.size();
  }

  /**
   * boolean that checks if the todo list is empty
   *
   * @return - true if the size of the todolist is zero
   */
  public boolean isEmpty() {
    return todoList.size() == 0;
  }

  /**
   * Displays the todo items with printline function
   *
   * @param sortType         - String representing the value the list is being sorted by (if any)
   * @param filterIncomplete - boolean, set to true if the list should return only incomplete items
   * @param filterCategory   - String, represents the category by which the list should be sorted
   *                         (if any)
   * @throws ListIsEmptyException - throws exception if the list is empty, cannot display an empty
   *                              list.
   */
  public void displayTodos(String sortType, boolean filterIncomplete,
      String filterCategory) throws ListIsEmptyException {
    if (this.todoList.isEmpty()) {
      throw new ListIsEmptyException("Cannot display an empty list.");
    }

    TodoDisplayer.setList(todoList);
    if (sortType != null) {
      TodoDisplayer.sort(sortType);
    }
    if (filterIncomplete || filterCategory != null) {
      TodoDisplayer.filter(filterIncomplete, filterCategory);
    }
    TodoDisplayer.display();
  }

  /**
   * Writes the list to a csv file and saves it
   *
   * @throws IllegalArgumentException - throws illegal argument if there is no csv file path, cannot
   *                                  add a csv if there's no path to story it.
   */
  public void writeToCsv() throws IllegalArgumentException {
    if (csvPath == null) {
      throw new IllegalArgumentException("CSV file path cannot be null.");
    }
    CsvWriter.writeToCsv(todoList, csvPath);
  }

  /**
   * Overrides equals to compare two objects, includes csv path and todoList
   *
   * @param o - object o for comparison
   * @return - new equals method with todoList and csv path
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TodoManager)) {
      return false;
    }
    TodoManager that = (TodoManager) o;
    return Objects.equals(todoList, that.todoList) && Objects
        .equals(csvPath, that.csvPath);
  }

  /**
   * overrides hashcode to return a new hashcode which includes todolist and csv path
   *
   * @return - new hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(todoList, csvPath);
  }

  /**
   * Overrides toString to include todoList and csv path
   *
   * @return - new toString with todoList (uses StringHelper private method for testing) and csv
   * path.
   */
  @Override
  public String toString() {
    return "TodoManager{" +
        "todoList=" + toStringHelper(todoList) +
        ", csvPath='" + csvPath + '\'' +
        '}';
  }

  /**
   * private method to make toString testing possible, since there is no getter for todoList
   *
   * @param todoList - List, representing todoList
   * @return
   */
  private String toStringHelper(List<Todo> todoList) {
    String todoToString = "";
    for (Todo todo : todoList) {
      todoToString = todo.toString();
      todoToString = todoToString + " ";
    }
    return todoToString;
  }
}
