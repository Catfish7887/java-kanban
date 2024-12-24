package test.tasks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.TaskStatus;

public class SubtaskTest {
  @Test
  // Наследники класса Task должны быть равны если равен их ID
  public void shouldBeEqualsById(){
    SubTask sub1 = new SubTask("name", "desc", 0, 1, TaskStatus.NEW);
    SubTask sub2 = new SubTask("12222", "dsc", 0, 1, TaskStatus.NEW);
    assertEquals(sub1, sub2);
  }
}
