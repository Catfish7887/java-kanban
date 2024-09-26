import java.util.HashMap;

public class TaskManager {
  private HashMap<Integer, Task> tasks;
  private HashMap<Integer, SubTask> subTasks;
  private HashMap<Integer, Epic> epics;
  int id = 0;


  public TaskManager(){
    tasks = new HashMap<>();
    subTasks = new HashMap<>();
    epics = new HashMap<>();
  }

  public void addNewTask(Task task){
    int taskId = id++;
    tasks.put(taskId, task);
    System.out.println("Задача добавлена "+task);
  }

  public void addNewSubTask(){
  }

}
