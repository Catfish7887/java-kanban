import java.io.File;
import java.io.IOException;

import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.FileBackedTaskManager;
import ru.JavaKanban.Tasks.*;

class Main {
  public static void main(String[] args) {
    try {
      File file = File.createTempFile("file", ".csv");

      FileBackedTaskManager taskManager = new FileBackedTaskManager(new InMemoryHistoryManager(), file);
      taskManager.addNewTask(new Task("mamam", "sss"));
      taskManager.addNewEpic(new Epic("SSSSSS", "ssssss"));
      taskManager.addNewSubTask(new SubTask("SSSSSSS", "ss", 10, 1, TaskStatus.DONE));
      FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

      System.out.println(loadedManager.getAllTasks());
      System.out.println(loadedManager.getAllEpics());
      System.out.println(loadedManager.getAllSubTasks());

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
