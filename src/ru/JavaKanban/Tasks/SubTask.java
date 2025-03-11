package ru.JavaKanban.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

  private int epicId;

  public SubTask(String name, String description, int epicId, long duration, LocalDateTime startTime) {
    super(name, description, duration, startTime);
    this.epicId = epicId;
  }

  public SubTask(String name, String description, int id, int epicId, TaskStatus status, long duration, LocalDateTime startTime) {
    super(name, description, id, status, duration, startTime);
    this.epicId = epicId;
  }

  public LocalDateTime getStartTime(){
    return this.startTime;
  }

  public Duration getDuration(){
    return this.duration;
  }

  @Override
  public TaskType getType() {
    return TaskType.SUBTASK;
  }

  @Override
  public SubTask getCopy() {
    return new SubTask(name, description, id, epicId, status, duration.toMinutes(), startTime);
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
