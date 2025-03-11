package ru.JavaKanban.TaskManager;

import java.util.ArrayList;
import java.util.List;

import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.Task;

public interface TaskManager {
  public ArrayList<Task> getTaskHistory();

  ArrayList<Task> getAllTasks();

  ArrayList<Epic> getAllEpics();

  ArrayList<SubTask> getAllSubTasks();

  void addNewTask(Task task);

  void addNewEpic(Epic epic);

  void addNewSubTask(SubTask subTask);

  Task getTask(int id);

  SubTask getSubTask(int id);

  Epic getEpic(int id);

  List<SubTask> getEpicSubTasks(int id);

  void removeSubTaskById(int id);

  void removeEpicById(int epicId);

  void removeTaskById(int id);

  void clearAllEpics();

  void clearAllTasks();

  void clearAllSubTasks();

  void updateSubTask(SubTask subTask);

  void updateEpic(Epic epic);

  void updateTask(Task task);

}
