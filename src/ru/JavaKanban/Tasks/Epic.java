package ru.JavaKanban.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
  private ArrayList<Integer> subTaskIds;
  private LocalDateTime endTime;

  public Epic(String name, String descriprion) {
    super(name, descriprion);
    subTaskIds = new ArrayList<>();
  }

  public void setEndTime(LocalDateTime endTime){
    this.endTime = endTime;
  }

  @Override
  public LocalDateTime getEndTime() {
    return this.endTime;

  }

  public Epic(String name, String description, int id) {
    super(name, description);
    this.id = id;
    subTaskIds = new ArrayList<>();
  }

  @Override
  public Epic getCopy() {
    Epic epic = new Epic(name, description, id);
    epic.status = this.status;
    epic.subTaskIds = new ArrayList<>(this.subTaskIds);
    return epic;
  }

  public LocalDateTime getStartTime(){
    return this.startTime;
  }

  public void setStartTime(LocalDateTime stime){
    this.startTime = stime;
  }

  public void setDuration(Duration duration){
    this.setDuration(duration);
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public void addSubTaskId(int newSubtaskId) {
    this.subTaskIds.add(newSubtaskId);
  }

  public ArrayList<Integer> getSubtasksIds() {
    return new ArrayList<Integer>(subTaskIds);
  }

  public void clearSubIds() {
    subTaskIds.clear();
  }

  public void removeSubIdByValue(int v) {
    this.subTaskIds.remove(Integer.valueOf(v));
  }

  @Override
  public TaskType getType() {
    return TaskType.EPIC;
  }

  @Override
  public String toString() {
    return String.format("%d,%s,%s,%s,%s,",
        this.id,
        TaskType.EPIC,
        this.name,
        this.status,
        this.description);
  }

}
