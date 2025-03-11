package ru.JavaKanban.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

  protected int id;
  protected String name;
  protected String description;
  protected TaskStatus status;
  protected Duration duration;
  LocalDateTime startTime;
  // TODO Конструктор должен принимать дату в виде строки, а formatter должен переводить строку в LocalDateTime

  // Оставил конструктор без длительности
  public Task(String name, String description){
    this.description = description;
    this.name = name;
    this.status = TaskStatus.NEW;
  }

  public Task(String name, String description, long duration, LocalDateTime startTime) {
    this.description = description;
    this.name = name;
    this.status = TaskStatus.NEW;
    this.duration = Duration.ofMinutes(duration);
    this.startTime = startTime;
  }

  public Task(String name, String description, int id, TaskStatus status, long duration, LocalDateTime startTime) {
    this.description = description;
    this.name = name;
    this.status = status;
    this.id = id;
    this.duration = Duration.ofMinutes(duration);
    this.startTime = startTime;
  }

  public LocalDateTime getStartTime(){
    return this.startTime;
  }

  public LocalDateTime getEndTime(){
    return startTime.plus(duration);
  };

  public Task getCopy() {
    return new Task(name, description, id, status, duration.toMinutes(), startTime);
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public TaskStatus getStatus() {
    return this.status;
  }

  public TaskType getType() {
    return TaskType.TASK;
  }

  @Override
  public String toString() {
    return String.format("%d,%s,%s,%s,%s,",
        this.id,
        TaskType.TASK,
        this.name,
        this.status,
        this.description);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Task)) {
      return false;
    }
    Task task = (Task) o;
    return id == task.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, status);
  }
}
