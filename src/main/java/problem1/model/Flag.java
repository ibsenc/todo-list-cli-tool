package problem1.model;

/**
 * Enum Flag defines the acceptable flags that can be passed in via the command line.
 */

public enum Flag {

  CSV_FILE_COMMAND("--csv-file"),
  ADD_TODO_COMMAND("--add-todo"),
  TODO_TEXT_INFO_COMMAND("--todo-text"),
  COMPLETED_INFO_COMMAND("--completed"),
  DUE_INFO_COMMAND("--due"),
  PRIORITY_INFO_COMMAND("--priority"),
  CATEGORY_INFO_COMMAND("--category"),
  COMPLETE_TODO_COMMAND("--complete-todo"),
  DISPLAY_COMMAND("--display"),
  SHOW_INCOMPLETE_DISPLAY_COMMAND("--show-incomplete"),
  SHOW_CATEGORY_DISPLAY_COMMAND("--show-category"),
  SORT_BY_DATE_DISPLAY_COMMAND("--sort-by-date"),
  SORT_BY_PRIORITY_DISPLAY_COMMAND("--sort-by-priority");

  private final String token;

  /**
   * Sets the flag's token.
   */
  Flag(String token) {
    this.token = token;
  }

  /**
   * Gets the flag's token.
   *
   * @return token, the flag's token
   */
  public String getToken() {
    return this.token;
  }
}
