package ru.JavaKanban.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.JavaKanban.Tasks.Task;

public class InMemoryHistoryManager implements HistoryManager {
  private Node<Task> head;
  private Node<Task> tail;

  private final Map<Integer, Node<Task>> idToNode;

  public InMemoryHistoryManager() {
    idToNode = new HashMap<>();
  }

  @Override
  public void addToHistory(Task taskToAdd) {
    Node<Task> newNode = new Node<Task>(null, null, taskToAdd);

    // Если список задач пустой
    if (head == null) {
      idToNode.put(taskToAdd.getId(), newNode);
      this.head = newNode;
      return;
    }

    // Если у нас одна задача в списке
    if (head != null && tail == null) {
      newNode.prev = this.head;
      this.head.next = newNode;
      idToNode.put(taskToAdd.getId(), newNode);
      this.tail = newNode;
      return;
    }

    // Если задача стоит первой в списке, и мы повторно её просматриваем
    if (this.head.data.getId() == taskToAdd.getId()) {
      unlinkFirst();
      linkLastAndAddToHistory(newNode, 0);
      return;
    }

    // Стандартный сценарий добавления. Если задачи ещё не было в списке,
    // и есть начало и конец списка.
    linkLastAndAddToHistory(newNode, taskToAdd.getId());

  }

  @Override
  public void removeTask(int id) {

    // Если ID нет в списке просмотренных задач - ничего не делаем
    if (!idToNode.keySet().contains(id)) {
      return;
    }

    // Если удаляемый элемент стоит первым в списке
    if (this.head.data.getId() == id) {
      unlinkFirst();
      idToNode.remove(id);
      return;
    }

    Node<Task> nodeToDelete = idToNode.get(id);
    nodeToDelete.prev.next = nodeToDelete.next;
    nodeToDelete.next.prev = nodeToDelete.prev;
    idToNode.remove(id);
  }

  // Отвязать первую задачу, првязать следующую к голове списка
  private void unlinkFirst() {

    Node<Task> newHeadLink = this.head.next;
    newHeadLink.prev = null;
    this.head.next = null;

    this.head = newHeadLink;
  }

  private void linkLastAndAddToHistory(Node<Task> node, int id) {
    this.tail.next = node;
    node.prev = this.tail;
    node.next = null;
    this.tail = node;

    idToNode.put(id, node);
  }

  @Override
  // Проблему с доступом к данным задачи решил через копирование объекта.
  // Для этого написал метод getCopy() для каждого класса.
  public ArrayList<Task> getHistory() {
    ArrayList<Task> result = new ArrayList<>();
    Node<Task> next;

    result.add(this.head.data.getCopy());
    next = head.next;
    while (next != null) {
      result.add(next.data.getCopy());
      next = next.next;
    }

    return result;
  }

  // Создал класс Node внутри менеджера.
  private class Node<T> {
    private Node<T> next;
    private Node<T> prev;
    private T data;

    public Node(Node<T> next, Node<T> prev, T data) {
      this.next = next;
      this.prev = prev;
      this.data = data;
    }

    public T getData() {
      return data;
    }

    @Override
    public int hashCode() {
      return this.data.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o == this)
        return true;
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return this.data == node.getData();
    }
  }
}
