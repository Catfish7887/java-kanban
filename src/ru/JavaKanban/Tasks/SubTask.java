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

  @Override
  public SubTask getCopy() {
    return new SubTask(name, description, id, epicId, status);
  }

  public int getEpicId() {
    return this.epicId;
  }

  @Override
  public String toString() {
    return String.format("%d,%s,%s,%s,%s,%d",
        this.id,
        TaskType.SUBTASK,
        this.name,
        this.status,
        this.description,
        this.epicId);
  }
}
