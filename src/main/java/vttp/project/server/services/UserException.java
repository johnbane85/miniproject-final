package vttp.project.server.services;

public class UserException extends Exception {
  public UserException() {
    super();
  }

  public UserException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
