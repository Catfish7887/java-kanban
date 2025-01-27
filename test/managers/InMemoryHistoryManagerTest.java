package test.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

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
  // Менеджер должен сохранять предидущие версии задачи
  void testHistoryManagerCanStorePrevVersionOfTask() {
    ArrayList<Task> array = new ArrayList<>();
    Task task1 = new Task("name1", "desc", 0, TaskStatus.NEW);
    historyManager.addToHistory(task1);
    array.add(task1);
    Task task2 = new Task("name2", "desc", 0, TaskStatus.NEW);
    array.add(task2);
    historyManager.addToHistory(task2);

    assertNotEquals(historyManager.getHistory().get(0).toString(), historyManager.getHistory().get(1).toString());

  }

  @Test
  // Менеджер истории добавляет все объекты типа Task и его наследников
  void managerShouldAddAnyTypeOfTasks(){
    Epic epic = new Epic("name", "desc");
    Task task = new Task("name", "desc");
    historyManager.addToHistory(task);
    historyManager.addToHistory(epic);
    assertEquals(historyManager.getHistory().get(0).getClass(), Task.class);
    assertEquals(historyManager.getHistory().get(1).getClass(), Epic.class);
  }

  @Test
  // Проверка вместимости менеджера истории, должно быть не более 10 задач
  void testOnlyTenTasks() {
    for (int i = 1; i <= 3; i++) {
      Task task = new Task("name", "desc", i, TaskStatus.DONE);
      historyManager.addToHistory(task);
    }

    // При создании задачи в цикле, номер итерации присваивается ID задачи. 
    // Цикл повторяется 11 раз, на 10 итерации заканчивается место в массиве, и удаляется первый элемент - задача с ID 1.
    // На последней итерации первой в списке становится задача с ID 2, последней в списке будет задача с ID 11.
    assertEquals(3,historyManager.getHistory().get(2).getId());
    historyManager.addToHistory(new Task("1", "2", 4, null));
    historyManager.addToHistory(new Task("1", "2", 1, null));
     
    assertEquals(1,historyManager.getHistory().get(3).getId());

  }
}
