package test.managers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    System.out.println("INIT");
  }

  @Test
  // 
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

    assertEquals(taskManager.getEpic(id), epic.toString());
  }

  @Test
  // Задачи не конфликтуют внутри менеджера
  void tasksdoesntConflictWhenIdsSimillar(){
    Task task1 = new Task("name", "desc");
    taskManager.addNewTask(task1);
    Task task2 = new Task("asa", "as", task1.getId(), TaskStatus.NEW);
    taskManager.addNewTask(task2);

    assertNotEquals(task1.getId(), task2.getId());
  }

@Test
void taskDoesentChangingAfterAdding(){
  Task task = new Task("name", "desc", 0, TaskStatus.NEW);
  taskManager.addNewTask(task);
  
  assertEquals(task.toString(), taskManager.getTask(task.getId()));
  
}
  
}