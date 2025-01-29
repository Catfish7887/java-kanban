package ru.JavaKanban.utils;
import ru.JavaKanban.HistoryManager.HistoryManager;
import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.InMemoryTaskManager;
import ru.JavaKanban.TaskManager.TaskManager;

public class Managers {
  public static TaskManager getDefault() {
    return new InMemoryTaskManager(new InMemoryHistoryManager());
  }

  public static HistoryManager getHistoryManager() {
    return new InMemoryHistoryManager();

  }
}
