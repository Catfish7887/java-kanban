package ru.JavaKanban.TaskManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import ru.JavaKanban.Exceptions.ManagerLoadException;
import ru.JavaKanban.Exceptions.ManagerSaveException;
import ru.JavaKanban.HistoryManager.HistoryManager;
import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.Tasks.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
  private File backupFile;
  private static final String CSV_FIRSTLINE = "id,type,name,status,description,epicID";
  // TODO Добавить сериализацию нового типа задачи, добавить дату и длительность в строку CSV
  public FileBackedTaskManager(HistoryManager historyManager, File file) {
    super(historyManager);
    this.backupFile = file;
  }

  public static FileBackedTaskManager loadFromFile(File file) {
    FileBackedTaskManager manager = new FileBackedTaskManager(new InMemoryHistoryManager(), file);

    List<String> lines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
      while (br.ready()) {
        String line = br.readLine();
        if (line.isEmpty()) {
          continue;
        }
        lines.add(line);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Файл не найден " + e.getMessage());
    } catch (IOException e) {
      throw new ManagerLoadException("Ошибка при чтении файла", e);
    }

    for (int i = 1; i < lines.size(); i++) {
      String data = lines.get(i);
      Task task = manager.fromString(data);
      TaskType type = task.getType();

      switch (type.toString()) {
        case "EPIC":
          manager.loadEpicFromFile((Epic) task);
          break;
        case "SUBTASK":
          manager.loadSubTaskFromFile((SubTask) task);
          break;
        default:
          manager.loadTaskFromFile(task);
      }

      if (manager.newId <= task.getId()) {
        manager.newId = task.getId() + 1;
      }
    }

    return manager;
  }

  private void save() {
    List<String> textToFile = new ArrayList<>();
    List<Task> tasks = this.getAllTasks();
    List<SubTask> subTasks = this.getAllSubTasks();
    List<Epic> epics = this.getAllEpics();

    try (FileWriter writer = new FileWriter(backupFile)) {
      textToFile.add(CSV_FIRSTLINE);

      for (Task task : tasks) {
        textToFile.add(task.toString());
      }

      for (Epic epic : epics) {
        textToFile.add(epic.toString());
      }

      for (SubTask subtask : subTasks) {
        textToFile.add(subtask.toString());
      }

      Files.write(backupFile.toPath(), textToFile, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new ManagerSaveException("Ошибка при сохранении файла ", e);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  // id,type,name,status,description,epic/subId
  private Task fromString(String value) {
    String[] taskData = value.split(",");
    int id = Integer.parseInt(taskData[0].trim());
    TaskType type = TaskType.valueOf(taskData[1].trim());
    String name = taskData[2].trim();
    TaskStatus status = TaskStatus.valueOf(taskData[3].trim());
    String description = taskData[4].trim();

    switch (type) {
      case TASK:
        return new Task(name, description, id, status);
      case EPIC:
        return new Epic(name, description, id);
      case SUBTASK:
        int epicID = Integer.parseInt(taskData[5].trim());
        return new SubTask(name, description, id, epicID, status);
      default:
        throw new IllegalArgumentException("Неверный тип задачи");
    }
  }

  private void loadEpicFromFile(Epic epic) {
    this.getEpicHashMap().put(epic.getId(), epic);

  }

  private void loadSubTaskFromFile(SubTask task) {
    int epicId = task.getEpicId();
    Epic epic = this.getEpicHashMap().get(epicId);
    this.getSubHashMap().put(task.getId(), task);
    epic.addSubTaskId(task.getId());
  }

  private void loadTaskFromFile(Task task) {
    this.getTaskHashMap().put(task.getId(), task);
  }

  @Override
  public void addNewEpic(Epic epic) {
    super.addNewEpic(epic);
    save();
  }

  @Override
  public void addNewSubTask(SubTask subTask) {
    super.addNewSubTask(subTask);
    save();
  }

  @Override
  public void addNewTask(Task task) {
    super.addNewTask(task);
    save();
  }

  @Override
  public void removeEpicById(int epicId) {
    super.removeEpicById(epicId);
    save();
  }

  @Override
  public void removeSubTaskById(int id) {
    super.removeSubTaskById(id);
    save();
  }

  @Override
  public void removeTaskById(int id) {
    super.removeTaskById(id);
    save();
  }

  @Override
  public void updateEpic(Epic epic) {
    super.updateEpic(epic);
    save();
  }

  @Override
  public void updateSubTask(SubTask subTask) {
    super.updateSubTask(subTask);
    save();
  }

  @Override
  public void updateTask(Task task) {
    super.updateTask(task);
    save();
  }

  @Override
  public void clearAllEpics() {
    super.clearAllEpics();
    save();
  }

  @Override
  public void clearAllSubTasks() {
    super.clearAllSubTasks();
    save();
  }

  @Override
  public void clearAllTasks() {
    super.clearAllTasks();
    save();
  }
}
