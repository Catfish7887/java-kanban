package test.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ru.JavaKanban.Tasks.Epic;

public class EpicTest {

  @Test
  void shouldBeEqualsById() {
    Epic epic1 = new Epic("name", "name", 0);
    Epic epic2 = new Epic("n12e", "name", 0);
    assertEquals(epic1, epic2);
  }
}
