package ru.JavaKanban.utils;
import ru.JavaKanban.HistoryManager.InMemoryHistoryManager;
import ru.JavaKanban.TaskManager.InMemoryTaskManager;


public class Managers {
  public static InMemoryTaskManager getDefault() {
    return new InMemoryTaskManager(new InMemoryHistoryManager());
  }
  public static InMemoryHistoryManager getHistoryManager(){
    return new InMemoryHistoryManager();
    
  } 
}
