package problem1;

import problem1.exceptions.DuplicateIDException;
import problem1.exceptions.IllegalValueForTextException;
import problem1.exceptions.ListIsEmptyException;
import problem1.exceptions.PriorityOutOfRangeException;

public class Main {

  public static void main(String[] args)
      throws PriorityOutOfRangeException, IllegalValueForTextException, ListIsEmptyException, DuplicateIDException {
    TodoManager todoManager = new TodoManager();
    TodoApplication todoApplication = new TodoApplication(todoManager);

    todoApplication.setFlags(args);
    todoApplication.processFlags();
  }
}
