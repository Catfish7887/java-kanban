import ru.JavaKanban.TaskManager.TaskManager;
import ru.JavaKanban.Tasks.*;

class Main {
  public static void main(String[] args) {


    //TODO Написать код тестов к ТЗ
    TaskManager manager = new TaskManager();

    // Task task1 = new Task("task1", "1234");
    // Task task2 = new Task("task2", "1234");
    // manager.addNewTask(task1);
    // manager.addNewTask(task2);

    Epic epic1 = new Epic("Epic1", "123");
    manager.addNewEpic(epic1);
    int epic1Id = epic1.getId();
    SubTask subtask1 = new SubTask("sub1", "12123", epic1Id);
    SubTask subTask2 = new SubTask("sub2", "212", epic1Id);
    manager.addNewSubTask(subtask1);
    manager.addNewSubTask(subTask2);
    // System.out.println();
    // System.out.println(manager.getAllEpics());
    // System.out.println(manager.getAllTasks());
    System.out.println(manager.getAllSubTasks());
    int sub1Id = subtask1.getId();
    // Имитация обновлённой задачи, прилетевшей с фронтенда
    SubTask updatedTask = new SubTask("NEW", "NEW", sub1Id, epic1Id, TaskStatus.IN_PROGRESS);
    manager.updateSubTask(updatedTask);
    System.out.println();
    System.out.println("Проверка обновления статуса эпика");
    System.out.println(manager.getEpic(epic1Id));

  }
}
