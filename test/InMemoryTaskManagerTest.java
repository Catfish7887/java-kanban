package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.StructuredTaskScope.Subtask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.InMemoryTaskManager;
import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.Task;
import ru.JavaKanban.Tasks.TaskStatus;
import ru.JavaKanban.utils.Managers;

public class InMemoryTaskManagerTest {
  private InMemoryTaskManager taskManager;
  private String name = "name";
  private String desc = "desc";

  @BeforeEach
  void init() {
    this.taskManager = Managers.getDefault();

  }

  @Test
  void testAddNewEpic() {

    Epic epic = new Epic("name", "desc");
    taskManager.addNewEpic(epic);
    assertNotNull(epic.getId());
    assertEquals(epic.getStatus(), TaskStatus.NEW);
    assertEquals(epic.getName(), this.name);
    assertEquals(epic.getDescription(), this.desc);
  }

  @Test
  // создание подзадачи, проверка на принадлежность к эпику
  void testAddNewSubTask() {
    Epic epic = new Epic("Epic", "description");
    taskManager.addNewEpic(epic);
    int id = epic.getId();
    SubTask sub = new SubTask(this.name, this.desc, id);

    assertEquals(sub.getName(), this.name);
    assertEquals(sub.getDescription(), this.desc);
    assertNotNull(sub.getId());
    assertEquals(epic.getId(), sub.getEpicId());

  }

  @Test
  void testAddNewTask() {

    Task task = new Task(this.name, this.desc);
    taskManager.addNewTask(task);
    assertNotNull(task.getId());
    assertEquals(task.getStatus(), TaskStatus.NEW);
    assertEquals(task.getName(), this.name);
    assertEquals(task.getDescription(), this.desc);

  }

  @Test
  void testGetTask() {
    Task task = new Task(this.name, this.desc);
    taskManager.addNewTask(task);
    int id = task.getId();
    String task2 = taskManager.getTask(id);
    assertEquals(task2, taskManager.getTaskHistory().get(0).toString());

  }

  @Test
  void testTasksEqualsEachOther() {
    Task task1 = new Task(name, desc);
    taskManager.addNewTask(task1);
    int id = task1.getId();
    assertEquals(id, task1.getId());
    assertEquals(task1, task1);
  }

  @Test
  void testTaskChildrenEqualsEachOther() {
    Epic epic = new Epic(name, desc);
    taskManager.addNewEpic(epic);
    int id = epic.getId();
    assertEquals(epic, epic);
    assertEquals(id, epic.getId());
  }

  @Test
  void testUtilClassReturnsReadyForWorkClasses() {
    assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    assertInstanceOf(InMemoryHistoryManager.class, Managers.getHistoryManager());
  }

  @Test
  void testManagerCanAddDifferentTasksAndFindById(){
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
    
    assertEquals(taskManager.getEpic(id), epic.toString());
  }
}
