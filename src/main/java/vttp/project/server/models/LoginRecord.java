package vttp.project.server.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class LoginRecord {

  private String user_id;
  private Integer login_count;
  private Date last_login;

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public Integer getLogin_count() {
    return login_count;
  }

  public void setLogin_count(Integer login_count) {
    this.login_count = login_count;
  }

  public Date getLast_login() {
    return last_login;
  }

  public void setLast_login(Date last_login) {
    this.last_login = last_login;
  }

  public void now() {
    this.setLast_login(new Date());
  }

  public static LoginRecord toLoginRecord(SqlRowSet rs) {

    LoginRecord record = new LoginRecord();
    record.setUser_id(rs.getString("user_id"));
    record.setLogin_count(rs.getInt("login_count"));
    record.setLast_login(rs.getDate("last_login"));

    return record;
  }

  public JsonObject toJSON() {
    return Json.createObjectBuilder()
        .add("user_id", user_id)
        .add("login_count", login_count)
        .add("last_login", last_login.toString())
        .build();
  }

}
