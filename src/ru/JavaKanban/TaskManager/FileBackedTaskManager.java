package ru.JavaKanban.TaskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ru.JavaKanban.Exceptions.ManagerSaveException;
import ru.JavaKanban.HistoryManager.HistoryManager;
import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.Task;

public class FileBackedTaskManager extends InMemoryTaskManager {
  private File backupFile;
  private static final String CSV_FIRSTLINE = "id,type,name,status,description,epic/subId";

  public FileBackedTaskManager(HistoryManager historyManager, File file) {
    super(historyManager);
    this.backupFile = file;
  }

  private void save() {
    List<Task> tasks = this.getAllTasks();
    List<SubTask> subTasks = this.getAllSubTasks();
    List<Epic> epics = this.getAllEpics();

    try (FileWriter writer = new FileWriter(backupFile)) {
      writer.write(CSV_FIRSTLINE + "\n");

      for (Task task : tasks) {
        // TODO Переписать метод записи. использовать Files.write()
        writer.write(task.toString() + "\n");
      }

      // TODO Дописать toString() для эпиков и сабтасков
      for (Epic epic : epics) {
        writer.write(epic.toString() + "\n");
      }

      for (SubTask subtask : subTasks) {
        writer.write(subtask.toString() + "\n");
      }

    } catch (IOException e) {
      throw new ManagerSaveException("Ошибка при сохранении файла ", e);
    }
  }

}
