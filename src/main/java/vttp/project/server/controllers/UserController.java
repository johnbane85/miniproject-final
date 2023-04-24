package vttp.project.server.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.project.server.models.User;
import vttp.project.server.services.EmailService;
import vttp.project.server.services.LoginRecordSerivce;
import vttp.project.server.services.TokenService;
import vttp.project.server.services.UserService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class UserController {

  @Autowired
  private TokenService tokenSvc;

  @Autowired
  private UserService userSvc;

  @Autowired
  private EmailService emailSvc;

  @Autowired
  private LoginRecordSerivce loginRecordSvc;

  // POST /api/postUser
  @PostMapping(path = "/postUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> postUser(@RequestPart String username, @RequestPart String password,
      @RequestPart String email) {

    try {
      String result = userSvc.createUser(username, password, email);

      if (result.equals("Success")) {
        String body = "Hello %s,\n\nWelcome! User Account Creation was successful.\n\n\nBest regards,\nSystem Admin"
            .formatted(username);
        String subject = "User Account Created Successfully";

        emailSvc.sendEmail(email, subject, body);

      } else {
        String body = "Hello %s,\n\nYour User Account was not created.\n%s.\nPlease try again.\n\n\nBest regards,\nSystem Admin"
            .formatted(username, result);
        String subject = "User Account Creation Failed";

        emailSvc.sendEmail(email, subject, body);
      }

      JsonObject response = Json.createObjectBuilder()
          .add("postUserResponse", result)
          .build();

      return ResponseEntity.ok(response.toString());

    } catch (Exception ex) {
      JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
    }
  }

  // GET /api/getUser
  @GetMapping(path = "/getUser")
  @ResponseBody
  public ResponseEntity<String> getUser(@RequestParam String username, @RequestParam String token) {

    // System.out.println("username:" + username + " " + "token: " + token);

    JsonObject result = null;

    if (tokenSvc.getToken().equals(token)) {
      User user = userSvc.getUser(username);
      JsonObjectBuilder builder = Json.createObjectBuilder(user.toJSON());
      result = builder.build();

      return ResponseEntity.ok(result.toString());
    } else {
      JsonObject error = Json.createObjectBuilder().add("ERROR", "INVALID TOKEN").build();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.toString());
    }

  }

  // GET /api/getLoginRecord
  @GetMapping(path = "/getLoginRecord")
  @ResponseBody
  public ResponseEntity<String> getLoginRecord(@RequestParam String user_id, @RequestParam String token) {

    if (tokenSvc.getToken().equals(token)) {
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      loginRecordSvc.getLoginRecord(user_id).stream()
          .forEach(r -> {
            arrayBuilder.add(r.toJSON());
          });

      String response = arrayBuilder.build().toString();

      return ResponseEntity.ok(response);

    } else {
      JsonObject error = Json.createObjectBuilder().add("ERROR", "INVALID TOKEN").build();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.toString());
    }
  }

  // POST /api/userLogin
  @PostMapping(path = "/userLogin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> userLogin(@RequestPart String email, @RequestPart String password) {

    JsonObject result = null;
    Boolean auth_state = userSvc.userLogin(email, password);
    User user = userSvc.getUserByEmail(email);

    String token = UUID.randomUUID().toString();
    tokenSvc.setToken(token);

    JsonObjectBuilder builder = Json.createObjectBuilder()
        .add("auth_state", auth_state)
        .add("username_returned", user.getUsername())
        .add("userId_returned", user.getUser_id())
        .add("email_returned", user.getEmail())
        .add("_token", token)
        .add("expiresIn", new Date().getTime() + 3600000 + "");
    result = builder.build();

    if (auth_state) {

      loginRecordSvc.updateLoginRecord(user.getUser_id());

      return ResponseEntity.ok(result.toString());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.toString());
    }

  }

  // PUT /api/editUser
  @PutMapping(path = "/editUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> editUser(@RequestPart String username,
      @RequestPart String password,
      @RequestPart String email, @RequestPart String user_id, @RequestPart String token) {

    if (tokenSvc.getToken().equals(token)) {
      try {

        String result = userSvc.editUser(user_id, username, password, email);

        if (result.equals("User profile was not changed") || result.equals("User account does not exist")) {

          String body = "Hello,\n\nYour User Account details update was not successful.\nPlease try again.\n\n\nBest regards,\nSystem Admin";
          String subject = "User Account details update failed";

          emailSvc.sendEmail(email, subject, body);

        } else {
          String body = "Hello %s,\n\nYour User Account details update was successful.\n\n\nBest regards,\nSystem Admin"
              .formatted(username);
          String subject = "User Account details has been updated";

          emailSvc.sendEmail(email, subject, body);
        }

        JsonObject response = Json.createObjectBuilder()
            .add("editUserResponse", result)
            .build();

        return ResponseEntity.ok(response.toString());

      } catch (Exception ex) {
        JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
      }
    } else {
      JsonObject error = Json.createObjectBuilder().add("ERROR", "INVALID TOKEN").build();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.toString());
    }
  }

  // DELETE /api/deleteUser
  @DeleteMapping(path = "/deleteUser")
  @ResponseBody
  public ResponseEntity<String> deleteUser(@RequestParam String user_id, @RequestParam String token,
      @RequestParam String email) {

    if (tokenSvc.getToken().equals(token)) {
      try {
        String result = userSvc.deleteUser(user_id);

        if (result.equals("Success")) {
          String body = "Hello,\n\nYour User Account has been deleted.\nWe are sad to see you go.\n\n\nBest regards,\nSystem Admin";
          String subject = "User Account Deleted";

          emailSvc.sendEmail(email, subject, body);
        } else {
          String body = "Hello,\n\nThe request to delete your User Account was unsuccessful.\nPlease try again.\n\n\nBest regards,\nSystem Admin";
          String subject = "User Account Delete Unsuccessful";

          emailSvc.sendEmail(email, subject, body);
        }

        JsonObject response = Json.createObjectBuilder()
            .add("deleteUserResponse", result)
            .build();

        return ResponseEntity.ok(response.toString());

      } catch (Exception ex) {
        JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
      }

    } else {
      JsonObject error = Json.createObjectBuilder().add("ERROR", "INVALID TOKEN").build();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.toString());
    }
  }

}
