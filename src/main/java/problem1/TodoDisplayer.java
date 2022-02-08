package problem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import problem1.exceptions.ListIsEmptyException;

/**
 * object TodoDisplayer which facilitates the display of the list using print line Uses only static
 * methods
 */
public class TodoDisplayer {

  private static List<Todo> todoList;

  /**
   * Checks that the list is not null
   *
   * @throws ListIsEmptyException- throws exception when list is null; cannot display an empty
   *                               list.
   */
  public static void validateList() throws ListIsEmptyException {
    if (TodoDisplayer.todoList == null) {
      throw new ListIsEmptyException("To do list cannot be empty.");
    }
  }

  /**
   * Sets the list as a list of todos
   *
   * @param list - creates todolist which is used by other methods to represent the list of todos
   */
  public static void setList(List<Todo> list) {
    todoList = list;
  }

  /**
   * method which sorts items by due date or priority
   *
   * @param sortType - takes in either Due date or Priority and calls sort by date or sort by
   *                 priority methods.
   * @throws ListIsEmptyException - thrown if the list is empty, thrown because sortbydate and
   *                              sortByPriority both call validateList which throws this
   *                              exception.
   */
  public static void sort(String sortType) throws ListIsEmptyException {
    if (sortType.equalsIgnoreCase("DATE")) {
      sortByDate();
    }
    if (sortType.equalsIgnoreCase("PRIORITY")) {
      sortByPriority();
    }
  }

  /**
   * method sortBypriority which takes the priority of each todo and sorts them with the highest
   * priority items at the top (priority 1, 2, 3..)
   *
   * @throws ListIsEmptyException - thrown if the list is empty, thrown because validateList is
   *                              called which throws this exception.
   */
  public static void sortByPriority() throws ListIsEmptyException {
    validateList();
    Collections.sort(todoList, new Comparator<Todo>() {
      @Override
      public int compare(Todo o1, Todo o2) {
        // If neither object has a priority, they are considered equal
        if (o1.getPriority() == null && o2.getPriority() == null) {
          return 0;
        }
        // If the current object priority is null and hits this else if, we know the other has a
        // priority and should be prioritized
        else if (o1.getPriority() == null) {
          return 1;
        }
        // If the other object priority is null and hits this else if, we know the current has a
        // priority and should be prioritized
        else if (o2.getPriority() == null) {
          return -1;
        }
        // Both objects have a priority so can be properly compared without the risk of a NPE (Null Pointer Exception)
        else {
          return o1.getPriority().compareTo(o2.getPriority());
        }
      }
    });

  }

  /**
   * method sortByDate which takes the date of each todo and sorts them according to the nearest
   * date (nearest date at the top of list, furthest date at the bottom)
   *
   * @throws ListIsEmptyException - thrown if the list is empty, thrown because validateList * is
   *                              called which throws this exception.
   */

  public static void sortByDate() throws ListIsEmptyException {
    validateList();
    Collections.sort(todoList, Comparator.nullsLast(new Comparator<Todo>() {
          @Override
          public int compare(Todo o1, Todo o2) {
            // If neither object has a due date, they are considered equal
            if (o1.getDue() == null && o2.getDue() == null) {
              return 0;
            }
            // If the current object due is null and hits this else if, we know the other has a due
            // date and should be prioritized
            else if (o1.getDue() == null) {
              return 1;
            }
            // If the other object due is null and hits this else if, we know the current has a due
            // date and should be prioritized
            else if (o2.getDue() == null) {
              return -1;
            }
            // Both objects have a due date so can be properly compared without the risk of a
            // NPE (Null Pointer Exception)
            else {
              return o1.getDue().compareTo(o2.getDue());
            }
          }
        })
    );
  }

  /**
   * Calls filter incomplete or filter category methods to filter the list
   *
   * @param filterIncomplete - returns true if completed items have been filtered from the list
   * @param filterCategory   - filters the list by String Category
   * @throws ListIsEmptyException - Filterincomplete and filter category call validate list which
   *                              throws this exception
   */
  public static void filter(Boolean filterIncomplete, String filterCategory) throws
      ListIsEmptyException {
    if (filterIncomplete) {
      filterIncomplete();
    }
    if (filterCategory != null) {
      filterCategory(filterCategory);
    }

  }

  /**
   * method filter incomplete which creates a new instance of the list with only incomplete todos
   *
   * @throws ListIsEmptyException - thrown because validate list is called, which throws this
   *                              exception
   */
  public static void filterIncomplete() throws ListIsEmptyException {
    validateList();
    List incompleteList = new ArrayList();
    for (Todo todo : todoList) {
      if (!todo.getCompleted()) {
        incompleteList.add(todo);
      }
    }
    todoList = incompleteList;
  }

  /**
   * Takes in a string with the target category, filters the list based on this category
   *
   * @param targetCategory - String, representing the target category to sort the list by
   * @throws ListIsEmptyException - thrown because validate list is called, which throws this
   *                              exception
   */
  public static void filterCategory(String targetCategory) throws ListIsEmptyException {
    validateList();
    List categoryList = new ArrayList();
    for (Todo todo : todoList) {
      if (todo.getCategory() != null && todo.getCategory().equals(targetCategory)) {
        categoryList.add(todo);
      }
    }
    todoList = categoryList;

  }

  /**
   * displays the todolist (after being sorted and/or filtered) using println
   */
  public static void display() {
    for (Todo todo : todoList) {
      System.out.println(todo.toString());
    }
  }

}

