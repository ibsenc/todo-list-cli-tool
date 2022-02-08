package problem1.utils;

import static problem1.model.Flag.ADD_TODO_COMMAND;
import static problem1.model.Flag.CATEGORY_INFO_COMMAND;
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

import java.util.Map;
import problem1.exceptions.CategoryValueNotFoundException;
import problem1.exceptions.CompleteTodoIdNotFoundException;
import problem1.exceptions.CsvFileFlagNotFoundException;
import problem1.exceptions.DisplayCommandNotFoundException;
import problem1.exceptions.DueDateNotFoundException;
import problem1.exceptions.MultipleSortCriteriaNotAllowedException;
import problem1.exceptions.NoArgumentsFoundException;
import problem1.exceptions.PriorityValueNotFoundException;
import problem1.exceptions.TodoTextNotFoundException;

/**
 * Class Validator validates the command line input.
 */

public class Validator {

  /**
   * Validates the flags within the command line arguments.
   *
   * @param flags - a map of flags and their corresponding values.
   */
  public static void validateFlags(Map<String, String> flags) {
    validateRequiredFlagsExist(flags);
    validateValues(flags);
  }

  /**
   * Validates that all required flags exist.
   *
   * @param flags - a map of flags.
   */
  private static void validateRequiredFlagsExist(Map<String, String> flags) {
    if (flags == null || flags.isEmpty()) {
      throw new NoArgumentsFoundException(
          "Command line arguments must be provided to run this program.");
    }

    // Check for csv file
    if (!flags.containsKey(CSV_FILE_COMMAND.getToken())) {
      throw new CsvFileFlagNotFoundException(CSV_FILE_COMMAND.getToken());
    }

    // Check for todo text if we have add todo command
    if (flags.containsKey(ADD_TODO_COMMAND.getToken()) &&
        !flags.containsKey(TODO_TEXT_INFO_COMMAND.getToken())) {
      throw new TodoTextNotFoundException(
          String.format("Todo description command (%s) is required when adding a todo.",
              TODO_TEXT_INFO_COMMAND.getToken()));
    }

    // If any sort or filter commands are given, display command must be given as well
    if ((flags.containsKey(SHOW_INCOMPLETE_DISPLAY_COMMAND.getToken()) ||
        flags.containsKey(SHOW_CATEGORY_DISPLAY_COMMAND.getToken()) ||
        flags.containsKey(SORT_BY_DATE_DISPLAY_COMMAND.getToken()) ||
        flags.containsKey(SORT_BY_PRIORITY_DISPLAY_COMMAND.getToken()))
        && !flags.containsKey(DISPLAY_COMMAND.getToken())) {
      throw new DisplayCommandNotFoundException(DISPLAY_COMMAND.getToken());
    }

    // Check if more than one sort type is provided
    if (flags.containsKey(SORT_BY_DATE_DISPLAY_COMMAND.getToken()) &&
        flags.containsKey(SORT_BY_PRIORITY_DISPLAY_COMMAND.getToken())) {
      throw new MultipleSortCriteriaNotAllowedException(
          SORT_BY_DATE_DISPLAY_COMMAND.getToken(),
          SORT_BY_PRIORITY_DISPLAY_COMMAND.getToken());
    }
  }

  /**
   * Validates the values corresponding to each flag.
   *
   * @param flags - a map of flags and their corresponding values.
   */
  private static void validateValues(Map<String, String> flags) {
    // Validate CSV file path
    FileReader.readFile(flags.get(CSV_FILE_COMMAND.getToken()), "CSV");

    // Validate todo text is not null or empty
    if (flags.containsKey(TODO_TEXT_INFO_COMMAND.getToken()) &&
        (flags.get(TODO_TEXT_INFO_COMMAND.getToken()) == null ||
            flags.get(TODO_TEXT_INFO_COMMAND.getToken()).isEmpty())) {
      throw new TodoTextNotFoundException("Todo description is required.");
    }

    // Validate due date is not null or empty
    if (flags.containsKey(DUE_INFO_COMMAND.getToken()) &&
        (flags.get(DUE_INFO_COMMAND.getToken()) == null ||
            flags.get(DUE_INFO_COMMAND.getToken()).isEmpty())) {
      throw new DueDateNotFoundException(DUE_INFO_COMMAND.getToken());
    }

    // Validate priority is not null or empty
    if (flags.containsKey(PRIORITY_INFO_COMMAND.getToken()) &&
        (flags.get(PRIORITY_INFO_COMMAND.getToken()) == null ||
            flags.get(PRIORITY_INFO_COMMAND.getToken()).isEmpty())) {
      throw new PriorityValueNotFoundException(PRIORITY_INFO_COMMAND.getToken());
    }

    // Validate category is not null or empty
    if (flags.containsKey(CATEGORY_INFO_COMMAND.getToken()) &&
        (flags.get(CATEGORY_INFO_COMMAND.getToken()) == null ||
            flags.get(CATEGORY_INFO_COMMAND.getToken()).isEmpty())) {
      throw new CategoryValueNotFoundException(
          "A category must be provided when using the category command: " +
              CATEGORY_INFO_COMMAND.getToken());
    }

    // Validate id exists for complete todo
    if (flags.containsKey(COMPLETE_TODO_COMMAND.getToken()) &&
        (flags.get(COMPLETE_TODO_COMMAND.getToken()) == null ||
            flags.get(COMPLETE_TODO_COMMAND.getToken()).isEmpty())) {
      throw new CompleteTodoIdNotFoundException(COMPLETE_TODO_COMMAND.getToken());
    }

    // Validate category is given for show category command
    if (flags.containsKey(SHOW_CATEGORY_DISPLAY_COMMAND.getToken()) &&
        (flags.get(SHOW_CATEGORY_DISPLAY_COMMAND.getToken()) == null ||
            flags.get(SHOW_CATEGORY_DISPLAY_COMMAND.getToken()).isEmpty())) {
      throw new CategoryValueNotFoundException(
          "A category must be provided when using the show category command: " +
              SHOW_CATEGORY_DISPLAY_COMMAND.getToken());
    }
  }
}
