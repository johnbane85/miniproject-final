package vttp.project.server.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.project.server.models.Comment;
import vttp.project.server.respositories.CommentRepository;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepo;

  @Transactional(rollbackFor = { CommentException.class })
  public String createComment(String user_id, String username, String location_name, String text)
      throws CommentException {

    String response;

    Comment comment = new Comment();

    // Set a unique comment id
    String comment_id = UUID.randomUUID().toString().substring(0, 8);
    comment.setComment_id(comment_id);
    // Set the comment date
    comment.now();
    // Set the comment user_id
    comment.setUser_id(user_id);
    // Set the comment username
    comment.setUsername(username);
    // Set the comment location_name
    comment.setLocation_name(location_name);
    // Set the comment text
    comment.setText(text);

    try {
      response = commentRepo.insertComment(comment);
    } catch (Exception ex) {
      throw new CommentException(ex.getMessage(), ex);
    }
    return response;
  }

  public List<Comment> getComments(String location_name) {

    return commentRepo.getComments(location_name);
  }

}
