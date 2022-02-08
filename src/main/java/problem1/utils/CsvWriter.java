package problem1.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import problem1.Todo;

public class CsvWriter {

  private static final String CSV_DELIMITER = "\"\"\"";
  private static final String DOUBLE_DELIMITER = "\"\"\",\"\"\"";
  private static final String DATE_PATTERN = "M/d/yyyy";

  /**
   * Writes todo list to csv file
   *
   * @param todoList List of ToDos
   * @param csvPath  the output file path
   */
  public static void writeToCsv(List<Todo> todoList, String csvPath) {
    try (BufferedWriter outputFile = new BufferedWriter(
        new FileWriter(csvPath))) {
      outputFile.write(getHeader());
      // https://github.ccs.neu.edu/cs5004-sea-summer2021/lecture_material/blob/master/Lecture13/src/main/java/functional/MiscExamples.java#L14
      todoList.stream().forEach(todo -> {
        try {
          outputFile.write(getTodoText(todo));
        } catch (IOException ioe) {
          System.out.println("Something went wrong! : " + ioe.getMessage());
          ioe.printStackTrace();
        }
      });
    } catch (Exception fnfe) {
      System.out.println("*** OOPS! A file was not found : " + fnfe.getMessage());
      fnfe.printStackTrace();
    }
  }

  /**
   * Generates each Todo line of the csv file
   *
   * @param todo a todo
   * @return the String representing that line of the csv file
   */
  private static String getTodoText(Todo todo) {
    String todoText = ((todo.getId() != null) ? todo.getId().toString() : "?");
    todoText += "," + CSV_DELIMITER + ((todo.getText() != null) ? todo.getText() : "?");
    todoText += DOUBLE_DELIMITER +
        ((todo.getCompleted() != null) ? todo.getCompleted().toString() : "?");
    todoText += DOUBLE_DELIMITER +
        ((todo.getDue() != null) ? todo.getDue().format(DateTimeFormatter.ofPattern(DATE_PATTERN))
            : "?");
    todoText += DOUBLE_DELIMITER +
        ((todo.getPriority() != null) ? todo.getPriority().toString() : "?");
    todoText += DOUBLE_DELIMITER +
        ((todo.getCategory() != null) ? todo.getCategory() : "?") + CSV_DELIMITER;
    return todoText + System.lineSeparator();
  }

  /**
   * Generates the first line of the csv file
   *
   * @return a String that represents the first line of the file
   */
  private static String getHeader() {
    String[] fieldNames = {"id", "text", "completed", "due", "priority", "category"};

    StringBuilder headerString = new StringBuilder();
    for (String fieldName : fieldNames) {
      headerString.append(CSV_DELIMITER).append(fieldName).append(CSV_DELIMITER).append(",");
    }
    return headerString + System.lineSeparator();
  }
}
