package ru.JavaKanban.Tasks;

public class SubTask extends Task {

  private int epicId;

  public SubTask(String name, String description, int epicId) {
    super(name, description);
    this.epicId = epicId;
  }

  public SubTask(String name, String description, int id, int epicId, TaskStatus status) {
    super(name, description, id, status);
    this.epicId = epicId;
  }

  public int getEpicId() {
    return this.epicId;
  }

  @Override
  public String toString() {
    return "SubTask{" +
        " id='" + this.id + "'" +
        ", name='" + this.name + "'" +
        ", description='" + this.description + "'" +
        ", epicId='" + this.epicId + "'" +
        ", status='" + this.status + "'" +
        "}";
  }
}
