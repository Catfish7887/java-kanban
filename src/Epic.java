import java.util.ArrayList;
public class Epic extends Task{
  private ArrayList<Integer> subTaskIds;

  public Epic(String name, String descriprion, int id){
    super(name, descriprion, id);
    subTaskIds = new ArrayList<>();
  }

  public ArrayList<Integer> getIds(){
    return subTaskIds;
  }
}
