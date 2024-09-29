package ru.JavaKanban.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;

import ru.JavaKanban.Tasks.*;

public class TaskManager {
  private HashMap<Integer, Task> tasks;
  private HashMap<Integer, SubTask> subTasks;
  private HashMap<Integer, Epic> epics;
  int newId = 0;

  public TaskManager() {
    tasks = new HashMap<>();
    subTasks = new HashMap<>();
    epics = new HashMap<>();
  }

  public ArrayList<Task> getAllTasks() {
    ArrayList<Task> result = new ArrayList<>();
    for (Task task : tasks.values()) {
      result.add(task);
    }
    return result;
  }

  public ArrayList<Epic> getAllEpics() {
    ArrayList<Epic> result = new ArrayList<>();
    for (Epic epic : epics.values()) {
      result.add(epic);
    }
    return result;
  }

  public ArrayList<SubTask> getAllSubTasks() {
    ArrayList<SubTask> result = new ArrayList<>();
    for (SubTask subTask : subTasks.values()) {
      result.add(subTask);
    }
    return result;
  }

  public void addNewTask(Task task) {
    int taskId = this.newId++;
    task.setId(taskId);
    tasks.put(taskId, task);
    System.out.println("Задача добавлена " + task);
  }

  public void addNewEpic(Epic epic) {
    int epicId = this.newId++;
    epic.setId(epicId);
    epics.put(epicId, epic);
    System.out.println("Эпик добавлен " + epic);
  }

  public void addNewSubTask(SubTask subTask) {
    int taskId = this.newId++;
    subTask.setId(taskId);
    int epicId = subTask.getEpicId();
    Epic epic = epics.get(epicId);
    epic.addSubTaskId(taskId);
    this.subTasks.put(taskId, subTask);
    System.out.println("Подзадача добавлена " + subTask);
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
    subTasks.remove(id);
    System.out.println("Удалена подзадача с id" + id);
  }

  public void removeEpicById(int epicId) {
    Epic epic = epics.get(epicId);
    for (int subId : epic.getSubtasksIds()) {
      subTasks.remove(subId);
      System.out.println("Удалён элемент с ID " + subId + ", который относился к удаляемому элементу");
    }
    epics.remove(epicId);
    System.out.println("Удален эпик с id" + epicId);

  }

  public void removeTaskById(int id) {
    tasks.remove(id);
    System.out.println("Удалена задача с id" + id);
  }

  public void clearAllEpics() {
    epics.clear();
    System.out.println("Удалены все эпики");
    // Очищаю и подзадачи, так как без эпиков их существование не имеет смысла
    subTasks.clear();
    System.out.println("Удалены все подзадачи");
  }

  public void clearAllTasks() {
    tasks.clear();
    System.out.println("Удалены все задачи");
  }

  public void clearAllSubTasks() {
    subTasks.clear();
    System.out.println("Удалены все подзадачи");
    for (Epic epic : epics.values()) {
      epic.clearSubIds();
      System.out.println("очищены ID подзадач у эпика " + epic);
    }
  }

  public void updateSubTask(SubTask subTask) {
    int id = subTask.getId();
    subTasks.put(id, subTask);
    System.out.println("Элемент обновлён успешно " + subTask);
    System.out.println(subTasks);
    Epic epic = epics.get(subTask.getEpicId());
    Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId(), calculateEpicStatus(epic));
    updateEpic(newEpic);
  }

  public void updateEpic(Epic epic) {
    int id = epic.getId();
    epics.put(id, epic);
    System.out.println("Элемент обновлён успешно " + epic);
  }

  public void updateTask(Task task) {
    int id = task.getId();
    tasks.put(id, task);
    System.out.println("Элемент обновлён успешно " + task);
  }

  public TaskStatus calculateEpicStatus(Epic epic) {
    int newStatus = 0;
    int doneStatus = 0;
    ArrayList<Integer> ids = epic.getSubtasksIds();

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
          continue;

      }
    }

    if (doneStatus == ids.size())
      return TaskStatus.DONE;
    if (newStatus == ids.size())
      return TaskStatus.NEW;
    return TaskStatus.IN_PROGRESS;

  }
}
