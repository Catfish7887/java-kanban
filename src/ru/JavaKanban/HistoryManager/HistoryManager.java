package ru.JavaKanban.HistoryManager;
import java.util.ArrayList;
import ru.JavaKanban.Tasks.Task;
public interface HistoryManager {
  void addToHistory(Task task);
  ArrayList<Task> getHistory();
}
