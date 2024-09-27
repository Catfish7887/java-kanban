package ru.JavaKanban.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;

import ru.JavaKanban.Tasks.*;

public class TaskManager {
  private HashMap<Integer, Task> tasks;
  private HashMap<Integer, SubTask> subTasks;
  private HashMap<Integer, Epic> epics;
  int id = 0;

  public TaskManager() {
    tasks = new HashMap<>();
    subTasks = new HashMap<>();
    epics = new HashMap<>();
  }

  public void addNewTask(Task task) {
    int taskId = this.id++;
    tasks.put(taskId, task);
    System.out.println("Задача добавлена " + task);
  }

  public void addNewEpic(Epic epic) {
    int epicId = this.id++;
    epics.put(epicId, epic);
  }

  public void addNewSubTask(SubTask subTask) {
    int taskId = this.id++;
    int epicId = subTask.getEpicId();
    Epic epic = epics.get(epicId);
    epic.addSubTaskId(taskId);
    this.subTasks.put(taskId, subTask);
  }

  public void updateSubTask(SubTask subTask) {
    int id = subTask.getId();
    Epic epic = epics.get(subTask.getEpicId());
    subTasks.put(id, subTask);
    if (checkAllSubTasksComplete(epic)) {

    }
  }

  public void updateEpic(Epic epic) {
    int id = epic.getId();
    epics.put(id, epic);
  }

  public void updateTask(Task task) {
    int id = task.getId();
    tasks.put(id, task);
  }

  private boolean checkAllSubTasksComplete(Epic epic) {
    ArrayList<Integer> ids = epic.getSubtasksIds();
    for (int id : ids) {
      SubTask subTask = this.subTasks.get(id);
      if (subTask.getStatus() == TaskStatus.NEW || subTask.getStatus() == TaskStatus.IN_PROGRESS)
        return false;
    }
    return true;
  }

}
