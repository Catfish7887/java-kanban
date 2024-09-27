package ru.JavaKanban.Tasks;
import java.util.ArrayList;
public class Epic extends Task{
  private ArrayList<Integer> subTaskIds;

  public Epic(String name, String descriprion, int id){
    super(name, descriprion, id);
    subTaskIds = new ArrayList<>();
  }

  public void addSubTaskId(int newSubtaskId) {
      this.subTaskIds.add(newSubtaskId);
  }

  public ArrayList<Integer> getSubtasksIds(){
    return this.subTaskIds;
  }

  @Override
  public String toString() {
    return "Task{" +
        " id='" + this.id + "'" +
        ", name='" + this.name + "'" +
        ", description='" + this.description + "'" +
        ", subTaskIds='" + this.subTaskIds.toString() + "'" +
        ", status='" + this.status + "'" +
        "}";
  }
  
}
