import java.util.Objects;
public class Task {
  protected int id;
  protected String name;
  protected String description;
  protected TaskStatus status;

  
  public Task(String name, String description, int id) {
    this.name = name;
    this.description = description;
    this.status = TaskStatus.NEW;
    this.id = id;
  }
  
  // public void setId(int id) {
    //   this.id = id;
    // }
    @Override
    public String toString() {
      return "Task{" +
        " id='" + this.id + "'" +
        ", name='" + this.name + "'" +
        ", description='" + this.description + "'" +
        ", status='" + this.status + "'" +
        "}";
    }
    
  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Task)) {
      return false;
    }
    Task task = (Task) o;
    return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
      && Objects.equals(status, task.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, status);
  }
}
