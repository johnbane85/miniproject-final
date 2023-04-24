package vttp.project.server.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.project.server.models.Comment;

import static vttp.project.server.respositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class CommentRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public String insertComment(Comment comment) {

    int count = jdbcTemplate.update(SQL_INSERT_COMMENT, comment.getComment_id(), comment.getUser_id(),
        comment.getUsername(), comment.getLocation_name(), comment.getText(),
        comment.getComment_date());

    if (count > 0) {
      return "comment %s was created".formatted(comment.getComment_id());
    } else {
      return "comment was not created";
    }

  }

  public List<Comment> getComments(String location_name) {

    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_COMMENTS_BY_LOCATION_NAME, location_name);

    final List<Comment> comments = new LinkedList<>();

    while (rs.next())
      comments.add(Comment.toComment(rs));

    return comments;
  }

}
