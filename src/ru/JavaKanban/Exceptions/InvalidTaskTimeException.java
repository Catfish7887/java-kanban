package ru.JavaKanban.Exceptions;

public class InvalidTaskTimeException extends RuntimeException {
  public InvalidTaskTimeException(String message) {
    super(message);
  }

  public InvalidTaskTimeException(String message, Throwable e) {
    super(message, e);
  }
}
