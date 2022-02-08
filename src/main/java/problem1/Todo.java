package problem1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.PriorityOutOfRangeException;

/**
 * Todo data structure
 */
public class Todo {

  /**
   * The constant ID.
   */
  public static final String ID = "ID";
  /**
   * The constant TEXT.
   */
  public static final String TEXT = "TEXT";
  /**
   * The constant COMPLETED.
   */
  public static final String COMPLETED = "COMPLETED";
  /**
   * The constant PLACEHOLDER.
   */
  public static final String PLACEHOLDER = "?";
  /**
   * The constant DUE.
   */
  public static final String DUE = "DUE";
  /**
   * The constant PRIORITY.
   */
  public static final String PRIORITY = "PRIORITY";
  /**
   * The constant CATEGORY.
   */
  public static final String CATEGORY = "CATEGORY";
  public static final String DATEPATTERN = "M/d/yyyy";

  private Integer id;
  private String text;
  private Boolean completed;
  private LocalDate due;
  private Integer priority;
  private String category;
  private Map<String, String> fields;

  /**
   * Instantiates a new To do.
   *
   * @param fields fields inside a todo
   * @throws PriorityOutOfRangeException  the priority out of range exception
   * @throws IllegalValueForTextException the illegal value for text exception
   */
  public Todo(Map<String, String> fields)
      throws PriorityOutOfRangeException, IllegalValueForTextException {
    this.fields = fields;
    this.instantiateMembers(fields);
  }

  private void instantiateMembers(Map<String, String> fields)
      throws IllegalValueForTextException, PriorityOutOfRangeException {
    for (Map.Entry<String, String> todo : fields.entrySet()) {
      if (todo.getKey().equalsIgnoreCase(ID)) {
        this.id = Integer.parseInt(todo.getValue());
      }

      if (todo.getKey().equalsIgnoreCase(TEXT)) {
        if (todo.getValue().isEmpty() || todo.getValue().equalsIgnoreCase(PLACEHOLDER)) {
          throw new IllegalValueForTextException("Text is a required field");
        }
        this.text = todo.getValue();
      }

      if (todo.getKey().equalsIgnoreCase(COMPLETED)) {
        if (todo.getValue() == null || todo.getValue().isEmpty() || todo.getValue()
            .equalsIgnoreCase(PLACEHOLDER)) {
          this.completed = false;
        } else {
          this.completed = Boolean.parseBoolean(todo.getValue());
        }
      }

      if (todo.getKey().equalsIgnoreCase(DUE)) {
        if (todo.getValue().equalsIgnoreCase(PLACEHOLDER)) {
          this.due = null;
        } else {
          try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEPATTERN);
            this.due = LocalDate.parse(todo.getValue(), formatter);
          } catch (DateTimeParseException de) {
            this.due = LocalDate.parse(todo.getValue());
          }
        }
      }

      if (todo.getKey().equalsIgnoreCase(PRIORITY)) {
        if (todo.getValue().equalsIgnoreCase(PLACEHOLDER)) {
          this.priority = 3;
        } else {
          this.priority = Integer.parseInt(todo.getValue());
        }
        this.validatePriority(this.priority);
      }

      if (todo.getKey().equalsIgnoreCase(CATEGORY)) {
        if (todo.getValue().equalsIgnoreCase(PLACEHOLDER)) {
          this.category = null;
        }
        // Needs else statement for all other ifs to replace the "?"
        else {
          this.category = todo.getValue();
        }

      }
    }

  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Gets completed.
   *
   * @return the completed
   */
  public Boolean getCompleted() {
    return completed;
  }

  /**
   * Gets due.
   *
   * @return the due
   */
  public LocalDate getDue() {
    return due;
  }

  /**
   * Gets priority.
   *
   * @return the priority
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Gets category.
   *
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Gets fields.
   *
   * @return the fields
   */
  public Map<String, String> getFields() {
    return fields;
  }

  /**
   * Validates priority
   *
   * @param priority todo's priority
   */
  private void validatePriority(Integer priority) throws PriorityOutOfRangeException {
    if (priority < 1 || priority > 3) {
      throw new PriorityOutOfRangeException("Priority should be between 1 and 3");
    }
  }

  /**
   * Sets completed.
   *
   * @param completed the completed
   */
  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Todo)) {
      return false;
    }
    Todo todo = (Todo) o;
    return Objects.equals(getId(), todo.getId()) && Objects
        .equals(getText(), todo.getText()) && Objects
        .equals(getCompleted(), todo.getCompleted()) && Objects
        .equals(getDue(), todo.getDue()) && Objects
        .equals(getPriority(), todo.getPriority()) && Objects
        .equals(getCategory(), todo.getCategory()) && Objects
        .equals(getFields(), todo.getFields());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getText(), getCompleted(), getDue(), getPriority(), getCategory(),
        getFields());
  }

  @Override
  public String toString() {
    return "ToDo{" +
        "id=" + id +
        ", text='" + text + '\'' +
        ", completed=" + completed +
        ", due=" + due +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        '}';
  }
}
