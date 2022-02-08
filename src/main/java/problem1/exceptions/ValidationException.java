package problem1.exceptions;

/**
 * An exception representing a generic validation exception. When a user provides an illegal
 * combination of inputs, provides a helpful error message, and a short explanation of how to use
 * the program along with examples.
 */

public class ValidationException extends RuntimeException {

  /**
   * Constructs a new exception with the specified message.
   */
  public ValidationException(String message) {
    super(message + "\n\nThe program takes in the following input flags:\n"
        + "--csv-file <path/to/file>            The CSV file containing the todos. This option is\n"
        + "                                     required.\n"
        + "\n"
        + "--add-todo                           Add a new todo. If this option is provided, then\n"
        + "                                     --todo-text must also be provided.\n"
        + "\n"
        + "--todo-text <description of todo>    A description of the todo.\n"
        + "\n"
        + "--completed                          (Optional) Sets the completed status of a new todo\n"
        + "                                     to true.\n"
        + "\n"
        + "--due <due date>                     (Optional) Sets the due date of a new todo. Date\n"
        + "                                     should be in mm/dd/yyyy format.\n"
        + "\n"
        + "--priority <1, 2, or 3>              (Optional) Sets the priority of a new todo. The\n"
        + "                                     value can be 1, 2, or 3 (where 1 = highest priority).\n"
        + "\n"
        + "--category <a category name>         (Optional) Sets the category of a new todo. The\n"
        + "                                     value can be any String. Categories do not need to\n"
        + "                                     be pre-defined.\n"
        + "\n"
        + "--complete-todo <id>                 Mark the Todo with the provided ID as complete.\n"
        + "\n"
        + "--display                            Display todos. If none of the following optional\n"
        + "                                     arguments are provided, displays all todos.\n"
        + "\n"
        + "--show-incomplete                    (Optional) If --display is provided, only incomplete\n"
        + "                                     todos should be displayed.\n"
        + "\n"
        + "--show-category <category>           (Optional) If --display is provided, only todos with\n"
        + "                                     the given category should be displayed.\n"
        + "\n"
        + "--sort-by-date                       (Optional) If --display is provided, sort the list\n"
        + "                                     of todos by date order (ascending). Cannot be\n"
        + "                                     combined with --sort-by-priority.\n"
        + "\n"
        + "--sort-by-priority                   (Optional) If --display is provided, sort the list\n"
        + "                                     of todos by priority (ascending). Cannot be\n"
        + "                                     combined with --sort-by-date.\n"
        + "\n"
        + "Example: --csv-file src/resources/todos.csv --add-todo --todo-text A complicated todo.\n"
        + "--completed --due 8/4/2021 --complete-todo 2 --priority 2 --category school\n"
        + "--complete-todo 3 --display --show-incomplete --show-category school --sort-by-date\n");
  }
}
