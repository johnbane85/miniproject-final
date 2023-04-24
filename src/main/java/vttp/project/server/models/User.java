package vttp.project.server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class User {

  private String user_id;
  private String username;
  private String password;
  private String email;

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public static User toUser(SqlRowSet rs) {

    User user = new User();
    user.setUser_id(rs.getString("user_id"));
    user.setUsername(rs.getString("username"));
    user.setEmail(rs.getString("email"));
    user.setPassword(rs.getString("password"));

    return user;
  }

  public JsonObject toJSON() {
    return Json.createObjectBuilder()
        .add("user_id", user_id)
        .add("username", username)
        .add("password", password)
        .add("email", email)
        .build();
  }

}
