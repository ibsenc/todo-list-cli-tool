package problem1.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import problem1.Todo;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.InvalidFilePathException;
import problem1.exceptions.PriorityOutOfRangeException;

/**
 * Class CsvParser parses the CSV file to generate Todo objects.
 */

public class CsvParser {

  /**
   * Parses the CSV file to generate Todos.
   *
   * @param path a String representing the path to the CSV file.
   * @return a list of Todos.
   */
  public static List<Todo> parseTodosCsv(String path) {
    List<Todo> todoList = new ArrayList<>();

    // Read file line by line
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> lines = stream.collect(Collectors.toList());

      // Generate fields maps for all todos
      final List<Map<String, String>> todoFieldsList = generateTodoFields(lines);

      // Create todo with its fields and add to todoList
      for (Map<String, String> todoFields : todoFieldsList) {
        Todo currentTodo = new Todo(todoFields);
        todoList.add(currentTodo);
      }
    } catch (IOException | PriorityOutOfRangeException | IllegalValueForTextException e) {
      throw new InvalidFilePathException("Csv", path);
    }

    return todoList;
  }

  /**
   * Generates a list of maps representing the fields for each Todo in the file.
   *
   * @param lines a list of Strings representing each line in the file.
   * @return a list of fields maps.
   */
  private static List<Map<String, String>> generateTodoFields(List<String> lines) {

    List<Map<String, String>> allTodosFieldsMaps = new ArrayList<>();
    Map<Integer, String> fieldNamesMap = new HashMap<>();

    // Iterate over each line representing one person at a time
    for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
      // Extract fields from the current line
      List<String> currentFields = extractFields(lines.get(lineIndex));

      // Process the lines
      if (lineIndex == 0) {
        currentFields = currentFields.subList(1, currentFields.size());
        fieldNamesMap = generateFieldNamesMap(currentFields);
      } else {
        // Add the current person fields to all person fields
        allTodosFieldsMaps.add(generateTodoFields(currentFields, fieldNamesMap));
      }
    }

    return allTodosFieldsMaps;
  }

  /**
   * Generates a map representing the fields of a single todo.
   *
   * @param currentFields a list of Strings representing the fields of a todo
   * @param fieldNamesMap a map of index and field name.
   * @return a map containing a todo's fields.
   */
  private static Map<String, String> generateTodoFields(List<String> currentFields,
      Map<Integer, String> fieldNamesMap) {
    Map<String, String> todoFields = new HashMap<>();

    // Map field title to todo field
    for (int fieldIndex = 0; fieldIndex < currentFields.size(); fieldIndex++) {
      String key = fieldNamesMap.get(fieldIndex);
      String value = currentFields.get(fieldIndex);
      todoFields.put(key, value);
    }

    return todoFields;
  }

  /**
   * Generates a map of field indices and their corresponding field titles.
   *
   * @param firstLineFields a list of Strings representing the fields in the first line of the file
   * @return a map containing indices and field names.
   */
  private static Map<Integer, String> generateFieldNamesMap(List<String> firstLineFields) {
    Map<Integer, String> fieldNamesMap = new HashMap<>();

    for (int fieldIndex = 0; fieldIndex < firstLineFields.size(); fieldIndex++) {
      fieldNamesMap.put(fieldIndex, firstLineFields.get(fieldIndex));
    }

    return fieldNamesMap;
  }

  /**
   * Generates a list of fields from a given line of the file.
   *
   * @param line a String representing a line in the file.
   * @return a list of Strings representing the fields within that line.
   */
  private static List<String> extractFields(String line) {
    // Separate each field by double quote alone or double quote, comma, double quote
    final String delimiterPattern = "(,*\"+(,\"*)*)";

    // Create an array of Strings representing the fields in that line
    String[] str = line.split(delimiterPattern);

    // Turn it into a list
    List<String> fields = Arrays.asList(str);

    return fields;
  }
}
