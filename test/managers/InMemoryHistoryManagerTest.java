package test.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.Task;
import ru.JavaKanban.Tasks.TaskStatus;
import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;

public class InMemoryHistoryManagerTest {
  private InMemoryHistoryManager historyManager;

  @BeforeEach
  void init() {
    historyManager = new InMemoryHistoryManager();
  }

  @Test
  // Менеджер истории добавляет все объекты типа Task и его наследников
  void managerShouldAddAnyTypeOfTasks() {
    Epic epic = new Epic("name", "desc");
    Task task = new Task("name", "desc");
    historyManager.addToHistory(task);
    historyManager.addToHistory(epic);
    assertEquals(historyManager.getHistory().get(0).getClass(), Task.class);
    assertEquals(historyManager.getHistory().get(1).getClass(), Epic.class);

  }

  @Test
  // Менеджер должен сохранять данные эпика
  void testManagerCanCopyEpicData() {
    Epic epic = new Epic(null, null, 0);
    epic.addSubTaskId(4);
    epic.addSubTaskId(5);

    historyManager.addToHistory(epic);
    assertEquals(epic.toString(), historyManager.getHistory().get(0).toString());

  }

  @Test
  // Проверка на удаление старой версии задачи при добавлении задачи с тем же ID,
  // но другими полями
  void testUpdateTaskVersion() {
    Task task = new Task("name", "desc", 5, TaskStatus.DONE);
    historyManager.addToHistory(task);
    historyManager.addToHistory(new Task("1", "2", 0, null));
    String newTaskName = "NewNAME";
    historyManager.addToHistory(new Task(newTaskName, null, 5, null));

    assertEquals(historyManager.getHistory().get(1).getName(), newTaskName);
  }

  @Test
  // Проверка на добавление в менеджер историй больше 10 задач
  // Менеджер сохраняет очередь добавления задач
  void testManagerCanAddManyTasksAndSaveOrder() {
    for (int i = 1; i <= 12; i++) {
      historyManager.addToHistory(new Task("" + i, null, i, null));
    }

    assertEquals(12, historyManager.getHistory().size());
    assertEquals(12, historyManager.getHistory().get(11).getId());
  }

  @Test
  // Менеджер не допускает изменения полей у объектов в истории
  void testManagerSecureNodeData() {
    historyManager.addToHistory(new Task("name1", "ssss", 0, null));
    Task dataToEdit = historyManager.getHistory().get(0);
    int newId = 5;
    dataToEdit.setId(newId);

    assertNotEquals(newId, historyManager.getHistory().get(0).getId());
  }

}