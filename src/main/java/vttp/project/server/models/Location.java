package vttp.project.server.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Location {

  private Integer location_id;
  private String location_name;
  private String address;
  private String description;
  private String website_url;
  private String image_1;
  private String image_2;
  private String image_3;

  public Integer getLocation_id() {
    return location_id;
  }

  public void setLocation_id(Integer location_id) {
    this.location_id = location_id;
  }

  public String getLocation_name() {
    return location_name;
  }

  public void setLocation_name(String location_name) {
    this.location_name = location_name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWebsite_url() {
    return website_url;
  }

  public void setWebsite_url(String website_url) {
    this.website_url = website_url;
  }

  public String getImage_1() {
    return image_1;
  }

  public void setImage_1(String image_1) {
    this.image_1 = image_1;
  }

  public String getImage_2() {
    return image_2;
  }

  public void setImage_2(String image_2) {
    this.image_2 = image_2;
  }

  public String getImage_3() {
    return image_3;
  }

  public void setImage_3(String image_3) {
    this.image_3 = image_3;
  }

  public static Location create(Document doc) {
    Location location = new Location();

    location.setLocation_id(doc.getInteger("location_id"));
    location.setLocation_name(doc.getString("location_name"));
    location.setAddress(doc.getString("address"));
    location.setDescription(doc.getString("description"));
    location.setWebsite_url(doc.getString("website_url"));
    location.setImage_1(doc.getString("image_1"));
    location.setImage_2(doc.getString("image_2"));
    location.setImage_3(doc.getString("image_3"));

    return location;
  }

  public JsonObject toJSON() {

    return Json.createObjectBuilder()
        .add("location_id", location_id)
        .add("location_name", location_name)
        .add("address", address)
        .add("description", description)
        .add("website_url", website_url)
        .add("image_1", image_1)
        .add("image_2", image_2)
        .add("image_3", image_3)
        .build();
  }

}
