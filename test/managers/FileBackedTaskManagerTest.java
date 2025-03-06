package test.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.FileBackedTaskManager;
import ru.JavaKanban.Tasks.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class FileBackedTaskManagerTest {
  File file;

  @BeforeEach
  public void init() throws IOException {
    file = File.createTempFile("test", ".csv");
    file.deleteOnExit();

  }

  @Test
  public void managerCanLoadAndSaveEmptyFileTest() throws IOException {
    // При запуске теста создаётся новый временный файл. Он будет пустой.
    Task task = new Task("name", "desc", 0, TaskStatus.NEW);
    FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(file);
    manager.addNewTask(task);

    // Сохранил задачу в файл и прочитал файл ещё раз, создав из этого файла ещё
    // один менеджер.
    FileBackedTaskManager anotherManager = FileBackedTaskManager.loadFromFile(file);
    assertEquals(task, anotherManager.getAllTasks().get(0));

  }

  @Test
  public void managerCanSaveMultiplyTasksTest() throws IOException {
    Epic epic = new Epic("null", "sss");
    Task task = new Task("ss", "name");
    SubTask sub = new SubTask("s", "lol", epic.getId());

    FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(file);
    manager.addNewEpic(epic);
    manager.addNewTask(task);
    manager.addNewSubTask(sub);

    assertFalse(manager.getAllEpics().isEmpty());
    assertFalse(manager.getAllSubTasks().isEmpty());
    assertFalse(manager.getAllTasks().isEmpty());
  }

  @Test
  public void managerCanLoadMultiplyTasksTest() throws IOException {

    FileBackedTaskManager manager = new FileBackedTaskManager(new InMemoryHistoryManager(), file);
    manager.addNewTask(new Task("name", "name"));
    manager.addNewTask(new Task("name", "name"));
    manager.addNewTask(new Task("name", "name"));
    manager.addNewTask(new Task("name", "name"));

    FileBackedTaskManager anotherManager = FileBackedTaskManager.loadFromFile(file);

    assertEquals(anotherManager.getAllTasks().size(), 4);
  }

}
