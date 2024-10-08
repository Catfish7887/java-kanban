import ru.JavaKanban.TaskManager.TaskManager;
import ru.JavaKanban.Tasks.*;

class Main {
  public static void main(String[] args) {

    TaskManager manager = new TaskManager();

    Task task1 = new Task("task1", "1234");
    Task task2 = new Task("task2", "1234");
    manager.addNewTask(task1);
    manager.addNewTask(task2);
    System.out.println();
    Epic epic1 = new Epic("Epic1", "123");
    Epic epic2 = new Epic(null, null);
    manager.addNewEpic(epic1);
    manager.addNewEpic(epic2);
    System.out.println();
    int epic1Id = epic1.getId();
    int epic2Id = epic2.getId();
    SubTask subtask1 = new SubTask("sub1", "12123", epic1Id);
    SubTask subTask2 = new SubTask("sub2", "212", epic1Id);
    SubTask subTask3 = new SubTask("task", null, epic2Id);
    manager.addNewSubTask(subtask1);
    manager.addNewSubTask(subTask2);
    manager.addNewSubTask(subTask3);
    System.out.println();
    System.out.println(manager.getAllEpics());
    System.out.println(manager.getAllTasks());
    System.out.println(manager.getAllSubTasks());
    int sub1Id = subtask1.getId();
    int sub3Id = subTask3.getId();

    // Имитация обновлённой задачи, прилетевшей с фронтенда
    SubTask updatedTask = new SubTask("NEW", "NEW", sub1Id, epic1Id, TaskStatus.IN_PROGRESS);
    manager.updateSubTask(updatedTask);
    System.out.println();
    SubTask updatedTask2 = new SubTask("ok", "2222222", sub3Id, epic2Id, TaskStatus.DONE);
    manager.updateSubTask(updatedTask2);

    System.out.println("Проверка обновления статуса эпика");
    System.out.println(manager.getEpic(epic1Id));
    System.out.println(manager.getEpic(epic2Id));
    System.out.println();
    manager.removeSubTaskById(sub3Id); // Удалю сабтаск со статусом DONE
    manager.addNewSubTask(subTask3); // Шаблон таска тот же, что и удалённого, создастся таск со статусом NEW
    System.out.println("Статус эпика обновился при добавлении новой подзадачи");
    System.out.println(manager.getEpic(epic2Id));
    System.out.println();
    System.out.println(manager.getAllSubTasks());//Вывожу список всех подзадач
    manager.removeEpicById(epic1Id);// Вместе с эпиком должны удалиться все его задачи
    System.out.println();
    System.out.println("Актуальные подзадачи:");
    System.out.println(manager.getAllSubTasks()); // Задачи удалились вместе с эпиком
  }
}
