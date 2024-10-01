package ru.JavaKanban.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;

import ru.JavaKanban.Tasks.*;

public class TaskManager {
  private HashMap<Integer, Task> tasks;
  private HashMap<Integer, SubTask> subTasks;
  private HashMap<Integer, Epic> epics;
  private int newId = 0;

  public TaskManager() {
    tasks = new HashMap<>();
    subTasks = new HashMap<>();
    epics = new HashMap<>();
  }

  public ArrayList<Task> getAllTasks() {
    return new ArrayList<>(tasks.values());
  }

  public ArrayList<Epic> getAllEpics() {
    return new ArrayList<>(epics.values());
  }

  public ArrayList<SubTask> getAllSubTasks() {
    return new ArrayList<>(subTasks.values());
  }

  public void addNewTask(Task task) {
    int taskId = this.newId++;
    task.setId(taskId);
    tasks.put(taskId, task);
  }

  public void addNewEpic(Epic epic) {
    int epicId = this.newId++;
    epic.setId(epicId);
    epics.put(epicId, epic);
  }

  public void addNewSubTask(SubTask subTask) {
    int taskId = this.newId++;
    subTask.setId(taskId);
    int epicId = subTask.getEpicId();
    Epic epic = epics.get(epicId);
    epic.addSubTaskId(taskId);
    this.subTasks.put(taskId, subTask);
    generateNewAndUpdateEpic(epic);
  }

  public String getTask(int id) {
    return tasks.get(id).toString();
  }

  public String getSubTask(int id) {
    return subTasks.get(id).toString();
  }

  public String getEpic(int id) {
    return epics.get(id).toString();
  }

  public ArrayList<SubTask> getEpicSubTasks(int id) {
    Epic epic = epics.get(id);
    ArrayList<SubTask> result = new ArrayList<>();
    for (int subTaskId : epic.getSubtasksIds()) {
      result.add(subTasks.get(subTaskId));
    }
    return result;
  }

  public void removeSubTaskById(int id) {
    SubTask task = subTasks.get(id);
    int epicId = task.getEpicId();
    Epic epic = epics.get(epicId);
    epic.removeSubIdByValue(id);
    subTasks.remove(id);
    generateNewAndUpdateEpic(epic);

  }

  public void removeEpicById(int epicId) {
    Epic epic = epics.get(epicId);
    for (int subId : epic.getSubtasksIds()) {
      subTasks.remove(subId);
    }
    epics.remove(epicId);


  }

  public void removeTaskById(int id) {
    tasks.remove(id);
  }

  public void clearAllEpics() {
    epics.clear();
    // Очищаю и подзадачи, так как без эпиков их существование не имеет смысла
    subTasks.clear();
  }

  public void clearAllTasks() {
    tasks.clear();
  }

  public void clearAllSubTasks() {
    subTasks.clear();
    for (Epic epic : epics.values()) {
      epic.clearSubIds();
      calculateAndSetEpicStatus(epic);
    }
  }

  public void updateSubTask(SubTask subTask) {
    int id = subTask.getId();
    subTasks.put(id, subTask);
    System.out.println(subTasks);
    Epic epic = epics.get(subTask.getEpicId());
    generateNewAndUpdateEpic(epic);
  }

  public void updateEpic(Epic epic) {
    int id = epic.getId();
    epics.put(id, epic);
  }

  public void generateNewAndUpdateEpic(Epic epic) {
    Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId());
    ArrayList<Integer> subIds = epic.getSubtasksIds();
    for (Integer subId : subIds) {
      newEpic.addSubTaskId(subId);
    }
    calculateAndSetEpicStatus(newEpic);
    updateEpic(newEpic);
  }

  public void updateTask(Task task) {
    int id = task.getId();
    tasks.put(id, task);
  }

  public void calculateAndSetEpicStatus(Epic epic) {
    int newStatus = 0;
    int doneStatus = 0;
    ArrayList<Integer> ids = epic.getSubtasksIds();
    if (ids.size() == 0)
      epic.setStatus(TaskStatus.NEW);

    for (int id : ids) {
      SubTask subTask = this.subTasks.get(id);
      switch (subTask.getStatus()) {
        case NEW:
          newStatus++;
          break;
        case DONE:
          doneStatus++;
          break;
        default:
          epic.setStatus(TaskStatus.IN_PROGRESS);
          return;
      }
    }

    if (doneStatus == ids.size()) {
      epic.setStatus(TaskStatus.DONE);
      return;
    }
    if (newStatus == ids.size()) {
      epic.setStatus(TaskStatus.NEW);
      return;
    }

  }
}
