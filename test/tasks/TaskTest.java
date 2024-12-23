package test.tasks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ru.JavaKanban.Tasks.Task;
import ru.JavaKanban.Tasks.TaskStatus;

public class TaskTest {

  @Test
  void taskWithsimillarIdsShouldBeEquals() {
    Task task1 = new Task("name", "desc", 10, TaskStatus.NEW);
    Task task2 = new Task("name", "123", 10, TaskStatus.NEW);
    assertEquals(task1, task2);
  }
}
