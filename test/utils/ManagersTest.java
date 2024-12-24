package test.utils;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.InMemoryTaskManager;
import ru.JavaKanban.utils.Managers;

public class ManagersTest {
  @Test
  void testUtilClassReturnsReadyForWorkClasses() {
    assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    assertInstanceOf(InMemoryHistoryManager.class, Managers.getHistoryManager());
  }
}
