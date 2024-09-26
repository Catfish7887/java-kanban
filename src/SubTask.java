public class SubTask extends Task {
  
  private int epicId;

  public SubTask(String name, String description, int id, int epicId){
    super(name, description, id);
    this.epicId = epicId;
  }

  @Override
    public String toString() {
      return "Task{" +
        " id='" + super.id + "'" +
        ", name='" + super.name + "'" +
        ", description='" + super.description + "'" +
        ", status='" + super.status + "'" +
        "}";
    }
}
