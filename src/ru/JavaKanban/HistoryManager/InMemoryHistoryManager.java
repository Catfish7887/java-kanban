package ru.JavaKanban.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.platform.engine.support.hierarchical.Node;

import ru.JavaKanban.Tasks.Task;

public class InMemoryHistoryManager implements HistoryManager {
  private Node<Task> head;
  private Node<Task> tail;

  private final Map<Integer, Node<Task>> idToNode;

  public InMemoryHistoryManager() {
    idToNode = new HashMap<>();
  }

  // public void addToHistory(Task task) {
  // if (history.size() == 10) {
  // history.remove(0);
  // }
  // history.add(task);
  // }

  @Override
  public void addToHistory(Task taskToAdd) {
    Node<Task> newNode = new Node<Task>(head, tail, taskToAdd);

    if (head == null) {
      idToNode.put(taskToAdd.getId(), newNode);
      this.head = newNode;
      return;
    }
    if (head != null && tail == null) {
      newNode.setPrev(this.head);
      this.head.setNext(newNode);
      idToNode.put(taskToAdd.getId(), newNode);
      this.tail = newNode;
      return;
    }
    if (this.head.data.getId() == taskToAdd.getId()) {
      Node<Task> newHeadLink = this.head.next;
      newHeadLink.prev = null;
      this.head = newHeadLink;
      linkLast(newNode, 0);
      return;
    }
    linkLast(newNode, taskToAdd.getId());

  }

  private void linkLast(Node<Task> node, int id) {
    this.tail.setNext(node);
    node.setPrev(this.tail);
    this.tail = node;
    idToNode.put(id, node);
  }

  @Override
  public ArrayList<Task> getHistory() {
    ArrayList<Task> result = new ArrayList<>();
    
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

    public void setNext(Node<T> newNext) {
      this.next = newNext;
    }

    public void setPrev(Node<T> newPrev) {
      this.prev = newPrev;
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
