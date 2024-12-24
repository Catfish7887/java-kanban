package ru.JavaKanban.HistoryManager;

import java.util.ArrayList;

import ru.JavaKanban.Tasks.Task;

public class InMemoryHistoryManager implements HistoryManager{
  private ArrayList<Task> history;
  
  public InMemoryHistoryManager(){
    history = new ArrayList<>();
  }

  @Override
  public void addToHistory(Task task){
    if (history.size() == 10) {
     history.remove(0); 
    }
    history.add(task);
  }

  @Override
  public ArrayList<Task> getHistory(){
    return this.history;
  }
}
