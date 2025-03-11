package ru.JavaKanban.TaskManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ru.JavaKanban.Exceptions.InvalidTaskTimeException;
import ru.JavaKanban.Exceptions.NoStartTimeException;
import ru.JavaKanban.HistoryManager.HistoryManager;
import ru.JavaKanban.Tasks.*;

public class InMemoryTaskManager implements TaskManager {
  private HashMap<Integer, Task> tasks;
  private HashMap<Integer, SubTask> subTasks;
  private HashMap<Integer, Epic> epics;
  protected int newId = 0;
  private HistoryManager historyManager;
  private Set<Task> prioritizedTasks;

  public InMemoryTaskManager(HistoryManager historyManager) {
    this.tasks = new HashMap<>();
    this.subTasks = new HashMap<>();
    this.epics = new HashMap<>();
    this.prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    this.historyManager = historyManager;
  }

  protected HashMap<Integer, Task> getTaskHashMap() {
    return this.tasks;
  }

  protected HashMap<Integer, SubTask> getSubHashMap() {
    return this.subTasks;
  }

  protected HashMap<Integer, Epic> getEpicHashMap() {
    return this.epics;
  }

  private void validateNewTask(Task task) {
    if (task.getStartTime() == null) {
      throw new NoStartTimeException(
          "Время начала выполнения задачи не задано. Приоритет задачи не будет учитываться.");
    }

    if (hasOverlappingTasks(task)) {
      throw new InvalidTaskTimeException(
          "Время выполнения новой задачи пересекается с временем выполнения существующей задачи");
    }
  }

  public boolean hasOverlappingTasks(Task newTask) {
    return prioritizedTasks.stream()
        .filter(task -> task.getStartTime().isBefore(newTask.getEndTime()))
        .anyMatch(task -> task.getEndTime().isAfter(newTask.getStartTime()));
  }

  public Set<Task> getPrioritizedTasks() {
    return this.prioritizedTasks;
  }

  @Override
  public ArrayList<Task> getTaskHistory() {
    return historyManager.getHistory();
  }

  @Override
  public ArrayList<Task> getAllTasks() {
    return new ArrayList<>(tasks.values());
  }

  @Override
  public ArrayList<Epic> getAllEpics() {
    return new ArrayList<>(epics.values());
  }

  @Override
  public ArrayList<SubTask> getAllSubTasks() {
    return new ArrayList<>(subTasks.values());
  }

  @Override
  public void addNewTask(Task task) {
    try {
      validateNewTask(task);
    } catch (InvalidTaskTimeException e) {
      System.err.println(e.getMessage() + "\n" + task);
      return;

    } catch (NoStartTimeException e) {
      System.err.println(e.getMessage() + "\n" + task);
    }
    int taskId = this.newId++;
    task.setId(taskId);
    tasks.put(taskId, task);
  }

  @Override
  public void addNewEpic(Epic epic) {
    int epicId = this.newId++;
    epic.setId(epicId);
    epics.put(epicId, epic);
  }

  @Override
  public void addNewSubTask(SubTask subTask) {
    try {
      validateNewTask(subTask);
    } catch (InvalidTaskTimeException e) {
      System.err.println(e.getMessage() + "\n" + subTask);
      return;
    } catch (NoStartTimeException e) {
      System.err.println(e.getMessage() + "\n" + subTask);
    }

    int taskId = this.newId++;
    subTask.setId(taskId);
    int epicId = subTask.getEpicId();
    Epic epic = epics.get(epicId);
    epic.addSubTaskId(taskId);
    this.subTasks.put(taskId, subTask);
    generateNewAndUpdateEpic(epic);
  }

  @Override
  public Task getTask(int id) {
    Task task = tasks.get(id);
    if (task != null) {
      historyManager.addToHistory(task);
    }
    return task;
  }

  @Override
  public SubTask getSubTask(int id) {
    SubTask subtask = subTasks.get(id);
    if (subtask != null) {
      historyManager.addToHistory(subtask);
    }
    return subtask;
  }

  @Override
  public Epic getEpic(int id) {
    Epic epic = epics.get(id);
    if (epic != null) {
      historyManager.addToHistory(epic);
    }
    return epic;
  }

  @Override
  public List<SubTask> getEpicSubTasks(int id) {
    Epic epic = epics.get(id);
    List<SubTask> result = subTasks.values().stream().filter(sub -> sub.getEpicId() == epic.getId())
        .collect(Collectors.toList());
    // ArrayList<SubTask> result = new ArrayList<>();
    // for (int subTaskId : epic.getSubtasksIds()) {
    // result.add(subTasks.get(subTaskId));
    // }
    return result;
  }

  @Override
  public void removeSubTaskById(int id) {
    SubTask task = subTasks.get(id);
    int epicId = task.getEpicId();
    Epic epic = epics.get(epicId);
    epic.removeSubIdByValue(id);
    subTasks.remove(id);
    historyManager.removeTask(id);
    generateNewAndUpdateEpic(epic);

  }

  @Override
  public void removeEpicById(int epicId) {
    Epic epic = epics.get(epicId);
    for (int subId : epic.getSubtasksIds()) {
      subTasks.remove(subId);
      historyManager.removeTask(subId);
    }
    epics.remove(epicId);
    historyManager.removeTask(epicId);

  }

  @Override
  public void removeTaskById(int id) {
    prioritizedTasks.remove(tasks.get(id));
    tasks.remove(id);
    historyManager.removeTask(id);
  }

  @Override
  public void clearAllEpics() {
    epics.clear();
    subTasks.clear();
  }

  @Override
  public void clearAllTasks() {
    tasks.clear();
  }

  @Override
  public void clearAllSubTasks() {
    subTasks.clear();
    for (Epic epic : epics.values()) {
      epic.clearSubIds();
      calculateAndSetEpicData(epic);
    }
  }

  @Override
  public void updateSubTask(SubTask subTask) {
    try {
      validateNewTask(subTask);
    } catch (InvalidTaskTimeException e) {
      System.err.println(e.getMessage() + "\n" + subTask);
      return;
    } catch (NoStartTimeException e) {
      System.err.println(e.getMessage() + "\n" + subTask);
    }
    int id = subTask.getId();
    subTasks.put(id, subTask);
    System.out.println(subTasks);
    Epic epic = epics.get(subTask.getEpicId());
    generateNewAndUpdateEpic(epic);
  }

  @Override
  public void updateEpic(Epic epic) {
    int id = epic.getId();
    epics.put(id, epic);
  }

  private void generateNewAndUpdateEpic(Epic epic) {
    Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId());
    ArrayList<Integer> subIds = epic.getSubtasksIds();
    for (Integer subId : subIds) {
      newEpic.addSubTaskId(subId);
    }
    calculateAndSetEpicData(newEpic);
    updateEpic(newEpic);
  }

  @Override
  public void updateTask(Task task) {
    try {
      validateNewTask(task);
    } catch (InvalidTaskTimeException e) {
      System.err.println(e.getMessage() + "\n" + task);
      return;
    } catch (NoStartTimeException e) {
      System.err.println(e.getMessage() + "\n" + task);
    }
    int id = task.getId();
    tasks.put(id, task);
  }

  protected void calculateAndSetEpicData(Epic epic) {
    int newStatus = 0;
    int doneStatus = 0;
    ArrayList<Integer> ids = epic.getSubtasksIds();
    Duration duration = Duration.ofMinutes(0);
    if (ids.size() == 0)
      epic.setStatus(TaskStatus.NEW);

    for (int i = 0; i < ids.size(); i++) {
      int id = ids.get(i);

      SubTask subTask = this.subTasks.get(id);
      duration = duration.plus(subTask.getDuration());

      // Если подзадача стоит первая в массиве, значит, она была добавлена самой
      // первой
      if (i == 0) {
        epic.setStartTime(subTask.getStartTime());
      }

      switch (subTask.getStatus()) {
        case NEW:
          newStatus++;
          break;
        case DONE:
          doneStatus++;
          break;
        default:
          epic.setStatus(TaskStatus.IN_PROGRESS);

      }
    }

    if (doneStatus == ids.size()) {
      epic.setStatus(TaskStatus.DONE);
    }
    if (newStatus == ids.size()) {
      epic.setStatus(TaskStatus.NEW);
    }

    epic.setDuration(duration);
    epic.setEndTime(epic.getStartTime().plus(duration));
  }
}
