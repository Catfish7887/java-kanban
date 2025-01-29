package test.managers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.JavaKanban.TaskManager.TaskManager;
import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.Task;
import ru.JavaKanban.Tasks.TaskStatus;
import ru.JavaKanban.utils.Managers;

public class InMemoryTaskManagerTest {

  private TaskManager taskManager;
  private String name = "name";
  private String desc = "desc";

  @BeforeEach
  void init() {
    this.taskManager = Managers.getDefault();
  }

  @Test
  // Проверка добавления новой задачи
  void testAddNewTask() {
    Task task = new Task(this.name, this.desc);
    taskManager.addNewTask(task);
    assertNotNull(task.getId());
    assertEquals(task.getStatus(), TaskStatus.NEW);
    assertEquals(task.getName(), this.name);
    assertEquals(task.getDescription(), this.desc);

  }

  @Test
  // Менеджер должен добавлять разные типы задач и находить их по ID
  public void testManagerCanAddDifferentTasksAndFindById() {
    Epic epic = new Epic(name, desc);
    Task task = new Task(name, desc);
    taskManager.addNewEpic(epic);
    taskManager.addNewTask(task);
    int id = epic.getId();
    SubTask subtask = new SubTask(name, desc, id);
    taskManager.addNewSubTask(subtask);
    assertFalse(taskManager.getAllEpics().isEmpty());
    assertFalse(taskManager.getAllTasks().isEmpty());
    assertFalse(taskManager.getAllSubTasks().isEmpty());
    assertEquals(taskManager.getEpic(id), epic);
  }

  @Test
  // Задачи не должны конфликтовать внутри менеджера
  void testTasksDoesntConflictWhenSameId() {
    Task task1 = new Task("name", "desc");
    taskManager.addNewTask(task1);
    Task task2 = new Task("asa", "as", task1.getId(), TaskStatus.NEW);
    taskManager.addNewTask(task2);
    assertNotEquals(task1.getId(), task2.getId());
  }

  @Test
  // После добавления задачи, она не должна меняться
  void testTaskDoesntChangingAfterAdding() {
    Task task = new Task("name", "desc", 0, TaskStatus.NEW);
    taskManager.addNewTask(task);
    assertEquals(task, taskManager.getTask(task.getId()));
  }

  @Test
  // Эпик должен вычислять статус при обновлении задачи
  void testEpicChangingStatus() {
    Epic epic = new Epic(name, desc);
    taskManager.addNewEpic(epic);
    SubTask sub = new SubTask(name, desc, epic.getId());
    taskManager.addNewSubTask(sub);
    SubTask updSubTask = new SubTask(name, desc, sub.getId(), epic.getId(), TaskStatus.DONE);
    taskManager.updateSubTask(updSubTask);
    assertEquals(taskManager.getAllEpics().get(epic.getId()).getStatus(), TaskStatus.DONE);
  }
}
