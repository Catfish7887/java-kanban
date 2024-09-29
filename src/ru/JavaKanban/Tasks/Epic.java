package ru.JavaKanban.Tasks;
import java.util.ArrayList;
public class Epic extends Task{
  private ArrayList<Integer> subTaskIds;

  public Epic(String name, String descriprion){
    super(name, descriprion);
    subTaskIds = new ArrayList<>();
  }
  // Конструктор для 
  public Epic(String name, String description, int id, TaskStatus status){
    super(name, description, id, status);
    subTaskIds = new ArrayList<>();
  }

  public void addSubTaskId(int newSubtaskId) {
    this.subTaskIds.add(newSubtaskId);
  }

  public ArrayList<Integer> getSubtasksIds(){
    return this.subTaskIds;
  }

  // При очищении списка подзадач, нужно удалить их у эпиков. 
  // Не добавляю очистку ID эпиков у класса подзадач, так как подзадача относится к эпику, и при его удалении, существование подзадачи не имеет смысла
  public void clearSubIds(){
    subTaskIds.clear();
  }

  @Override
  public String toString() {
    return "Epic{" +
        " id='" + this.id + "'" +
        ", name='" + this.name + "'" +
        ", description='" + this.description + "'" +
        ", subTaskIds='" + this.subTaskIds.toString() + "'" +
        ", status='" + this.status + "'" +
        "}";
  }
  
}
