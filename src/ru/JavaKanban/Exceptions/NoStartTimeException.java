package ru.JavaKanban.Exceptions;

public class NoStartTimeException extends RuntimeException {
  public NoStartTimeException(String message) {
    super(message);
  }

  public NoStartTimeException(String message, Throwable e) {
    super(message, e);
  }

}
