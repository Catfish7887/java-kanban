package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.JavaKanban.TaskManager.InMemoryTaskManager;
import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.TaskStatus;
import ru.JavaKanban.utils.Managers;

public class InMemoryTaskManagerTest {
  private InMemoryTaskManager taskManager;
  
  @BeforeEach
  void init(){
    this.taskManager = Managers.getDefault();
  }
  
  @Test
  void testAddNewEpic() {
    Epic epic = new Epic("name", "desc");
    taskManager.addNewEpic(epic);
    assertNotNull(epic.getId());
    assertEquals(epic.getStatus(), TaskStatus.NEW);
    assertEquals(epic.getName(), "name");
    assertEquals(epic.getDescription(), "");
  }

  @Test
  void testAddNewSubTask() {

  }

  @Test
  void testAddNewTask() {

  }

  @Test
  void testClearAllEpics() {

  }

  @Test
  void testClearAllSubTasks() {

  }

  @Test
  void testClearAllTasks() {

  }

  @Test
  void testGetAllEpics() {

  }

  @Test
  void testGetAllSubTasks() {

  }

  @Test
  void testGetAllTasks() {

  }

  @Test
  void testGetEpic() {

  }

  @Test
  void testGetEpicSubTasks() {

  }

  @Test
  void testGetSubTask() {

  }

  @Test
  void testGetTask() {

  }

  @Test
  void testRemoveEpicById() {

  }

  @Test
  void testRemoveSubTaskById() {

  }

  @Test
  void testRemoveTaskById() {

  }

  @Test
  void testUpdateEpic() {

  }

  @Test
  void testUpdateSubTask() {

  }

  @Test
  void testUpdateTask() {

  }
}
