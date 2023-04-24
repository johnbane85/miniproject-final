package vttp.project.server.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {

  private String comment_id;
  private String user_id;
  private String username;
  private String location_name;
  private String text;
  private Date comment_date;

  public String getComment_id() {
    return comment_id;
  }

  public void setComment_id(String comment_id) {
    this.comment_id = comment_id;
  }

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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getLocation_name() {
    return location_name;
  }

  public void setLocation_name(String location_name) {
    this.location_name = location_name;
  }

  public Date getComment_date() {
    return comment_date;
  }

  public void setComment_date(Date comment_date) {
    this.comment_date = comment_date;
  }

  public void now() {
    this.setComment_date(new Date());
  }

  public static Comment toComment(SqlRowSet rs) {

    Comment comment = new Comment();
    comment.setComment_id(rs.getString("comment_id"));
    comment.setUser_id(rs.getString("user_id"));
    comment.setUsername(rs.getString("username"));
    comment.setLocation_name(rs.getString("location_name"));
    comment.setText(rs.getString("text"));
    comment.setComment_date(rs.getDate("comment_date"));

    return comment;
  }

  public JsonObject toJSON() {
    return Json.createObjectBuilder()
        .add("comment_id", comment_id)
        .add("user_id", user_id)
        .add("username", username)
        .add("location_name", location_name)
        .add("text", text)
        .add("comment_date", comment_date.toString())
        .build();
  }

}
