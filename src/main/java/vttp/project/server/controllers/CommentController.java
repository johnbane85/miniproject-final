package vttp.project.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.project.server.services.CommentService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CommentController {

  @Autowired
  private CommentService commentSvc;

  // POST /api/postComment
  @PostMapping(path = "/postComment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> postUser(@RequestPart String user_id, @RequestPart String username,
      @RequestPart String location_name, @RequestPart String text) {

    try {
      String result = commentSvc.createComment(user_id, username, location_name, text);

      JsonObject response = Json.createObjectBuilder()
          .add("postCommentResponse", result)
          .build();

      return ResponseEntity.ok(response.toString());

    } catch (Exception ex) {
      JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.toString());
    }

  }

  // GET /api/getComments/location_name
  @GetMapping(path = "/getComments/{location_name}")
  @ResponseBody
  public ResponseEntity<String> getLocationByName(@PathVariable String location_name) {

    // Query the database for the comments
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    commentSvc.getComments(location_name).stream()
        .forEach(c -> {
          arrBuilder.add(c.toJSON());
        });

    String response = arrBuilder.build().toString();

    // System.out.printf(">>> response:%s \n", response);

    return ResponseEntity.ok(response);
  }

}
