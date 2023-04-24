package vttp.project.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Weather {

  private String text;
  private String icon;
  private Integer temp_c;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getTemp_c() {
    return temp_c;
  }

  public void setTemp_c(Integer temp_c) {
    this.temp_c = temp_c;
  }

  public JsonObject toJSON() {
    return Json.createObjectBuilder()
        .add("text", text)
        .add("icon", icon)
        .add("temp_c", temp_c)
        .build();

  }
}